package com.paulapps.kereseresapp.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.paulapps.kereseresapp.R;
import com.paulapps.kereseresapp.activities.login_signup.MainActivity;
import com.paulapps.kereseresapp.activities.perfil.PerfilActivity;

public class ListViewActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseDatabase firebase;
    private GoogleApiClient googleApiClient;
    ImageView filtroAll;
    ImageView filtroAmigos;
    ImageView filtroInformatica;
    ImageView filtroClases;
    ImageView filtroMenaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        // getSupportActionBar().hide();
        ListView LVDemandas= (ListView) findViewById(R.id.lvDemandas);
        ListView LVOfertas= (ListView) findViewById(R.id.lvOfertas);
        Toolbar menu =(Toolbar) findViewById(R.id.toolbar);//importar como v7 para q no de error
        setSupportActionBar(menu);

        //Barra para filtrar
        filtroAll = (ImageView) findViewById(R.id.filtroAll);
        filtroAmigos = (ImageView) findViewById(R.id.filtroAmigos);
        filtroInformatica = (ImageView) findViewById(R.id.filtroInformatica);
        filtroClases = (ImageView) findViewById(R.id.filtroClases);
        filtroMenaje = (ImageView) findViewById(R.id.filtroMenaje);

        filtroAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Hacer el filtro
                Intent i = new Intent(ListViewActivity.this, ListViewActivity.class);
                startActivity(i);

            }
        });

        filtroAmigos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Hacer el filtro
                Intent i = new Intent(ListViewActivity.this, ListViewActivity.class);
                startActivity(i);

            }
        });

        filtroInformatica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Hacer el filtro
                Intent i = new Intent(ListViewActivity.this, ListViewActivity.class);
                startActivity(i);

            }
        });

        filtroClases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Hacer el filtro
                Intent i = new Intent(ListViewActivity.this, ListViewActivity.class);
                startActivity(i);

            }
        });

        filtroMenaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Hacer el filtro
                Intent i = new Intent(ListViewActivity.this, ListViewActivity.class);
                startActivity(i);

            }
        });


       /* Adapter adapter = new Adapter(this, Perfil.aulasDB);
        LVDemandas.setAdapter(adapter);*/

            Resources res = getResources();

            TabHost tabs = findViewById(android.R.id.tabhost);
            tabs.setup();

            TabHost.TabSpec spec = tabs.newTabSpec("mitab1");
            spec.setContent(R.id.tab1);
            spec.setIndicator("Ofertas",getDrawable(R.drawable.ic_launcher_background));
            tabs.addTab(spec);

            spec = tabs.newTabSpec("mitab2");
            spec.setContent(R.id.tab2);
            spec.setIndicator("Demandas",getDrawable(R.drawable.ic_launcher_background));
            tabs.addTab(spec);




            tabs.setCurrentTab(0);

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
