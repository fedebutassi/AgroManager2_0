package com.example.agromanager2_0.labores;

import java.util.*;

public class LaborStorage {
    private static final List<Labor> labores = new ArrayList<>();

    public static List<Labor> getLabores() {
        return new ArrayList<>(labores);
    }
}
