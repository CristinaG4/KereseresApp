package com.paulapps.kereseresapp.activities.perfil;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.paulapps.kereseresapp.R;

public class EditPerfilActivity extends AppCompatActivity {

    TextView tituloAppProfile;
    Button btnCalcelarProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_perfil);

        tituloAppProfile = (TextView) findViewById(R.id.tituloAppProfile);
        btnCalcelarProfile = (Button) findViewById(R.id.btnCalcelarProfile);


        //Fuente titulo
        Typeface myFont = Typeface.createFromAsset(getAssets(), "Strawberry Blossom.ttf");
        tituloAppProfile.setTypeface(myFont);

        btnCalcelarProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(EditPerfilActivity.this, PerfilActivity.class);
                startActivity(i);
            }
        });


    }

}
