package com.example.agromanager2_0.aplicaciones;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.*;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.agromanager2_0.R;
import com.example.agromanager2_0.database.MyDataBaseHelper;
import com.example.agromanager2_0.lotes.Lote;
import java.util.*;

public class AplicacionActivity extends AppCompatActivity {

    private EditText editTextNombreAplicacion, editTextDescripcionAplicacion;
    private EditText editTextAreaCubierta;
    private Spinner spinnerLotes;
    private Button fechaButton;
    private List<Aplicacion> listaAplicaciones;
    private String fechaSeleccionada = "";
    private MyDataBaseHelper miDb;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        miDb = new MyDataBaseHelper(this);

        setContentView(R.layout.activity_aplicacion2);

        // Habilitar el botón atrás en la ActionBar
        Toolbar toolbar = findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Aplicaciones");

        // Inicializar vistas
        editTextNombreAplicacion = findViewById(R.id.editTextNombreAplicacion);
        editTextDescripcionAplicacion = findViewById(R.id.editTextDescripcionAplicacion);
        spinnerLotes = findViewById(R.id.spinnerLotes);
        editTextAreaCubierta = findViewById(R.id.editTextAreaCubierta);
        fechaButton = findViewById(R.id.buttonSeleccionarFechaAplicacion);
        Button guardarButton = findViewById(R.id.buttonGuardarAplicacion);

        findViewById(R.id.recyclerViewAplicaciones);

        List<Lote> lotesDesdeDb = miDb.obtenerLotesLista();
        List<String> nombresLotes = new ArrayList<>();
        for (Lote lote : lotesDesdeDb) {
            if (lote != null && lote.getNombre() != null) {  // Verifica que lote y nombre no sean nulos
                nombresLotes.add(lote.getNombre());
            }
        }

        if (nombresLotes.isEmpty()) {
            Log.d("CultivoActivity", "No se encontraron lotes en la base de datos.");
            Toast.makeText(this, "No hay lotes disponibles.", Toast.LENGTH_SHORT).show();
        } else {
            Log.d("CultivoActivity", "Lotes disponibles: " + nombresLotes.size());

            // Configura el ArrayAdapter y asigna al Spinner
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombresLotes);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerLotes.setAdapter(adapter);
        }

        // Inicializar la lista de labores una sola vez
        listaAplicaciones = AplicacionStorage.getAplicacion();

        // Inicializa el RecyclerView y el adaptador en onCreate o similar
        @SuppressLint("CutPasteId") RecyclerView recyclerView = findViewById(R.id.recyclerViewAplicaciones);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializa la lista de labores desde LaborStorage
        listaAplicaciones = AplicacionStorage.getAplicacion();


        // Configura el adaptador con la lista de labores
        AplicacionAdapter aplicacionAdapter = new AplicacionAdapter((ArrayList<Aplicacion>) listaAplicaciones, miDb, this);
        recyclerView.setAdapter(aplicacionAdapter);

        fechaButton.setOnClickListener(v -> mostrarDatePicker());

        guardarButton.setOnClickListener(v -> {
            String nombreAplicacion = editTextNombreAplicacion.getText().toString();
            String descripcionAplicacion = editTextDescripcionAplicacion.getText().toString();
            String areaCubierta = editTextAreaCubierta.getText().toString();
            if (spinnerLotes.getSelectedItem() != null) {
                String loteSeleccionado = spinnerLotes.getSelectedItem().toString();

                if (!nombreAplicacion.isEmpty() && !fechaSeleccionada.isEmpty() && !loteSeleccionado.isEmpty()) {
                    // Verificar si la labor ya existe para evitar duplicados
                    boolean existeAplicacion = false;
                    for (Aplicacion aplicacion : listaAplicaciones) {
                        if (aplicacion.getNombreAplicacion().equals(nombreAplicacion) && aplicacion.getFechaAplicacion().equals(fechaSeleccionada)) {
                            existeAplicacion = true;
                            break;
                        }
                    }

                    if (!existeAplicacion) {
                        // Crear nueva labor
                        Aplicacion nuevaAplicacion = new Aplicacion(nombreAplicacion, fechaSeleccionada, areaCubierta,descripcionAplicacion);

                        boolean isInserted = miDb.insertarDatosAplicaciones(
                                nombreAplicacion,                // nombre_producto
                                areaCubierta,                    // cantidad_aplicada (el área cubierta puede representar la cantidad aplicada)
                                fechaSeleccionada,               // fecha_aplicacion
                                areaCubierta,                    // zona_cubierta_hectareas
                                descripcionAplicacion            // descripcion_aplicacion
                        );
                        if (isInserted) {
                            // Agregar directamente a la lista temporal
                            listaAplicaciones.add(nuevaAplicacion);

                            // Verificación mediante Log
                            Log.d("AplicacionActivity", "Aplicacion guardada: " + nuevaAplicacion.getNombreAplicacion());
                            Log.d("AplicacionActivity", "Cantidad de aplicaciones guardadas: " + listaAplicaciones.size());

                            // Notificar al adaptador que los datos han cambiado
                            aplicacionAdapter.notifyDataSetChanged();

                            // Limpiar los campos después de guardar
                            limpiarAplicacion();

                            Toast.makeText(this, "Aplicacion guardada exitosamente", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Error al guardar la aplicación", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Esta aplicación ya existe", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "No hay lotes disponibles para seleccionar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Cierra la actividad actual y vuelve a la anterior
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Mostrar un DatePicker para seleccionar la fecha
    private void mostrarDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH);
        int anio = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    fechaSeleccionada = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                    fechaButton.setText(fechaSeleccionada);
                }, anio, mes, dia);
        datePickerDialog.show();
    }

    // Limpiar los campos del formulario
    @SuppressLint("SetTextI18n")
    private void limpiarAplicacion() {
        editTextNombreAplicacion.setText("");
        editTextDescripcionAplicacion.setText("");
        spinnerLotes.setSelection(0);
        fechaButton.setText("Seleccionar Fecha");
        fechaSeleccionada = "";
        editTextAreaCubierta.setText("");
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

}
