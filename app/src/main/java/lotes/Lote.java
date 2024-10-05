package lotes;

//**representacion de un lote**//

import com.google.android.gms.maps.model.LatLng;

public class Lote {
    private String nombre;
    private String superficie;
    private LatLng ubicacion; // Añadir la ubicación

    // Constructor que incluye la ubicación
    public Lote(String nombre, String superficie, LatLng ubicacion) {
        this.nombre = nombre;
        this.superficie = superficie;
        this.ubicacion = ubicacion;
    }

    // Getters para los datos
    public String getNombre() {
        return nombre;
    }

    public String getSuperficie() {
        return superficie;
    }

    public LatLng getUbicacion() {
        return ubicacion;
    }
}

