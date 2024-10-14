package com.example.agromanager2_0.aplicaciones;


import static com.example.agromanager2_0.lotes.LoteStorage.lotes;


import com.example.agromanager2_0.labores.Labor;

import java.util.ArrayList;
import java.util.List;

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
