package com.example.agromanager2_0.aplicaciones;

public class Aplicacion {

    //Declaracion de variables

    private String nombreAplicacion;
    private String fechaAplicacion;
    private String lote;
    private String areaCubierta;
    private String descripcionAplicacacion;

    public Aplicacion(String nombreAplicacion, String fechaAplicacion, String lote, String areaCubierta, String descripcionAplicacacion) {
        this.nombreAplicacion = nombreAplicacion;
        this.fechaAplicacion = fechaAplicacion;
        this.lote = lote;
        this.areaCubierta = areaCubierta;
        this.descripcionAplicacacion = descripcionAplicacacion;
    }

    public String getNombreAplicacion() {
        return nombreAplicacion;
    }

    public String getFechaAplicacion() {
        return fechaAplicacion;
    }

    public String getLote() {
        return lote;
    }

    public String getAreaCubierta(){
        return areaCubierta;
    }

    public String getDescripcionAplicacacion() {
        return descripcionAplicacacion;
    }
}
