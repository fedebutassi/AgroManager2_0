package com.example.agromanager2_0.lotes;

import java.util.*;

//**clase auxiliar para almacenar los lotes**//

public class LoteStorage {
    // Lista estática para almacenar los lotes creados temporalmente
    public static List<Lote> lotes = new ArrayList<>();

    public static void addLote(Lote lote) {
        lotes.add(lote);
    }

    public static List<Lote> getLotes() {
        return lotes;
    }

}



