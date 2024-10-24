package com.example.agromanager2_0.labores;

import java.util.*;

public class LaborStorage {
    private static List<Labor> labores = new ArrayList<>();

    public static void addLabor(Labor labor) {
        for (Labor l : labores) {
            if (l.getNombreLabor().equals(labor.getNombreLabor()) && l.getFecha().equals(labor.getFecha())) {

                return;
            }
        }
        labores.add(labor);
    }

    public static List<Labor> getLabores() {
        return new ArrayList<>(labores);
    }
}
