package com.ulp.canchas.model;

import java.io.Serializable;

public class Tipo implements Serializable {

    private int id;
    private String nombre;

    public Tipo() {
    }

    public Tipo(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
