package com.example.lab13eco;

public class Usuario {

    private String nombre,id;

    public Usuario(String nombre, String id){
    this.nombre=nombre;
    this.id=id;

    }

    public Usuario(){

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
