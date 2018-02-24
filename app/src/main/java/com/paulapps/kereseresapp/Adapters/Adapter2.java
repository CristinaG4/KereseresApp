package com.paulapps.kereseresapp.Adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.paulapps.kereseresapp.R;
import com.paulapps.kereseresapp.model.Pedido;
import com.paulapps.kereseresapp.model.Perfil;

import java.util.ArrayList;

/**
 * Created by crist on 20/02/2018.
 */

public class Adapter2 extends BaseAdapter {

    Context context;
    ArrayList<Pedido> pedido;
    private static LayoutInflater inflater = null;
    TextView tvTituloVerPedido, tvEmailVerPedido;
    ImageView imgViewProfile;


    public Adapter2(Context context, ArrayList<Pedido> pedido) {
        this.context = context;
        this.pedido = pedido;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final View listview = inflater.inflate(R.layout.celda_ver_pedidos,null);

        //Declaramos todos los elementos de la celda del listView
        tvTituloVerPedido = (TextView) listview.findViewById(R.id.tvTituloVerPedido);
        tvEmailVerPedido = (TextView) listview.findViewById(R.id.tvEmailVerPedido);
        imgViewProfile = listview.findViewById(R.id.imgViewProfile);

        //Introducimos los valores en el xml
        tvEmailVerPedido.setText(pedido.get(position).getPerfil().getEmail()/*perfil.get(position).getNombre()*/);
        tvTituloVerPedido.setText(pedido.get(position).getTitulo());
        imgViewProfile.setImageResource(seleccionarImagenTipo(position));

        return listview;
    }


    @Override
    public int getCount() {
        return pedido.size();
    }

    @Override
    public Perfil getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //funcion que segun el tipo de pedido le dispone un imagen personalizada
    private  int seleccionarImagenTipo(int position){
        if(pedido.get(position).getCategoria().equalsIgnoreCase("compañia")){
            return R.drawable.amigos;
        }else if (pedido.get(position).getCategoria().equalsIgnoreCase("informatica")){
            return R.drawable.ordenador;
        }else if (pedido.get(position).getCategoria().equalsIgnoreCase("clases")){
            return R.drawable.clases;
        }else if (pedido.get(position).getCategoria().equalsIgnoreCase("hogar")){
            return R.drawable.herramientas;
        }
        return 0;
    }

    //funcion que segun el tipo de pago le dispone un imagen personalizada
    private int seleccionarImagenPago(int position){
        if(pedido.get(position).getCategoria().equalsIgnoreCase("dinero")){
            return R.drawable.amigos;
        }else if (pedido.get(position).getCategoria().equalsIgnoreCase("favor")){
            return R.drawable.herramientas;
        }

        return 0;
    }
}
