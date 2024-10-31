package com.example.agromanager2_0.lotes;


import com.google.android.gms.maps.model.LatLng;

public class Lote {
    private String nombre;
    private double hectareas;
    private LatLng ubicacion;

    public Lote(String nombre, double hectareas, LatLng ubicacion) {
        this.nombre = nombre;
        this.hectareas = hectareas;
        this.ubicacion = ubicacion;
    }

    public Lote() {
    }


    public String getNombre() {
        return nombre;
    }

    public double getHectareas() {
        return hectareas;
    }

    public LatLng getUbicacion() {
        return ubicacion;
    }
}