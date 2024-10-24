package com.example.agromanager2_0.aplicaciones;



import java.util.*;

public class AplicacionStorage {

    private static List<Aplicacion> aplicaciones = new ArrayList<>();

    public static void addAplicacion(Aplicacion aplicacion) {
        for (Aplicacion a : aplicaciones) {
            if (a.getNombreAplicacion().equals(aplicacion.getNombreAplicacion()) && a.getFechaAplicacion().equals(aplicacion.getFechaAplicacion())) {

                return;
            }
        }
        aplicaciones.add(aplicacion);
    }

    public static List<Aplicacion> getAplicacion() {
        return new ArrayList<>(aplicaciones);
    }


}
