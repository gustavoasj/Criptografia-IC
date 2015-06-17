package com.cripto.android.criptografia;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;


public class MainActivity extends ActionBarActivity {

    private static Button Encrypt, Decrypt;
    private static TextView Entrada, Encriptado, Decriptado;
    private static EditText Input_text;
    private ConversaoIC conversao;

    private Integer[] res_encrypt, res_decrypt;
    private String input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Input_text = (EditText) findViewById(R.id.input);
        //Entrada = (TextView) findViewById(R.id.input);
        Encriptado = (TextView) findViewById(R.id.encript);
        Decriptado =  (TextView) findViewById(R.id.decript);
        Encrypt = (Button) findViewById(R.id.encrypt_button);
        Decrypt = (Button) findViewById(R.id.decrypt_button);

        Encrypt.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                //Integer[] entrada =  new Integer[]{11, 23, 37, 45, 68, 25, 236, 58, 59, 90};
                input = Input_text.getText().toString();
                //Converte para array
                String[] items = input.replaceAll("\\[", "").replaceAll("\\]", "").split(",");

                Integer[] results = new Integer[items.length];

                for (int i = 0; i < items.length; i++) {
                    try {
                        results[i] = Integer.parseInt(items[i]);
                    } catch (NumberFormatException nfe) {};
                }

                //Entrada.setText(Arrays.toString(entrada));
                conversao = new ConversaoIC(results);
                res_encrypt = conversao.encriptar();
                Encriptado.setText(Arrays.toString(res_encrypt));

            }

        });

        Decrypt.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                if(res_encrypt != null) {
                    conversao = new ConversaoIC(res_encrypt);
                    res_decrypt = conversao.encriptar();
                    Decriptado.setText(Arrays.toString(res_decrypt));
                }else {
                    Decriptado.setText("Nada foi Encriptado");
                }
            }

        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
