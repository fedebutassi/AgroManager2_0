package com.example.agromanager2_0.cultivos;

import java.util.*;

public class CultivoStorage {

    private static final List<Cultivo> cultivos = new ArrayList<>();

    public static List<Cultivo> getCultivos() {
        return new ArrayList<>(cultivos);
    }

}
