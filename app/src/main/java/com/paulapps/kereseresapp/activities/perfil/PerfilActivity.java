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
import com.paulapps.kereseresapp.model.Perfil;

public class PerfilActivity extends AppCompatActivity {

    private Perfil perfil;
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

        perfil = (Perfil) getIntent().getSerializableExtra("PERFIL");


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
                in.putExtra("PERFIL",perfil);
                startActivityForResult(in,1);
                overridePendingTransition(R.transition.left_in, R.transition.left_out);
            }
        });

        //Mostrar datos usuario
        InfoUser();

    }
//para sacar info del usuario
    public void InfoUser(){

        //obtenemos el usuario q ha abierto la sesion

        PerfilEmail.setText(perfil.getEmail());
        PerfilName.setText(perfil.getNombre());
        PerfilApartment.setText(perfil.getApart());
        PerfilComCode.setText(Integer.toString(perfil.getComCode()));
        PerfilTelefono.setText(perfil.getTelf());
        PerfilPassword.setText(perfil.getPass());


            // Check if user's email is verified
            //boolean emailVerified = user.isEmailVerified();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                perfil = (Perfil)data.getSerializableExtra("PV");
            }else{

            }
        }
    }
}
