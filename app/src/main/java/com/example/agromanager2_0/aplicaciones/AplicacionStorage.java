package com.example.agromanager2_0.aplicaciones;



import java.util.*;

public class AplicacionStorage {

    public static final List<Aplicacion> aplicaciones = new ArrayList<>();

    public static List<Aplicacion> getAplicacion() {
        return new ArrayList<>(aplicaciones);
    }


}
