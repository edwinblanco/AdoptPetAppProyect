package com.example.adoptpetapp.model.entity;

import android.telephony.mbms.StreamingServiceInfo;

import java.io.Serializable;

public class Mascota implements Serializable {
    private String idMascota;
    private String urlImagen;
    private String nombre;
    private String genero;
    private int edad;
    private String descripcion;
    private String estado;
    private String contacto;

    public Mascota() {
    }

    public Mascota(String urlImagen, String nombre, String genero, int edad, String descripcion, String estado, String contacto) {
        this.idMascota = idMascota;
        this.urlImagen = urlImagen;
        this.nombre = nombre;
        this.genero = genero;
        this.edad = edad;
        this.descripcion = descripcion;
        this.estado = estado;
        this.idMascota = idMascota;
        this.contacto = contacto;
    }

    public String getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(String idMascota) {
        this.idMascota = idMascota;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }
}
