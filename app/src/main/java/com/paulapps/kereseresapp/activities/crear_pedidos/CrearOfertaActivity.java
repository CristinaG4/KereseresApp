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
import com.paulapps.kereseresapp.R;
import com.paulapps.kereseresapp.activities.ListViewActivity;
import com.paulapps.kereseresapp.model.FirebaseReferences;
import com.paulapps.kereseresapp.model.Pedido;
import com.paulapps.kereseresapp.model.Perfil;

public class CrearOfertaActivity extends AppCompatActivity {

    Button btnCancelarCreaOferta, btnCrearOferta;
    EditText descripCreaOfertaET,titleCreaOfertaET;
    Spinner spinnerCrearOferta;
    Intent i;
    RadioButton radioBtnFavorCreaOferta,radioBtnMoneyCreaOferta;
    RadioGroup radioGroup;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    private Perfil perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_oferta);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();

        btnCancelarCreaOferta = findViewById(R.id.btnCancelarCreaOferta);
        btnCrearOferta = findViewById(R.id.btnCrearOferta);
        descripCreaOfertaET = findViewById(R.id.descripCreaOfertaET);
        spinnerCrearOferta=findViewById(R.id.spinnerCreaOferta);
        titleCreaOfertaET = findViewById(R.id.titleCreaOfertaET);
        radioBtnFavorCreaOferta = findViewById(R.id.radioBtnFavorCreaOferta);
        radioBtnMoneyCreaOferta = findViewById(R.id.radioBtnMoneyCreaOferta);
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

        //Coger valor spinner
        //String categoria=spinnerCrearOferta.getSelectedItem().toString();

        //Hacer scroll en el texto para descripcion
        descripCreaOfertaET.setMovementMethod(new ScrollingMovementMethod());

        //Funcionalidad botones
        btnCancelarCreaOferta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(CrearOfertaActivity.this, ListViewActivity.class);
                startActivity(i);
            }
        });

        btnCrearOferta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(examinarCampos()) {
                    Pedido p = new Pedido();
                    rellenarPedido(p);
                    mDatabaseReference.child(FirebaseReferences.PEDIDO_REFERENCES).push().setValue(p);
                    Toast t = Toast.makeText(CrearOfertaActivity.this, "Demand created", Toast.LENGTH_SHORT);
                    t.show();
                    i = new Intent(CrearOfertaActivity.this, ListViewActivity.class);
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
        p.setTitulo(titleCreaOfertaET.getText().toString());
        p.setCategoria(spinnerCrearOferta.getSelectedItem().toString());
        p.setDescripcion(descripCreaOfertaET.getText().toString());
        if (radioBtnFavorCreaOferta.isChecked())
        {
            p.setTipoPago(radioBtnFavorCreaOferta.getText().toString());
        }else
        {
            p.setTipoPago(radioBtnMoneyCreaOferta.getText().toString());
        }
        p.setOferDeman("oferta");
        p.setPerfil(perfil);


    }


    public boolean examinarCampos() {
        boolean b = true;
        if(titleCreaOfertaET.getText().toString().equals("")) {
            Toast t = Toast.makeText(this,"Fill Title", Toast.LENGTH_SHORT);
            t.show();
            b = false;
        }else if(spinnerCrearOferta.getSelectedItem().toString().equals("Select category")){
            Toast t = Toast.makeText(this,"Select category", Toast.LENGTH_SHORT);
            t.show();
            b = false;
        }else if(descripCreaOfertaET.getText().toString().equals("")){
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
