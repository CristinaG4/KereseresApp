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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paulapps.kereseresapp.R;
import com.paulapps.kereseresapp.model.FirebaseReferences;
import com.paulapps.kereseresapp.model.Perfil;

public class EditPerfilActivity extends AppCompatActivity {

    Perfil perfil;
    Intent i;
    TextView tituloAppProfile;
    Button btnCalcelarEditProfile, btnAceptarEditProfile;
    EditText EtEmail,EtPassword,EtName,EtApartment,EtComCode,EtTelefono;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_perfil);

        tituloAppProfile = (TextView) findViewById(R.id.tituloAppProfile);
        btnCalcelarEditProfile = (Button) findViewById(R.id.btnCalcelarEditProfile);
        btnAceptarEditProfile = (Button) findViewById(R.id.btnAceptarEditProfile);
        EtEmail=(EditText)findViewById(R.id.EtEmail);
        EtPassword=(EditText)findViewById(R.id.EtPassword);
        EtName=(EditText)findViewById(R.id.EtName);
        EtApartment=(EditText)findViewById(R.id.EtApartment);
        EtComCode=(EditText)findViewById(R.id.EtComCode);
        EtTelefono=(EditText)findViewById(R.id.EtTelefono);


        //Fuente titulo
        Typeface myFont = Typeface.createFromAsset(getAssets(), "Strawberry Blossom.ttf");
        tituloAppProfile.setTypeface(myFont);

        btnCalcelarEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i  = new Intent(EditPerfilActivity.this, PerfilActivity.class);
                startActivity(i);
                overridePendingTransition(R.transition.right_in, R.transition.right_out);
            }
        });

        btnAceptarEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarPerfil();
                i = new Intent(EditPerfilActivity.this, PerfilActivity.class);
                i.putExtra("PV",perfil);
                setResult(AppCompatActivity.RESULT_OK, i);
                startActivity(i);
            }
        });

        InfoUser();

    }
    //para sacar info del usuario
    public void InfoUser(){
        perfil = (Perfil) getIntent().getSerializableExtra("PERFIL");
        //obtenemos el usuario q ha abierto la sesion

        EtEmail.setText(perfil.getEmail());
        EtPassword.setText(perfil.getPass());
        EtName.setText(perfil.getNombre());
        EtApartment.setText(perfil.getApart());
        EtComCode.setText(Integer.toString(perfil.getComCode()));
        EtTelefono.setText(perfil.getTelf());

    }

    public void actualizarPerfil() {

        //initialize firebase components
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();

        perfil.setEmail(EtEmail.getText().toString());
        perfil.setPass(EtPassword.getText().toString());
        perfil.setNombre(EtName.getText().toString());
        perfil.setApart(EtApartment.getText().toString());
        perfil.setComCode(Integer.parseInt(EtComCode.getText().toString()));
        perfil.setTelf(EtTelefono.getText().toString());


        mDatabaseReference.child(mAuth.getCurrentUser().getUid()).child("nombre").setValue(perfil.getNombre());

    }

}
