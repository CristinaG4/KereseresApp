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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.paulapps.kereseresapp.Adapters.Adapter;
import com.paulapps.kereseresapp.R;
import com.paulapps.kereseresapp.activities.login_signup.MainActivity;
import com.paulapps.kereseresapp.activities.perfil.PerfilActivity;
import com.paulapps.kereseresapp.model.Pedido;
import com.paulapps.kereseresapp.model.Perfil;

import java.util.ArrayList;

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
    ListView listViewDemandas;
    ListView listViewOfertas;
    ArrayList<Perfil> perfiles;
    static ArrayList<Pedido> pedidos;
    private int pedidoIndex;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        // getSupportActionBar().hide();
        listViewDemandas= (ListView) findViewById(R.id.lvDemandas);
        listViewOfertas= (ListView) findViewById(R.id.lvOfertas);
        Toolbar menu =(Toolbar) findViewById(R.id.toolbar);//importar como v7 para q no de error
        setSupportActionBar(menu);

        perfiles = new ArrayList<>();
        perfiles.add(new Perfil("Nacho Jimenez","ncassinello@gmail.com","1234","7ºG",1000,"913140885",R.drawable.all));
        perfiles.add(new Perfil("Cristinini","cristinini@gmail.com","1234","1ºH",1000,"91548775",R.drawable.all));
        perfiles.add(new Perfil("PaulaCR7","paulaCR7@gmail.com","1234","13ºA",1000,"911254889",R.drawable.all));

        pedidos = new ArrayList<>();
        pedidos.add(new Pedido(0,"Formatear Ordenador",perfiles.get(0),"dinero","informatica","Necesito que me formateis el ordenador","demanda"));
        pedidos.add(new Pedido(1,"Cuidar a mis hijos",perfiles.get(1),"favor","compañia","Salgo esta noche y encesito niñera","demanda"));
        pedidos.add(new Pedido(2,"Ver el Madrid",perfiles.get(2),"favor","compañia","Ofrezco salon y futbol a cambio de alguien con quien verlo","oferta"));
        pedidos.add(new Pedido(3,"Clases de XML",perfiles.get(0),"dinero","clases","Necesito clases de XML avanzadas","demanda"));

        listViewOfertas.setAdapter(new Adapter(this,seleccionarLista(pedidos,"oferta")));
        listViewDemandas.setAdapter(new Adapter(this,seleccionarLista(pedidos,"demanda")));

        listViewOfertas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(view.getContext(),DetallesPedido.class);
                //se le pasa a traves del intent el pedido entero que ha seleccionado
                intent.putExtra("PEDIDO",seleccionarLista(pedidos,"oferta").get(position));
                pedidoIndex = position;

                startActivityForResult(intent,1);

            }
        });
        listViewDemandas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Hay que implementar la clase DetallesPedido tambien, para ello necesito el xml de la activity
                Intent intent = new Intent(view.getContext(),DetallesPedido.class);
                intent.putExtra("PEDIDO",seleccionarLista(pedidos,"demanda").get(position));
                pedidoIndex = position;

                startActivityForResult(intent,1);

            }
        });

        //Manejamos los tabhost
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






    }

    //creamos el menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =  getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    //para saber que opcion del menu se selecciona
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
    //funcion para filtrar por el oferta/demanda
    public ArrayList<Pedido> seleccionarLista(ArrayList<Pedido> pedidos, String filtro){

        ArrayList<Pedido> pedidosFiltrados = new ArrayList<>();
        for (Pedido p:pedidos){
            if (p.getOferDeman().equalsIgnoreCase(filtro)){
                pedidosFiltrados.add(p);
            }
        }

        return pedidosFiltrados;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){/*
                data.getSerializableExtra("PEDIDO");
                pedidos.get(pedidoIndex) = (Pedido) data.getSerializableExtra("PEDIDO");;

            */
            }
        }
    }
}
