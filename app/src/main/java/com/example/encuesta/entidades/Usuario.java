package com.example.encuesta.entidades;

public class Usuario {
    private Integer NroEncuesta;
    private String Nombre;
    private String Fecha;

    public Integer getNroEncuesta() {
        return NroEncuesta;
    }

    public void setNroEncuesta(Integer nroEncuesta) {
        NroEncuesta = nroEncuesta;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }
}
