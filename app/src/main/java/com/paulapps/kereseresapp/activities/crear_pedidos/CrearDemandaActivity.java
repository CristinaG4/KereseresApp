package com.paulapps.kereseresapp.activities.crear_pedidos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.*;
import android.view.View;
import android.content.Intent;

import com.paulapps.kereseresapp.R;
import com.paulapps.kereseresapp.activities.ListViewActivity;

public class CrearDemandaActivity extends AppCompatActivity {

    EditText titleCreaDemandaET, descripCreaDemandaET;
    Button btnCancelarCreaDemanda, btnCrearDemanda;
    Intent i;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_demanda);

        //Declaramos elementos
        titleCreaDemandaET = findViewById(R.id.titleCreaDemandaET);
        btnCancelarCreaDemanda = findViewById(R.id.btnCancelarCreaDemanda);
        btnCrearDemanda = findViewById(R.id.btnCrearDemanda);
        descripCreaDemandaET = findViewById(R.id.descripCreaDemandaET);

        //Hacer scroll en el texto para descripcion
        descripCreaDemandaET.setMovementMethod(new ScrollingMovementMethod());

        //Funcionalidad botones
        btnCancelarCreaDemanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(CrearDemandaActivity.this, ListViewActivity.class);
                startActivity(i);
            }
        });

        btnCrearDemanda.setOnClickListener(new View.OnClickListener() {
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
