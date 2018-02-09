package com.paulapps.kereseresapp.model;

/**
 * Created by crist on 09/02/2018.
 */

public class Perfil {

    private String nombre;
    private String email;
    private String pass;
    private String apart;
    private int comCode;
    private String telf;
    private int foto;

    public Perfil() {
    }

    public Perfil(String nombre, String email, String pass, String apart, int comCode, String telf, int foto) {
        this.nombre = nombre;
        this.email = email;
        this.pass = pass;
        this.apart = apart;
        this.comCode = comCode;
        this.telf = telf;
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getApart() {
        return apart;
    }

    public void setApart(String apart) {
        this.apart = apart;
    }

    public int getComCode() {
        return comCode;
    }

    public void setComCode(int comCode) {
        this.comCode = comCode;
    }

    public String getTelf() {
        return telf;
    }

    public void setTelf(String telf) {
        this.telf = telf;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }
}
