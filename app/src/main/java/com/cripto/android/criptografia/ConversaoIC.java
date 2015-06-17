package com.cripto.android.criptografia;

/**
 * Created by Thales on 14/06/15.
 */
public class ConversaoIC {
    Integer[] input;
    Integer[] crypt;

    /*public static void printArray(Object[] vector, int length, int cl, String name) {
        System.out.println(name);
        for (int i = 0; i < length; i++) {
            if (cl == 1) {
                System.out.println(vector[i]);
            } else {
                System.out.print(vector[i] + " ");
            }

        }
        System.out.println("");
    }*/

    /*public static void printMatrix(Object[][] vector, int length, String name) {
        System.out.println(name);
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                System.out.print(vector[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("");
    }*/

    public static String toCompleteBinary(String binary, int length) {
        while (binary.length() < length) {
            binary = "0" + binary;
        }
        return binary;
    }

    public static void encrypt(Integer[] input,Integer[] crypt){
        String[] inputx = new String[input.length];
        Double[] x = new Double[input.length];
        Double[] xl = new Double[input.length];
        String[] b = new String[input.length];
        String[] d = new String[input.length];

        //printArray(input, input.length, 0, "TO ENCRYPT");

        for (int i = 0; i < input.length; i++) {
            b[i] = "00000000";
            d[i] = "00000000";
            x[i] = 0.0;
            crypt[i] = 0;
        }
        for (int i = 0; i < input.length; i++) {
            inputx[i] = toCompleteBinary(Integer.toBinaryString(input[i]), 8);
        }

        int l = inputx.length;
        double mu = 3.9;
        x[0] = 0.75;

        for (int i = 1; i < l; i++) {
            x[i] = ((Double) (mu * x[i - 1] * (1 - x[i - 1])));
        }
        //printArray(x,x.length,1,"X");

        double max = x[0];
        double min = x[0];

        for (int k = 0; k < l; k++) {
            if (x[k] > max) {
                max = x[k];
            }
            if (x[k] < min) {
                min = x[k];
            }
        }
        for (int i = 0; i < l; i++) {  //uint8
            xl[i] = ((x[i] - min) / max) * 255;
        }

        //printArray(xl,xl.length,1,"XL");
        for (int i = 0; i < input.length; i++) {
            b[i] = toCompleteBinary(Integer.toBinaryString(Math.round(xl[i].floatValue())), 8);

        }
        //printArray(b,b.length,1,"B");

        double[] teta = new double[8];
        Integer[][] weights = new Integer[8][8];
        for (int c = 0; c < input.length; c++) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (b[c].charAt(i) == '0' && (i == j)) {
                        weights[i][j] = 1;
                    } else if (b[c].charAt(i) == '1' && (i == j)) {
                        weights[i][j] = -1;
                    } else if ((i != j)) {
                        weights[i][j] = 0;
                    }
                }
                if (b[c].charAt(i) == '0') {
                    teta[i] = -0.5;
                } else {
                    teta[i] = 0.5;
                }
            }
            //printMatrix(weights,weights.length,"WEIGHTS");

            for (int i = 0; i < 8; i++) {
                double sum = 0;
                for (int w = 0; w < 8; w++) {
                    sum = sum + weights[i][w] * Integer.parseInt("" + inputx[c].charAt(w));
                }
                sum = sum + teta[i];
                StringBuilder dc = new StringBuilder(d[c]);
                if (sum >= 0) {
                    dc.setCharAt(i, '1');
                } else {
                    dc.setCharAt(i, '0');
                }
                d[c] = dc.toString();

            }
            //System.out.println("dc="+d[c]);
            for (int i = 0; i < 8; i++) {
                crypt[c] = crypt[c] + (Integer.parseInt(d[c].charAt(i) + "") * ((Double) (Math.pow(2, (7 - i)))).intValue());
            }
        }
        //printArray(d, d.length, 1, "D");
        //printArray(crypt, crypt.length, l, "ENCRYPTED");

    }


    public ConversaoIC(Integer[] entrada) {
        input = entrada;
        crypt = new Integer[input.length];
        //Encrypt
        //encrypt(input, crypt);
        //System.out.println("");
        //Decrypt
        //encrypt(crypt, input);
    }

    public Integer[] encriptar(){
        encrypt(input, crypt);
        return crypt;
    }
}