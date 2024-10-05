package labores;

import java.util.ArrayList;
import java.util.List;

public class LaborStorage {
    private static List<Labor> labores = new ArrayList<>();

    public static void addLabor(Labor labor) {
        for (Labor l : labores) {
            if (l.getNombre().equals(labor.getNombre()) && l.getFecha().equals(labor.getFecha())) {

                return;
            }
        }
        labores.add(labor);
    }

    public static List<Labor> getLabores() {
        return new ArrayList<>(labores);
    }
}

