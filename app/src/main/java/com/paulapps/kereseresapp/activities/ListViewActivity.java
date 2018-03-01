package com.paulapps.kereseresapp.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paulapps.kereseresapp.Adapters.Adapter;
import com.paulapps.kereseresapp.R;
import com.paulapps.kereseresapp.activities.crear_pedidos.CrearDemandaActivity;
import com.paulapps.kereseresapp.activities.crear_pedidos.CrearOfertaActivity;
import com.paulapps.kereseresapp.activities.login_signup.MainActivity;
import com.paulapps.kereseresapp.activities.perfil.PerfilActivity;
import com.paulapps.kereseresapp.activities.ver_pedidos.verPedidosListViewActivity;
import com.paulapps.kereseresapp.model.FirebaseReferences;
import com.paulapps.kereseresapp.model.Pedido;
import com.paulapps.kereseresapp.model.Perfil;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {

    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseDatabase firebase;
    private GoogleApiClient googleApiClient;
    private Button filtroAll, filtroAmigos, filtroInformatica, filtroClases, filtroMenaje;
    private ListView listViewDemandas, listViewOfertas;
    private Perfil perfil;
    private ArrayList<Pedido> pedidos;
    private Adapter adapterOfertas;
    private Adapter adapterDemandas;
    private int pedidoIndex;
    private Toolbar toolbar;
    private  ActionBarDrawerToggle toggle;
    private  DrawerLayout drawer;
    private MenuInflater inflater;
    private FloatingActionMenu floatButtonPrincipal;
    private com.github.clans.fab.FloatingActionButton floatButtonDemanda, floatButtonOferta;
    private AlertDialog.Builder builder;
    private ProgressDialog Prodialog;


    //Firebase Instance variables
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mAuth;

    Intent i;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        // getSupportActionBar().hide();



        Prodialog=new ProgressDialog(this);

        //initialize firebase components
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();

        //Botones Flotantes
        floatButtonPrincipal = findViewById(R.id.floatButtonPrincipal);
        floatButtonDemanda = findViewById(R.id.floatButtonDemanda);
        floatButtonOferta = findViewById(R.id.floatButtonOferta);
        toolbar = findViewById(R.id.toolbar);

        //toolbar.setTitleTextColor(0xFFF0);

        //Declaramos Toolbar
        setSupportActionBar(toolbar);
        Toolbar menu =(Toolbar) findViewById(R.id.toolbar);//importar como v7 para q no de error
        setSupportActionBar(menu);

        //Barra para filtrar
        filtroAll =  findViewById(R.id.filtroAll);
        filtroAmigos = findViewById(R.id.filtroAmigos);
        filtroInformatica = findViewById(R.id.filtroInformatica);
        filtroClases =  findViewById(R.id.filtroClases);
        filtroMenaje = findViewById(R.id.filtroMenaje);

        //ListView Demandas y Ofertas
        listViewDemandas= (ListView) findViewById(R.id.lvDemandas);
        listViewOfertas= (ListView) findViewById(R.id.lvOfertas);

        //declaramos el Arraylist aqui porque sino tiene un bug que duplica la list view de forma exponencial al volver a cargar la Activity
        pedidos = new ArrayList<>();



        adapterDemandas = new Adapter(this,R.layout.celda_listview,seleccionarLista(pedidos,"demanda"));
        adapterOfertas = new Adapter(this,R.layout.celda_listview, seleccionarLista(pedidos,"oferta"));

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Pedido pedido = dataSnapshot.getValue(Pedido.class);
                if (pedido.getOferDeman().equalsIgnoreCase("oferta")){
                    adapterOfertas.add(pedido);
                }else if(pedido.getOferDeman().equalsIgnoreCase("demanda")){
                    adapterDemandas.add(pedido);
                }
                pedidos.add(pedido);
            }

            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            public void onCancelled(DatabaseError databaseError) {}
        };
        mDatabaseReference.child(FirebaseReferences.PEDIDO_REFERENCES).addChildEventListener(mChildEventListener);

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

        floatButtonDemanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListViewActivity.this,CrearDemandaActivity.class);
                intent.putExtra("PCD",perfil);
                startActivity(intent);
            }
        });
        floatButtonOferta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListViewActivity.this,CrearOfertaActivity.class);
                startActivity(i);
            }
        });

        //funcionalidad de los adapters
        listViewOfertas.setAdapter(adapterOfertas);
        listViewDemandas.setAdapter(adapterDemandas);

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
                //listViewOfertas.setAdapter(new Adapter(ListViewActivity.this, seleccionarLista(pedidos,"oferta")));
                //listViewDemandas.setAdapter(new Adapter(ListViewActivity.this, seleccionarLista(pedidos,"demanda")));

                //Control fondo filtro
                filtroAmigos.setBackgroundResource(R.drawable.amigos_fondo);
                filtroAll.setBackgroundResource(R.drawable.all);
                filtroClases.setBackgroundResource(R.drawable.clases_fondo);
                filtroInformatica.setBackgroundResource(R.drawable.ordenador_fondo);
                filtroMenaje.setBackgroundResource(R.drawable.herramientas_fondo);

                adapterDemandas.clear();
                adapterDemandas.addAll(seleccionarLista(pedidos,"demanda"));
                adapterOfertas.clear();
                adapterOfertas.addAll(seleccionarLista(pedidos,"oferta"));

            }
        });

        filtroAmigos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Recorremos el array y seleccionamos solo los que son de compañia luego lo dividimos en oferta y demanda
                ArrayList<Pedido> pedidosFiltrados = new ArrayList<>();

                //Control fondo filtro
                filtroAmigos.setBackgroundResource(R.drawable.amigos);
                filtroAll.setBackgroundResource(R.drawable.all_fondo);
                filtroClases.setBackgroundResource(R.drawable.clases_fondo);
                filtroInformatica.setBackgroundResource(R.drawable.ordenador_fondo);
                filtroMenaje.setBackgroundResource(R.drawable.herramientas_fondo);

                for (Pedido p:pedidos)
                {
                    if (p.getCategoria().equalsIgnoreCase("compañia"))
                    {
                        pedidosFiltrados.add(p);
                    }
                }
                //listViewOfertas.setAdapter(new Adapter(ListViewActivity.this, seleccionarLista(pedidosFiltradosCompania,"oferta")));
                //listViewDemandas.setAdapter(new Adapter(ListViewActivity.this,seleccionarLista(pedidosFiltradosCompania,"demanda")));
                adapterDemandas.clear();
                adapterDemandas.addAll(seleccionarLista(pedidosFiltrados,"demanda"));
                adapterOfertas.clear();
                adapterOfertas.addAll(seleccionarLista(pedidosFiltrados,"oferta"));
                //Query recentPostsQuery = mDatabaseReference.child(FirebaseReferences.PEDIDO_REFERENCES).child(FirebaseReferences.CAT_PERFIL_PREFERENCES).equalTo("compañia");
            }
        });

        filtroInformatica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Recorremos el array y seleccionamos solo los que son de Informatica,luego lo dividimos en oferta y demanda

                ArrayList<Pedido> pedidosFiltrados = new ArrayList<>();

                //Control fondo filtro
                filtroAmigos.setBackgroundResource(R.drawable.amigos_fondo);
                filtroAll.setBackgroundResource(R.drawable.all_fondo);
                filtroClases.setBackgroundResource(R.drawable.clases_fondo);
                filtroInformatica.setBackgroundResource(R.drawable.ordenador);
                filtroMenaje.setBackgroundResource(R.drawable.herramientas_fondo);

                for (Pedido p:pedidos)
                {
                    if (p.getCategoria().equalsIgnoreCase("informatica"))
                    {
                        pedidosFiltrados.add(p);
                    }
                }
                // listViewOfertas.setAdapter(new Adapter(ListViewActivity.this,seleccionarLista(pedidosFiltradosInformatica,"oferta")));
                // listViewDemandas.setAdapter(new Adapter(ListViewActivity.this, seleccionarLista(pedidosFiltradosInformatica,"demanda")));
                adapterDemandas.clear();
                adapterDemandas.addAll(seleccionarLista(pedidosFiltrados,"demanda"));
                adapterOfertas.clear();
                adapterOfertas.addAll(seleccionarLista(pedidosFiltrados,"oferta"));

            }
        });

        filtroClases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Recorremos el array y seleccionamos solo los que son de Clase, luego lo dividimos en oferta y demanda
                ArrayList<Pedido> pedidosFiltrados = new ArrayList<>();

                //Control fondo filtro
                filtroAmigos.setBackgroundResource(R.drawable.amigos_fondo);
                filtroAll.setBackgroundResource(R.drawable.all_fondo);
                filtroClases.setBackgroundResource(R.drawable.clases);
                filtroInformatica.setBackgroundResource(R.drawable.ordenador_fondo);
                filtroMenaje.setBackgroundResource(R.drawable.herramientas_fondo);

                for (Pedido p:pedidos)
                {
                    if (p.getCategoria().equalsIgnoreCase("clases"))
                    {
                        pedidosFiltrados.add(p);
                    }
                }
                // listViewOfertas.setAdapter(new Adapter(ListViewActivity.this,seleccionarLista(pedidosFiltradosClases,"oferta")));
                //  listViewDemandas.setAdapter(new Adapter(ListViewActivity.this, seleccionarLista(pedidosFiltradosClases,"demanda")));
                adapterDemandas.clear();
                adapterDemandas.addAll(seleccionarLista(pedidosFiltrados,"demanda"));
                adapterOfertas.clear();
                adapterOfertas.addAll(seleccionarLista(pedidosFiltrados,"oferta"));
            }
        });

        filtroMenaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Recorremos el array y seleccionamos solo los que son de Hogar/Menaje, luego lo dividimos en oferta y demanda
                ArrayList<Pedido> pedidosFiltrados = new ArrayList<>();

                //Control fondo filtro
                filtroAmigos.setBackgroundResource(R.drawable.amigos_fondo);
                filtroAll.setBackgroundResource(R.drawable.all_fondo);
                filtroClases.setBackgroundResource(R.drawable.clases_fondo);
                filtroInformatica.setBackgroundResource(R.drawable.ordenador_fondo);
                filtroMenaje.setBackgroundResource(R.drawable.herramientas);


                for (Pedido p:pedidos)
                {
                    if (p.getCategoria().equalsIgnoreCase("hogar"))
                    {
                        pedidosFiltrados.add(p);
                    }
                }
                // listViewOfertas.setAdapter(new Adapter(ListViewActivity.this,seleccionarLista(pedidosFiltradosHM,"oferta")));
                // listViewDemandas.setAdapter(new Adapter(ListViewActivity.this, seleccionarLista(pedidosFiltradosHM,"demanda")));
                adapterDemandas.clear();
                adapterDemandas.addAll(seleccionarLista(pedidosFiltrados,"demanda"));
                adapterOfertas.clear();
                adapterOfertas.addAll(seleccionarLista(pedidosFiltrados,"oferta"));

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
            case R.id.miMainInicio:
                i= new Intent(ListViewActivity.this, ListViewActivity.class);
                startActivity(i);
                break;
            case R.id.perfil:
                i = new Intent(ListViewActivity.this, PerfilActivity.class);
                i.putExtra("PERFIL",perfil);
                //i.putExtra("PEDIDO",seleccionarLista(pedidos,"oferta").get(position));
                startActivity(i);
                break;
            case R.id.misPedidos:
                i= new Intent(ListViewActivity.this, verPedidosListViewActivity.class);
                startActivity(i);
                break;
            case R.id.eliminarPerfil:
                //Creamos un alert dialog
                builder=new AlertDialog.Builder(this);
                builder.setTitle("Delete account");
                builder.setMessage("Are you sure to delete your account?");
                builder.setPositiveButton("Acept", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                eliminarCuenta();
                            }
                        });

                builder.setNegativeButton(android.R.string.cancel,null);
                Dialog dialog=builder.create();
                dialog.show();
                break;
            case R.id.menuSalir://hacer case por opcion
                FirebaseAuth.getInstance().signOut();
                finish();
                i = new Intent(this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.transition.login_in, R.transition.login_out);
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

    public void eliminarCuenta(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Prodialog.setMessage("Deleting account... Please wait");
                        Prodialog.show();
                        Toast.makeText(ListViewActivity.this, "Deleting the account was correct", Toast.LENGTH_SHORT).show();
                        i= new Intent(ListViewActivity.this, MainActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(ListViewActivity.this, "The account could not be deleted", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }
    }
}


