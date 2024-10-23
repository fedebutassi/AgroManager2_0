package com.example.agromanager2_0.labores;

// Representación de una Labor
public class Labor {
    private String nombreLabor;
    private String fecha;
    private String lote;
    private String descripcion;

    public Labor(String nombreLabor, String fecha, String lote, String descripcion) {
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