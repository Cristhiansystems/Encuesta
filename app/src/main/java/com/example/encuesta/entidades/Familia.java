package com.example.encuesta.entidades;

public class Familia {
    private Integer id;
    private String nombre;
    private String apellidos;
    private Integer genero;
    private Integer parentesco;
    private String referencia;
    private String telefono;
    private String actividad_laboral;
    private String ingreso_mensual;

    public Familia(){

    }

    public Familia(Integer id, String nombre, String apellidos, Integer genero, Integer parentesco, String telefono, String actividad_laboral, String ingreso_mensual) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.genero = genero;
        this.parentesco = parentesco;
        this.telefono = telefono;
        this.actividad_laboral = actividad_laboral;
        this.ingreso_mensual = ingreso_mensual;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public Integer getGenero() {
        return genero;
    }

    public Integer getParentesco() {
        return parentesco;
    }

    public String getReferencia() {
        return referencia;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getActividad_laboral() {
        return actividad_laboral;
    }

    public String getIngreso_mensual() {
        return ingreso_mensual;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setGenero(Integer genero) {
        this.genero = genero;
    }

    public void setParentesco(Integer parentesco) {
        this.parentesco = parentesco;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setActividad_laboral(String actividad_laboral) {
        this.actividad_laboral = actividad_laboral;
    }

    public void setIngreso_mensual(String ingreso_mensual) {
        this.ingreso_mensual = ingreso_mensual;
    }
}
