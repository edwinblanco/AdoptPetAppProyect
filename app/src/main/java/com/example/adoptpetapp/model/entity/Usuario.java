package com.example.adoptpetapp.model.entity;

public class Usuario {
    private String nombreCompleto;
    private String direccion;
    private int edad;
    private String correo;
    private String contrasena;

    public Usuario() {
    }

    public Usuario(String nombreCompleto, String direccion, int edad, String correo, String contrasena) {
        this.nombreCompleto = nombreCompleto;
        this.direccion = direccion;
        this.edad = edad;
        this.correo = correo;
        this.contrasena = contrasena;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
