package com.example.agromanager2_0.labores;

// Representación de una Labor
public class Labor {
    private final String nombreLabor;
    private final String fecha;
    private final String lote;
    private final String descripcion;

    public Labor(String nombreLabor, String fecha, String descripcion) {
        this.nombreLabor = nombreLabor;
        this.fecha = fecha;
        this.lote = "";
        this.descripcion = descripcion;
    }

    public String getNombreLabor() {
        return nombreLabor;
    }

    public String getFecha() {
        return fecha;
    }

    public String getLote() {
        return lote;
    }

    public String getDescripcion() {
        return descripcion;
    }
}