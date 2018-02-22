package com.paulapps.kereseresapp.activities.ver_pedidos;

import android.app.AlertDialog;
import android.app.Dialog;
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
import com.paulapps.kereseresapp.Adapters.Adapter;
import com.paulapps.kereseresapp.Adapters.Adapter2;
import com.paulapps.kereseresapp.R;
import com.paulapps.kereseresapp.activities.ListViewActivity;
import com.paulapps.kereseresapp.activities.login_signup.MainActivity;
import com.paulapps.kereseresapp.activities.perfil.PerfilActivity;
import com.paulapps.kereseresapp.model.Pedido;
import com.paulapps.kereseresapp.model.Perfil;

import java.util.ArrayList;

public class verPedidosListViewActivity extends AppCompatActivity {

    private ArrayList<Perfil> perfiles;
    private ArrayList<Pedido> pedidos;
    private int pedidoIndex;
    private MenuInflater inflater;
    private ListView lvOfertasVerPedido, lvDemandasVerPedido;
    private Toolbar toolbar;
    AlertDialog.Builder builder;

    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_pedidos_listview);


        Toolbar menu = (Toolbar) findViewById(R.id.toolbar);//importar como v7 para q no de error
        setSupportActionBar(menu);

        lvDemandasVerPedido = findViewById(R.id.lvDemandasVerPedido);
        lvOfertasVerPedido = findViewById(R.id.lvOfertasVerPedido);

        perfiles = new ArrayList<>();
        perfiles.add(new Perfil("Nacho Jimenez", "ncassinello@gmail.com", "1234", "7ºG", 1000, "913140885", R.drawable.all));

        pedidos = new ArrayList<>();
        //Todos los perfiles.get(0) a 0 para que salga el mismo usuario
        pedidos.add(new Pedido(0, "Formatear Ordenador", perfiles.get(0), "dinero", "informatica", "Necesito que me formateis el ordenador", "demanda"));
        pedidos.add(new Pedido(1, "Cuidar a mis hijos", perfiles.get(0), "favor", "compañia", "Salgo esta noche y encesito niñera", "demanda"));
        pedidos.add(new Pedido(2, "Ver el Madrid", perfiles.get(0), "favor", "compañia", "Ofrezco salon y futbol a cambio de alguien con quien verlo", "oferta"));
        pedidos.add(new Pedido(3, "Clases de XML", perfiles.get(0), "dinero", "clases", "Necesito clases de XML avanzadas", "demanda"));

        //funcionalidad de los adapters
        // lvOfertasVerPedido.setAdapter(new Adapter2(this, seleccionarLista(pedidos,"oferta")));
        //lvDemandasVerPedido.setAdapter(new Adapter(this, seleccionarLista(pedidos, "demanda")));

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
    }

    //creamos el menu
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    //para saber que opcion del menu se selecciona
    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()) {
            case R.id.miMainInicio:
                i = new Intent(verPedidosListViewActivity.this, ListViewActivity.class);
                startActivity(i);
                break;
            case R.id.perfil:
                i = new Intent(verPedidosListViewActivity.this, PerfilActivity.class);
                //i.putExtra("PEDIDO",seleccionarLista(pedidos,"oferta").get(position));
                startActivity(i);
                break;
            case R.id.misPedidos:
                i = new Intent(verPedidosListViewActivity.this, verPedidosListViewActivity.class);
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
                        Toast.makeText(verPedidosListViewActivity.this, "Deleting the account was correct", Toast.LENGTH_SHORT).show();
                        i= new Intent(verPedidosListViewActivity.this, MainActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(verPedidosListViewActivity.this, "The account could not be deleted", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }
    }
}
