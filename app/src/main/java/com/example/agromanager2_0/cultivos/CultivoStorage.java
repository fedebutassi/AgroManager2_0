package com.example.agromanager2_0.cultivos;

import java.util.*;

public class CultivoStorage {

    private static List<Cultivo> cultivos = new ArrayList<>();

    public static void addCultivo(Cultivo Cultivo) {
        for (Cultivo c : cultivos) {
            if (c.getCultivo().equals(Cultivo.getCultivo()) && c.getFechaCultivo().equals(Cultivo.getFechaCultivo())) {

                return;
            }
        }
        cultivos.add(Cultivo);
    }

    public static List<Cultivo> getCultivos() {
        return new ArrayList<>(cultivos);
    }

}
