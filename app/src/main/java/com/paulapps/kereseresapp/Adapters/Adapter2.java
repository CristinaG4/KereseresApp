package com.paulapps.kereseresapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.paulapps.kereseresapp.R;
import com.paulapps.kereseresapp.model.Pedido;
import com.paulapps.kereseresapp.model.Perfil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by crist on 20/02/2018.
 */

public class Adapter2 extends ArrayAdapter<Pedido> {

    Pedido pedido;
    Perfil perfil;


    public Adapter2(Context context, int resource, List<Pedido> objects){
        super(context,resource,objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.celda_listview,parent,false);
        }
//

        //Declaramos todos los elementos de la celda del listView
        TextView  tvTituloVerPedido = (TextView) convertView.findViewById(R.id.tvTituloVerPedido);
        TextView  tvEmailVerPedido = (TextView) convertView.findViewById(R.id.tvEmailVerPedido);
        ImageView imgViewProfile = convertView.findViewById(R.id.imgViewProfile);

        pedido = getItem(position);


        //Introducimos los valores en el xml
        tvEmailVerPedido.setText(pedido.getPerfil().getEmail());
        tvTituloVerPedido.setText(pedido.getTitulo());
        imgViewProfile.setImageResource(seleccionarImagenTipo());

        return convertView;
    }


    //funcion que segun el tipo de pedido le dispone un imagen personalizada
    private  int seleccionarImagenTipo(){
        if(pedido.getCategoria().equalsIgnoreCase("compañia")){
            return R.drawable.amigos;
        }else if (pedido.getCategoria().equalsIgnoreCase("informatica")){
            return R.drawable.ordenador;
        }else if (pedido.getCategoria().equalsIgnoreCase("clases")){
            return R.drawable.clases;
        }else if (pedido.getCategoria().equalsIgnoreCase("hogar")){
            return R.drawable.herramientas;
        }
        return 0;
    }

    //funcion que segun el tipo de pago le dispone un imagen personalizada
   /* private int seleccionarImagenPago(int position){
        if(pedido.get(position).getCategoria().equalsIgnoreCase("dinero")){
            return R.drawable.amigos;
        }else if (pedido.get(position).getCategoria().equalsIgnoreCase("favor")){
            return R.drawable.herramientas;
        }

        return 0;
    }*/
}
