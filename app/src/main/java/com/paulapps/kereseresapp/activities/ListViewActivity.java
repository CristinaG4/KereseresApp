package com.paulapps.kereseresapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.*;
import android.view.ContextMenu;

import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.paulapps.kereseresapp.Adapters.Adapter;
import com.paulapps.kereseresapp.R;
import com.paulapps.kereseresapp.activities.crear_pedidos.CrearDemandaActivity;
import com.paulapps.kereseresapp.activities.crear_pedidos.CrearOfertaActivity;
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
    private ImageButton filtroAll, filtroAmigos, filtroInformatica, filtroClases, filtroMenaje;
    private ListView listViewDemandas, listViewOfertas;
    private ArrayList<Perfil> perfiles;
    private ArrayList<Pedido> pedidos;
    private int pedidoIndex;
    private Toolbar toolbar;
    private  ActionBarDrawerToggle toggle;
    private  DrawerLayout drawer;
    private MenuInflater inflater;
    private FloatingActionMenu floatButtonPrincipal;
    private com.github.clans.fab.FloatingActionButton floatButtonDemanda, floatButtonOferta;


    Intent i;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        // getSupportActionBar().hide();

        //Botones Flotantes
        floatButtonPrincipal = findViewById(R.id.floatButtonPrincipal);
        floatButtonDemanda = findViewById(R.id.floatButtonDemanda);
        floatButtonOferta = findViewById(R.id.floatButtonOferta);

        //Barra para filtrar
        filtroAll = (ImageButton) findViewById(R.id.filtroAll);
        filtroAmigos = (ImageButton) findViewById(R.id.filtroAmigos);
        filtroInformatica = (ImageButton) findViewById(R.id.filtroInformatica);
        filtroClases = (ImageButton) findViewById(R.id.filtroClases);
        filtroMenaje = (ImageButton) findViewById(R.id.filtroMenaje);

        //ListView Demandas y Ofertas
        listViewDemandas= (ListView) findViewById(R.id.lvDemandas);
        listViewOfertas= (ListView) findViewById(R.id.lvOfertas);

        //Declaramos Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        Toolbar menu =(Toolbar) findViewById(R.id.toolbar);//importar como v7 para q no de error
        setSupportActionBar(menu);





        //declaramos el Arraylist aqui porque sino tiene un bug que duplica la list view de forma exponencial al volver a cargar la Activity
        perfiles = new ArrayList<>();
        perfiles.add(new Perfil("Nacho Jimenez","ncassinello@gmail.com","1234","7ºG",1000,"913140885",R.drawable.all));
        perfiles.add(new Perfil("Cristinini","cristinini@gmail.com","1234","1ºH",1000,"91548775",R.drawable.all));
        perfiles.add(new Perfil("PaulaCR7","paulaCR7@gmail.com","1234","13ºA",1000,"911254889",R.drawable.all));

        pedidos = new ArrayList<>();
        pedidos.add(new Pedido(0,"Formatear Ordenador",perfiles.get(0),"dinero","informatica","Necesito que me formateis el ordenador","demanda"));
        pedidos.add(new Pedido(1,"Cuidar a mis hijos",perfiles.get(1),"favor", "compañia","Salgo esta noche y encesito niñera","demanda"));
        pedidos.add(new Pedido(2,"Ver el Madrid",perfiles.get(2),"favor","compañia","Ofrezco salon y futbol a cambio de alguien con quien verlo", "oferta"));
        pedidos.add(new Pedido(3,"Clases de XML",perfiles.get(0),"dinero","clases","Necesito clases de XML avanzadas","demanda"));

        //funcionalidad de los adapters
        listViewOfertas.setAdapter(new Adapter(this,seleccionarLista(pedidos,"oferta")));
        listViewDemandas.setAdapter(new Adapter(this,seleccionarLista(pedidos,"demanda")));

        //funcionalidad cuando se pulsa un elemento del listView
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

                Intent intent = new Intent(view.getContext(),DetallesPedido.class);
                intent.putExtra("PEDIDO",seleccionarLista(pedidos,"demanda").get(position));
                pedidoIndex = position;

                startActivityForResult(intent,1);
            }
        });

        //Manejamos los tabhost
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

        //MÉTODOS PARA FILTRAR POR CATEGORIAS

        filtroAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Volvemos el adapter al original diferenciandolo por oferta o compañia
                listViewOfertas.setAdapter(new Adapter(ListViewActivity.this, seleccionarLista(pedidos,"oferta")));
                listViewDemandas.setAdapter(new Adapter(ListViewActivity.this, seleccionarLista(pedidos,"demanda")));
            }
        });

        filtroAmigos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Recorremos el array y seleccionamos solo los que son de compañia luego lo dividimos en oferta y demanda
                ArrayList<Pedido> pedidosFiltradosCompania = new ArrayList<>();

                for (Pedido p:pedidos)
                {
                    if (p.getCategoria().equalsIgnoreCase("compañia"))
                    {
                        pedidosFiltradosCompania.add(p);
                    }
                }
                listViewOfertas.setAdapter(new Adapter(ListViewActivity.this, seleccionarLista(pedidosFiltradosCompania,"oferta")));
                listViewDemandas.setAdapter(new Adapter(ListViewActivity.this,seleccionarLista(pedidosFiltradosCompania,"demanda")));
            }
        });

        filtroInformatica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Recorremos el array y seleccionamos solo los que son de Informatica,luego lo dividimos en oferta y demanda

                ArrayList<Pedido> pedidosFiltradosInformatica = new ArrayList<>();

                for (Pedido p:pedidos)
                {
                    if (p.getCategoria().equalsIgnoreCase("informatica"))
                    {
                        pedidosFiltradosInformatica.add(p);
                    }
                }
                listViewOfertas.setAdapter(new Adapter(ListViewActivity.this,seleccionarLista(pedidosFiltradosInformatica,"oferta")));
                listViewDemandas.setAdapter(new Adapter(ListViewActivity.this, seleccionarLista(pedidosFiltradosInformatica,"demanda")));

            }
        });

        filtroClases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Recorremos el array y seleccionamos solo los que son de Clase, luego lo dividimos en oferta y demanda
                ArrayList<Pedido> pedidosFiltradosClases = new ArrayList<>();

                for (Pedido p:pedidos)
                {
                    if (p.getCategoria().equalsIgnoreCase("clases"))
                    {
                        pedidosFiltradosClases.add(p);
                    }
                }
                listViewOfertas.setAdapter(new Adapter(ListViewActivity.this,seleccionarLista(pedidosFiltradosClases,"oferta")));
                listViewDemandas.setAdapter(new Adapter(ListViewActivity.this, seleccionarLista(pedidosFiltradosClases,"demanda")));
            }
        });

        filtroMenaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Recorremos el array y seleccionamos solo los que son de Hogar/Menaje, luego lo dividimos en oferta y demanda
                ArrayList<Pedido> pedidosFiltradosHM = new ArrayList<>();

                for (Pedido p:pedidos)
                {
                    if (p.getCategoria().equalsIgnoreCase("hogar/menaje"))
                    {
                        pedidosFiltradosHM.add(p);
                    }
                }
                listViewOfertas.setAdapter(new Adapter(ListViewActivity.this,seleccionarLista(pedidosFiltradosHM,"oferta")));
                listViewDemandas.setAdapter(new Adapter(ListViewActivity.this, seleccionarLista(pedidosFiltradosHM,"demanda")));
            }
        });

        floatButtonDemanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(ListViewActivity.this, CrearDemandaActivity.class);
                startActivity(i);
            }
        });

        floatButtonOferta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(ListViewActivity.this, CrearOfertaActivity.class);
                startActivity(i);
            }
        });

        //NavDrawer();
    }


    //creamos el menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        inflater =  getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    //para saber que opcion del menu se selecciona
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.perfil:
                i = new Intent(ListViewActivity.this, PerfilActivity.class);
               //i.putExtra("PEDIDO",seleccionarLista(pedidos,"oferta").get(position));
                startActivity(i);
                break;
            case R.id.misDemandas:
                i= new Intent(ListViewActivity.this, PerfilActivity.class);
                startActivity(i);
                break;
            case R.id.misOfertas:
                i= new Intent(ListViewActivity.this, PerfilActivity.class);
                startActivity(i);
                break;
            case R.id.misNotificaciones:
                i= new Intent(ListViewActivity.this, PerfilActivity.class);
                startActivity(i);
                break;

            case R.id.menuSalir://hacer case por opcion
                FirebaseAuth.getInstance().signOut();
                finish();
                i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;
        }
        return true;
    }

    //funcion para filtrar por el oferta/demanda
    public ArrayList<Pedido> seleccionarLista(ArrayList<Pedido> pedidos, String filtro)
    {
        ArrayList<Pedido> pedidosFiltrados = new ArrayList<>();
        for (Pedido p:pedidos)
        {
            if (p.getOferDeman().equalsIgnoreCase(filtro))
            {
                pedidosFiltrados.add(p);
            }
        }
        return pedidosFiltrados;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){/*
                data.getSerializableExtra("PEDIDO");
                pedidos.get(pedidoIndex) = (Pedido) data.getSerializableExtra("PEDIDO");
            */
            }
        }
    }









    //Navigation Drawer
    private void NavDrawer()
    {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        {
            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            public void onDrawerClosed(View drawerView)
            {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }
        };
        // Drawer Toggle Object Made
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    };



}
