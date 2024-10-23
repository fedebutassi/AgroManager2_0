package com.example.agromanager2_0.lotes;

//**representacion de un lote**//

import com.google.android.gms.maps.model.LatLng;

public class Lote {
    private String nombre;
    private double hectareas; // Asegúrate de que sea double, no int
    private double latitud;
    private double longitud;
    private LatLng ubicacion;

    public Lote(String nombre,double hectareas, double latitud, double longitud, LatLng ubicacion) {
        this.nombre = nombre;
        this.hectareas = hectareas;
        this.latitud = latitud;
        this.longitud = longitud;
        this.ubicacion = ubicacion;
    }

    public Lote(String nombre) {
    }


    public String getNombre() {
        return nombre;
    }

    public double getHectareas() {
        return hectareas;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public LatLng getUbicacion() {
        return ubicacion;
    }
}