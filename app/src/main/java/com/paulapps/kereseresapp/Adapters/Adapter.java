package com.paulapps.kereseresapp.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.paulapps.kereseresapp.R;
import com.paulapps.kereseresapp.model.Perfil;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {

    Context context;
    ArrayList<Perfil> perfil;
    private static LayoutInflater inflater = null;
    private String titulos[];
    private String nombres[];


    public Adapter(Context context, ArrayList<Perfil> perfil) {
        this.context = context;
        this.perfil = perfil;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return perfil.size();
    }

    @Override
    public Perfil getItem(int position) {
        return perfil.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listview;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            listview = inflater.inflate(R.layout.celda_listview, null);
        } else {
            listview = convertView;
        }
        TextView titulo = (TextView) listview.findViewById(R.id.tvTitulo);
        TextView nombre = (TextView) listview.findViewById(R.id.tvNombre);
        titulo.setText(titulos[position]);
        nombre.setText(nombres[position]);

        return listview;
    }
}
