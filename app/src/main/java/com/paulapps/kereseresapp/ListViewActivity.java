package com.paulapps.kereseresapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ListViewActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseDatabase firebase;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
//        getSupportActionBar().hide();
        Toolbar menu =(Toolbar) findViewById(R.id.toolbar);//importar como v7 para q no de error
        setSupportActionBar(menu);

    }
    //creamos el menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =  getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }
    //para saber q opcion del menu se selecciona
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSalir://hacer case por opcion
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;
            case R.id.perfil:
                Intent in = new Intent(ListViewActivity.this, PerfilActivity.class);
                startActivity(in);
                break;

        }

        return true;
    }
}
