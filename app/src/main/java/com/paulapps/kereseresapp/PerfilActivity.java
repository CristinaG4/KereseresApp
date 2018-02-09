package com.paulapps.kereseresapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PerfilActivity extends AppCompatActivity {

    TextView tituloAppPerfil;
    Button btnCalcelarPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        tituloAppPerfil = (TextView) findViewById(R.id.tituloAppPerfil);
        btnCalcelarPerfil = (Button) findViewById(R.id.btnCalcelarPerfil);


        //Fuente titulo
        Typeface myFont = Typeface.createFromAsset(getAssets(), "Strawberry Blossom.ttf");
        tituloAppPerfil.setTypeface(myFont);

        btnCalcelarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PerfilActivity.this, ListViewActivity.class);
                startActivity(i);
            }
        });

    }

}
