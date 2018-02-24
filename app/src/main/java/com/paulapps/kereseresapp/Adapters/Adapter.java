package com.paulapps.kereseresapp.Adapters;

import com.paulapps.kereseresapp.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.paulapps.kereseresapp.model.Pedido;


import java.util.ArrayList;
import java.util.List;

public class Adapter extends ArrayAdapter<Pedido> {

    //Context context;
    //ArrayList<Pedido> pedido;
    //private static LayoutInflater inflater = null;
    Pedido pedido;

    public Adapter(Context context, int resource, List<Pedido> objects){
        super(context,resource,objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.celda_listview,parent,false);
        }

        //Declaramos todos los elementos de la celda del listView
        TextView titulo = (TextView) convertView.findViewById(R.id.tvTitulo);
        TextView nombre = (TextView) convertView.findViewById(R.id.tvNombre);
        ImageView tipo = convertView.findViewById(R.id.imgViewProfile);
        ImageView pago = convertView.findViewById(R.id.imgTipoPago);

        pedido = getItem(position);

        //Introducimos los valores en el xml
        nombre.setText(pedido.getPerfil().getNombre());
        titulo.setText(pedido.getTitulo());
        tipo.setImageResource(seleccionarImagenTipo());
        pago.setImageResource(seleccionarImagenPago());

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
    private int seleccionarImagenPago(){
        if(pedido.getTipoPago().equalsIgnoreCase("dinero")){
            return R.drawable.euro;
        }else if (pedido.getTipoPago().equalsIgnoreCase("favor")){
            return R.drawable.logo2;
        }

        return 0;
    }
}