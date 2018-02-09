package com.paulapps.kereseresapp.activities.perfil;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.paulapps.kereseresapp.R;
import com.paulapps.kereseresapp.activities.ListViewActivity;

public class PerfilActivity extends AppCompatActivity {

    TextView tituloAppPerfil;
    Button btnCalcelarPerfil;
    Button btnEditarPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        tituloAppPerfil = (TextView) findViewById(R.id.tituloAppPerfil);
        btnCalcelarPerfil = (Button) findViewById(R.id.btnCalcelarPerfil);
        btnEditarPerfil= (Button) findViewById(R.id.btnEditarPerfil);


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

        btnEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(PerfilActivity.this, EditPerfilActivity.class);
                startActivity(in);
            }
        });

    }

}
