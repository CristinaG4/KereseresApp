package com.paulapps.kereseresapp.model;


import android.widget.ImageView;

public class Pedido {
    private int id;
    private String titulo;
    private Object Perfil;
    private ImageView tipoPago;
    private String categoria;
    private String descripcion;

    public Pedido(int id, String titulo, Object perfil, ImageView tipoPago, String categoria, String descripcion) {
        this.id = id;
        this.titulo = titulo;
        Perfil = perfil;
        this.tipoPago = tipoPago;
        this.categoria = categoria;
        this.descripcion = descripcion;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Object getPerfil() {
        return Perfil;
    }

    public void setPerfil(Object perfil) {
        Perfil = perfil;
    }

    public ImageView getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(ImageView tipoPago) {
        this.tipoPago = tipoPago;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
