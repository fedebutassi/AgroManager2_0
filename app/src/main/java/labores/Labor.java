package labores;

// Representación de una Labor
public class Labor {
    private String nombre;
    private String fecha;
    private String lote;
    private String descripcion;

    public Labor(String nombre, String fecha, String lote, String descripcion) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.lote = lote;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
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
