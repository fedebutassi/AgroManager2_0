package com.example.agromanager2_0.cultivos;

public class Cultivo {

    private String Cultivo;
    private String fechaCultivo;
    private String lote;
    private String areaCubiertaPorCultivo;
    private String descripcionCultivo;

    public Cultivo(String Cultivo, String fechaAplicacionCultivo, String lote, String areaCubiertaPorCultivo, String descripcionCultivo) {
        this.Cultivo = Cultivo;
        this.fechaCultivo = fechaAplicacionCultivo;
        this.lote = lote;
        this.areaCubiertaPorCultivo = areaCubiertaPorCultivo;
        this.descripcionCultivo = descripcionCultivo;
    }

    public String getCultivo() {
        return Cultivo;
    }

    public String getFechaCultivo() {
        return fechaCultivo;
    }

    public String getLote() {
        return lote;
    }

    public String getAreaCubiertaPorCultivo() {
        return areaCubiertaPorCultivo;
    }

    public String getDescripcionCultivo() {
        return descripcionCultivo;
    }
}
