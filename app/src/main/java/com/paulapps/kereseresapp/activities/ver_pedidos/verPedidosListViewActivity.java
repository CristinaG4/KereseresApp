package com.paulapps.kereseresapp.activities.ver_pedidos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paulapps.kereseresapp.Adapters.Adapter2;
import com.paulapps.kereseresapp.R;
import com.paulapps.kereseresapp.activities.DetallesPedido;
import com.paulapps.kereseresapp.activities.EliminarCeldas.SwipeListViewTouchListener;
import com.paulapps.kereseresapp.activities.ListViewActivity;
import com.paulapps.kereseresapp.activities.login_signup.MainActivity;
import com.paulapps.kereseresapp.activities.perfil.PerfilActivity;
import com.paulapps.kereseresapp.model.FirebaseReferences;
import com.paulapps.kereseresapp.model.Pedido;
import com.paulapps.kereseresapp.model.Perfil;

import java.util.ArrayList;

public class verPedidosListViewActivity extends AppCompatActivity {

    private ArrayList<Pedido> pedidos;
    private Perfil perfil;
    private int pedidoIndex;
    private MenuInflater inflater;
    private ListView lvOfertasVerPedido, lvDemandasVerPedido;
    private Toolbar toolbar;
    private AlertDialog.Builder builder;
    private ProgressDialog Prodialog;
    private Adapter2 adapterOfertas;
    private Adapter2 adapterDemandas;
    //Firebase Instance variables
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mAuth;

    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_pedidos_listview);
        //perfil = (Perfil) getIntent().getSerializableExtra("PERFIL");

        //declaramos el Arraylist aqui porque sino tiene un bug que duplica la list view de forma exponencial al volver a cargar la Activity
        pedidos = new ArrayList<>();

        //initialize firebase components
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();


        Prodialog = new ProgressDialog(this);

        toolbar= (Toolbar) findViewById(R.id.toolbar);//importar como v7 para q no de error
        setSupportActionBar(toolbar);

        lvDemandasVerPedido = (ListView) findViewById(R.id.lvDemandasVerPedido);
        lvOfertasVerPedido = (ListView) findViewById(R.id.lvOfertasVerPedido);


        adapterDemandas = new Adapter2(this,R.layout.celda_listview,seleccionarLista(pedidos,"demanda"));
        adapterOfertas = new Adapter2(this,R.layout.celda_listview, seleccionarLista(pedidos,"oferta"));

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

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Pedido pedido = dataSnapshot.getValue(Pedido.class);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user.getEmail().equals(pedido.getPerfil().getEmail())){
                    if (pedido.getOferDeman().equalsIgnoreCase("oferta")){
                        adapterOfertas.add(pedido);
                    }else if(pedido.getOferDeman().equalsIgnoreCase("demanda")){
                        adapterDemandas.add(pedido);
                    }
                    pedidos.add(pedido);
                }

            }

            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            public void onCancelled(DatabaseError databaseError) {}
        };
        mDatabaseReference.child(FirebaseReferences.PEDIDO_REFERENCES).addChildEventListener(mChildEventListener);



        //funcionalidad de los adapters
        lvOfertasVerPedido.setAdapter(adapterOfertas);
        lvDemandasVerPedido.setAdapter(adapterDemandas);

        //funcionalidad cuando se pulsa un elemento del listView
        lvOfertasVerPedido.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(view.getContext(),DetallesPedido.class);
                //se le pasa a traves del intent el pedido entero que ha seleccionado
                intent.putExtra("PEDIDO",seleccionarLista(pedidos,"oferta").get(position));
                pedidoIndex = position;

                startActivityForResult(intent,1);
            }
        });

        lvDemandasVerPedido.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(view.getContext(),DetallesPedido.class);
                intent.putExtra("PEDIDO",seleccionarLista(pedidos,"demanda").get(position));
                pedidoIndex = position;

                startActivityForResult(intent,1);
            }
        });


        //funcionalidad cuando se pulsa un elemento del listView
        lvOfertasVerPedido.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(view.getContext(), VerDetallePedidoActivity.class);
                //se le pasa a traves del intent el pedido entero que ha seleccionado
                intent.putExtra("PEDIDO", seleccionarLista(pedidos, "oferta").get(position));
                pedidoIndex = position;

                startActivityForResult(intent, 1);
            }
        });

        lvDemandasVerPedido.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(view.getContext(), VerDetallePedidoActivity.class);
                intent.putExtra("PEDIDO", seleccionarLista(pedidos, "demanda").get(position));
                pedidoIndex = position;

                startActivityForResult(intent, 1);
            }
        });

        //Manejamos los tabhost
        TabHost tabs = findViewById(android.R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Ofertas", getDrawable(R.drawable.ic_launcher_background));
        tabs.addTab(spec);

        spec = tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Demandas", getDrawable(R.drawable.ic_launcher_background));
        tabs.addTab(spec);

        tabs.setCurrentTab(0);

        EliminarCeldaOfertas();
        EliminarCeldaDemandas();
    }

    //creamos el menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    //para saber que opcion del menu se selecciona
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miMainInicio:
                i = new Intent(verPedidosListViewActivity.this, ListViewActivity.class);
                startActivity(i);
                break;
            case R.id.perfil:
                i = new Intent(verPedidosListViewActivity.this, PerfilActivity.class);
                //i.putExtra("PEDIDO",seleccionarLista(pedidos,"oferta").get(position));
                i.putExtra("PERFIL",perfil);
                startActivity(i);
                break;
            case R.id.misPedidos:
                i = new Intent(verPedidosListViewActivity.this, verPedidosListViewActivity.class);
                startActivity(i);
                break;
            case R.id.eliminarPerfil:
                //Creamos un alert dialog
                builder = new AlertDialog.Builder(this);
                builder.setTitle("Delete account");
                builder.setMessage("Are you sure to delete your account?");
                builder.setPositiveButton("Acept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eliminarCuenta();
                    }
                });

                builder.setNegativeButton(android.R.string.cancel, null);
                Dialog dialog = builder.create();
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
    public ArrayList<Pedido> seleccionarLista(ArrayList<Pedido> pedidos, String filtro) {
        ArrayList<Pedido> pedidosFiltrados = new ArrayList<>();
        for (Pedido p : pedidos) {
            if (p.getOferDeman().equalsIgnoreCase(filtro)) {
                pedidosFiltrados.add(p);
            }
        }
        return pedidosFiltrados;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {/*
                data.getSerializableExtra("PEDIDO");
                pedidos.get(pedidoIndex) = (Pedido) data.getSerializableExtra("PEDIDO");
            */
            }
        }
    }

    public void eliminarCuenta() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Prodialog.setMessage("Deleting account... Please wait");
                        Prodialog.show();
                        Toast.makeText(verPedidosListViewActivity.this, "Deleting the account was correct", Toast.LENGTH_SHORT).show();
                        i = new Intent(verPedidosListViewActivity.this, MainActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(verPedidosListViewActivity.this, "The account could not be deleted", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }
    }


    //Deslizar item del listview de ofertas para borrarlo
    public void EliminarCeldaOfertas() {
        SwipeListViewTouchListener touchListener = new SwipeListViewTouchListener(lvOfertasVerPedido, new SwipeListViewTouchListener.OnSwipeCallback() {

            @Override
            public void onSwipeLeft(ListView listView, int[] reverseSortedPositions) {
                //Aqui ponemos lo que hara el programa cuando deslizamos un item a la izquierda
                pedidos.remove(reverseSortedPositions[0]);

                //con esta llamada se refresca la pantalla
                adapterOfertas.notifyDataSetChanged();
            }

            @Override
            public void onSwipeRight(ListView listView, int[] reverseSortedPositions) {

                //Aqui ponemos lo que hara el programa cuando deslizamos un item a la derecha
                pedidos.remove(reverseSortedPositions[0]);

                //con esta llamada se refresca la pantalla
                adapterOfertas.notifyDataSetChanged();
            }

        },true, false);

        //Escuchadores del listView
        lvOfertasVerPedido.setOnTouchListener(touchListener);
        //adapterOfertas.setOnScrollListener(touchListener.makeScrollListener());
    }

    //Deslizar item del listview de demandas para borrarlo
    public void EliminarCeldaDemandas() {
        SwipeListViewTouchListener touchListener = new SwipeListViewTouchListener(lvDemandasVerPedido, new SwipeListViewTouchListener.OnSwipeCallback() {
            // callback devuelve de llamada cuando el usuario ha indicado que le gustaría descartar,si uno o más elementos de la lista.
            // dismissLeft es para ver si la animación de desactivación está activada cuando el usuario desliza hacia la izquierda  y
            // lo mismo para la derecha

            @Override
            public void onSwipeLeft(ListView listView, int[] reverseSortedPositions) {
                //Aqui ponemos lo que hara el programa cuando deslizamos un item a la izquierda
                pedidos.remove(reverseSortedPositions[0]);

                //con esta llamada se refresca la pantalla
                adapterDemandas.notifyDataSetChanged();
            }

            @Override
            public void onSwipeRight(ListView listView, int[] reverseSortedPositions) {

                //Aqui ponemos lo que hara el programa cuando deslizamos un item a la derecha
                pedidos.remove(reverseSortedPositions[0]);
                //mDatabaseReference.child(FirebaseReferences.PEDIDO_REFERENCES).child();

                //con esta llamada se refresca la pantalla
                adapterDemandas.notifyDataSetChanged();
            }

        },true, false);

        //Escuchadores del listView
        lvDemandasVerPedido.setOnTouchListener(touchListener);

        //cuando hay un scroll
        //adapterOfertas.setOnScrollListener(touchListener.makeScrollListener());
    }
}






