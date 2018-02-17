package com.paulapps.kereseresapp.activities.perfil;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.paulapps.kereseresapp.R;

public class EditPerfilActivity extends AppCompatActivity {

    TextView tituloAppProfile;
    Button btnCalcelarProfile;
    EditText EtEmail,EtPassword,EtName,EtApartment,EtComCode,EtTelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_perfil);

        tituloAppProfile = (TextView) findViewById(R.id.tituloAppProfile);
        btnCalcelarProfile = (Button) findViewById(R.id.btnCalcelarProfile);
        EtEmail=(EditText)findViewById(R.id.EtEmail);
        EtPassword=(EditText)findViewById(R.id.EtPassword);
        EtName=(EditText)findViewById(R.id.EtName);
        EtApartment=(EditText)findViewById(R.id.EtApartment);
        EtComCode=(EditText)findViewById(R.id.EtComCode);
        EtTelefono=(EditText)findViewById(R.id.EtTelefono);


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
    //para sacar info del usuario
    public void InfoUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
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
    }

}
