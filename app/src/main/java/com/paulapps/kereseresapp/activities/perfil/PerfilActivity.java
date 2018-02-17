package com.paulapps.kereseresapp.activities.perfil;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.paulapps.kereseresapp.R;
import com.paulapps.kereseresapp.activities.ListViewActivity;
import com.paulapps.kereseresapp.activities.NavigationDrawerActivity;

public class PerfilActivity extends AppCompatActivity {

    private FirebaseDatabase firebase;
    private FirebaseAuth mAuth;
    //private FirebaseUser user;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    TextView tituloAppPerfil;
    Button btnCalcelarPerfil;
    Button btnEditarPerfil;
    Button btnEliminarPerfil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        tituloAppPerfil = (TextView) findViewById(R.id.tituloAppPerfil);
        btnCalcelarPerfil = (Button) findViewById(R.id.btnCalcelarPerfil);
        btnEditarPerfil = (Button) findViewById(R.id.btnEditarPerfil);
        btnEliminarPerfil = (Button) findViewById(R.id.btnEliminarPerfil);


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

        btnEliminarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
/*
    public void InfoUser(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();


            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
        }
    }*/
}
