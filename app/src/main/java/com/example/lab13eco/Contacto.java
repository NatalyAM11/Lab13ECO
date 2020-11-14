package com.example.lab13eco;

public class Contacto {

    private String nombre, telefono,idUser, idC;

    public Contacto (String nombre, String telefono, String idUser, String idC){
        this.nombre=nombre;
        this.telefono=telefono;
        this.idUser=idUser;
        this.idC=idC;
    }

    public Contacto (){

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdC() {
        return idC;
    }

    public void setIdC(String idC) {
        this.idC = idC;
    }
}
