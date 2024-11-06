package com.example.agromanager2_0.cultivos;

public class Cultivo {

    private final String Cultivo;
    private final String fechaCultivo;

    private final String areaCubiertaPorCultivo;
    private final String descripcionCultivo;

    public Cultivo(String Cultivo, String fechaAplicacionCultivo, String areaCubiertaPorCultivo, String descripcionCultivo) {
        this.Cultivo = Cultivo;
        this.fechaCultivo = fechaAplicacionCultivo;

        this.areaCubiertaPorCultivo = areaCubiertaPorCultivo;
        this.descripcionCultivo = descripcionCultivo;
    }

    public String getCultivo() {
        return Cultivo;
    }

    public String getFechaCultivo() {
        return fechaCultivo;
    }

    public String getLoteCultivo() {
        return null;
    }

    public String getAreaCubiertaPorCultivo() {
        return areaCubiertaPorCultivo;
    }

    public String getDescripcionCultivo() {
        return descripcionCultivo;
    }

    public String getNombre() {
        return null;
    }

}
