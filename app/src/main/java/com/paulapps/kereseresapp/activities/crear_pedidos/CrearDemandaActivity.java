package com.paulapps.kereseresapp.activities.crear_pedidos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.*;
import android.view.View;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paulapps.kereseresapp.model.FirebaseReferences;
import com.paulapps.kereseresapp.model.Pedido;
import com.paulapps.kereseresapp.R;
import com.paulapps.kereseresapp.activities.ListViewActivity;
import com.paulapps.kereseresapp.model.Perfil;
import android.util.Log;

public class CrearDemandaActivity extends AppCompatActivity {

    EditText titleCreaDemandaET, descripCreaDemandaET;
    Button btnCancelarCreaDemanda, btnCrearDemanda;
    Intent i;
    Spinner spinnerCreaDemanda;
    RadioButton radioBtnFavorCreaDemanda,radioBtnMoneyCreaDemanda;
    RadioGroup radioGroup;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    private Perfil perfil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_demanda);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();

        //Declaramos elementos
        titleCreaDemandaET = findViewById(R.id.titleCreaDemandaET);
        btnCancelarCreaDemanda = findViewById(R.id.btnCancelarCreaDemanda);
        btnCrearDemanda = findViewById(R.id.btnCrearDemanda);
        descripCreaDemandaET = findViewById(R.id.descripCreaDemandaET);
        spinnerCreaDemanda = findViewById(R.id.spinnerCreaDemanda);
        radioBtnFavorCreaDemanda = findViewById(R.id.radioBtnFavorCreaDemanda);
        radioBtnMoneyCreaDemanda = findViewById(R.id.radioBtnMoneyCreaDemanda);
        radioGroup = findViewById(R.id.radioGroupBtn);

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Perfil p = dataSnapshot.getValue(Perfil.class);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (p.getEmail().equals(user.getEmail())){
                    perfil = p;
                    if (perfil.getTelf()==null){
                        perfil.setTelf("");
                    }
                }
            }

            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            public void onCancelled(DatabaseError databaseError) {}
        };
        mDatabaseReference.child(FirebaseReferences.PERFIL_REFERENCES).addChildEventListener(mChildEventListener);

        /*perfil = new Perfil();
        perfil = (Perfil) getIntent().getSerializableExtra("PCD");*/


        //Coger valor spinner
        //String categoria=spinnerCreaDemanda.getSelectedItem().toString();
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

                if(examinarCampos()) {
                    Pedido p = new Pedido();
                    rellenarPedido(p);
                    mDatabaseReference.child(FirebaseReferences.PEDIDO_REFERENCES).push().setValue(p);
                    Toast t = Toast.makeText(CrearDemandaActivity.this, "Demand created", Toast.LENGTH_SHORT);
                    t.show();
                    i = new Intent(CrearDemandaActivity.this, ListViewActivity.class);
                    startActivity(i);
                }

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

    private void rellenarPedido(Pedido p){
        p.setTitulo(titleCreaDemandaET.getText().toString());
        p.setCategoria(spinnerCreaDemanda.getSelectedItem().toString());
        p.setDescripcion(descripCreaDemandaET.getText().toString());
        if (radioBtnFavorCreaDemanda.isChecked())
        {
            p.setTipoPago(radioBtnFavorCreaDemanda.getText().toString());
        }else
        {
            p.setTipoPago(radioBtnMoneyCreaDemanda.getText().toString());
        }
        p.setOferDeman("demanda");
        p.setPerfil(perfil);


    }

    //Examinar que todos los campos esten rellenados
    public boolean examinarCampos()
    {
        boolean b = true;
        if(titleCreaDemandaET.getText().toString().equals("")) {
            Toast t = Toast.makeText(this,"Fill Title", Toast.LENGTH_SHORT);
            t.show();
            b = false;
        }else if(spinnerCreaDemanda.getSelectedItem().toString().equals("Select category")){
            Toast t = Toast.makeText(this,"Select category", Toast.LENGTH_SHORT);
            t.show();
            b = false;
        }else if(descripCreaDemandaET.getText().toString().equals("")){
            Toast t = Toast.makeText(this,"Fill description", Toast.LENGTH_SHORT);
            t.show();
            b = false;
        }else if(radioGroup.getCheckedRadioButtonId() == -1){
            Toast t = Toast.makeText(this,"Select type of payment", Toast.LENGTH_SHORT);
            t.show();
            b = false;
        }
        return b;
    }

}
