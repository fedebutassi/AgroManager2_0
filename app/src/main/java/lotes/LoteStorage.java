package lotes;

import java.util.ArrayList;
import java.util.List;

//**clase auxiliar para almacenar los lotes**//

public class LoteStorage {
    // Lista estática para almacenar los lotes creados temporalmente
    private static List<Lote> lotes = new ArrayList<>();

    public static void addLote(Lote lote) {
        lotes.add(lote);
    }

    public static List<Lote> getLotes() {
        return lotes;
    }

}



