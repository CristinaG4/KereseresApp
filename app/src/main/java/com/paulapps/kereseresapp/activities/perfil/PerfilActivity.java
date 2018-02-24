package com.paulapps.kereseresapp.activities.perfil;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.paulapps.kereseresapp.R;
import com.paulapps.kereseresapp.activities.ListViewActivity;
import com.paulapps.kereseresapp.activities.NavigationDrawerActivity;

public class PerfilActivity extends AppCompatActivity {

    private FirebaseDatabase firebase;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    TextView tituloAppPerfil,PerfilEmail,PerfilPassword,PerfilName,PerfilApartment,PerfilComCode,PerfilTelefono;
    Button btnAceptarPerfil, btnEditarPerfil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        tituloAppPerfil = (TextView) findViewById(R.id.tituloAppPerfil);
        btnAceptarPerfil = (Button) findViewById(R.id.btnAceptarPerfil);
        btnEditarPerfil = (Button) findViewById(R.id.btnEditarPerfil);
        PerfilEmail=(TextView) findViewById(R.id.PerfilEmail);
        PerfilPassword=(TextView) findViewById(R.id.PerfilPassword);
        PerfilName=(TextView) findViewById(R.id.PerfilName);
        PerfilApartment=(TextView) findViewById(R.id.PerfilApartment);
        PerfilComCode=(TextView) findViewById(R.id.PerfilComCode);
        PerfilTelefono=(TextView) findViewById(R.id.PerfilTelefono);


        //Fuente titulo
        Typeface myFont = Typeface.createFromAsset(getAssets(), "Strawberry Blossom.ttf");
        tituloAppPerfil.setTypeface(myFont);

        btnAceptarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PerfilActivity.this, ListViewActivity.class);
                startActivity(i);
                overridePendingTransition(R.transition.fade_in, R.transition.fade_out);
            }
        });

        btnEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(PerfilActivity.this, EditPerfilActivity.class);
                startActivity(in);
                overridePendingTransition(R.transition.left_in, R.transition.left_out);
            }
        });

        //Mostrar datos usuario
        InfoUser();

    }
//para sacar info del usuario
    public void InfoUser(){
        //obtenemos el usuario q ha abierto la sesion
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            String phone = user.getPhoneNumber();

            PerfilEmail.setText(email);
            PerfilName.setText(name);


            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
        }
    }
}
