package com.paulapps.kereseresapp.model;


import android.widget.ImageView;

import java.io.Serializable;

public class Pedido implements Serializable{
    private int id;
    private String titulo;
    private Perfil perfil;
    private String tipoPago;
    private String categoria;
    private String descripcion;
    private String oferDeman;

    public Pedido() {
    }

    public Pedido(int id, String titulo, Perfil perfil, String tipoPago, String categoria, String descripcion, String oferDeman) {
        this.id = id;
        this.titulo = titulo;
        this. perfil = perfil;
        this.tipoPago = tipoPago;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.oferDeman = oferDeman;
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

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
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

    public String getOferDeman() {
        return oferDeman;
    }

    public void setOferDeman(String oferDeman) {
        this.oferDeman = oferDeman;
    }
}
