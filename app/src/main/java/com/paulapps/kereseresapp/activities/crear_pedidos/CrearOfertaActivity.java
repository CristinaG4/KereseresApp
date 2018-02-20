package com.paulapps.kereseresapp.activities.crear_pedidos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.*;
import android.view.View;
import android.content.Intent;

import com.paulapps.kereseresapp.R;
import com.paulapps.kereseresapp.activities.ListViewActivity;

public class CrearOfertaActivity extends AppCompatActivity {

    Button btnCancelarCreaOferta, btnCrearOferta;
    EditText descripCreaOfertaET;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_oferta);

        btnCancelarCreaOferta = findViewById(R.id.btnCancelarCreaOferta);
        btnCrearOferta = findViewById(R.id.btnCrearOferta);
        descripCreaOfertaET = findViewById(R.id.descripCreaOfertaET);

        //Hacer scroll en el texto para descripcion
        descripCreaOfertaET.setMovementMethod(new ScrollingMovementMethod());

        //Funcionalidad botones
        btnCancelarCreaOferta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(CrearOfertaActivity.this, ListViewActivity.class);
                startActivity(i);
            }
        });

        btnCrearOferta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    //Funcionalidad RadioButtons
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioBtnFavorCreaDemanda:
                if (checked)
                    break;
            case R.id.radioBtnMoneyCreaDemanda:
                if (checked)
                    break;
        }
    }
}
