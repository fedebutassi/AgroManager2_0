package com.example.agromanager2_0.aplicaciones;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agromanager2_0.R;
import com.example.agromanager2_0.labores.Labor;
import com.example.agromanager2_0.labores.LaborAdapter;
import com.example.agromanager2_0.labores.LaborStorage;
import com.example.agromanager2_0.lotes.Lote;
import com.example.agromanager2_0.lotes.LoteStorage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AplicacionActivity extends AppCompatActivity {

    private EditText editTextNombreAplicacion, editTextDescripcionAplicacion;
    private EditText editTextAreaCubierta;
    private Spinner spinnerLotes;
    private Button fechaButton, guardarButton;
    private RecyclerView recyclerViewAplicaciones;
    private AplicacionAdapter aplicacionAdapter;
    private List<Aplicacion> listaAplicaciones;
    private String fechaSeleccionada = "";

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_aplicacion2);

        // Habilitar el botón atrás en la ActionBar
        Toolbar toolbar = findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Aplicaciones");

        // Inicializar vistas
        editTextNombreAplicacion = findViewById(R.id.editTextNombreAplicacion);
        editTextDescripcionAplicacion = findViewById(R.id.editTextDescripcionAplicacion);
        spinnerLotes = findViewById(R.id.spinnerLotes);
        editTextAreaCubierta = findViewById(R.id.editTextAreaCubierta);
        fechaButton = findViewById(R.id.buttonSeleccionarFechaAplicacion);
        guardarButton = findViewById(R.id.buttonGuardarAplicacion);

        recyclerViewAplicaciones = findViewById(R.id.recyclerViewAplicaciones);

        List<String> nombresLotes = new ArrayList<>();
        for (Lote lote : LoteStorage.getLotes()) {
            nombresLotes.add(lote.getNombre());
        }

        // Log para comprobar cuántos lotes se están cargando
        Log.d("AplicacionActivity", "Lotes disponibles: " + nombresLotes.size());

        // Adaptador para el Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombresLotes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLotes.setAdapter(adapter);

        // Inicializar la lista de labores una sola vez
        listaAplicaciones = AplicacionStorage.getAplicacion();

        // Inicializa el RecyclerView y el adaptador en onCreate o similar
        RecyclerView recyclerView = findViewById(R.id.recyclerViewAplicaciones);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializa la lista de labores desde LaborStorage
        listaAplicaciones = AplicacionStorage.getAplicacion();


        // Configura el adaptador con la lista de labores
        aplicacionAdapter = new AplicacionAdapter(listaAplicaciones);
        recyclerView.setAdapter(aplicacionAdapter);

        fechaButton.setOnClickListener(v -> mostrarDatePicker());

        guardarButton.setOnClickListener(v -> {
            String nombreAplicacion = editTextNombreAplicacion.getText().toString();
            String descripcionAplicacion = editTextDescripcionAplicacion.getText().toString();
            int areaCubierta = Integer.parseInt(editTextAreaCubierta.getText().toString());
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
                        Aplicacion nuevaAplicacion = new Aplicacion(nombreAplicacion, fechaSeleccionada, loteSeleccionado, areaCubierta,descripcionAplicacion);

                        // Guardar la labor en LaborStorage
                        AplicacionStorage.addAplicacion(nuevaAplicacion);

                        // Agregar directamente a la lista temporal
                        listaAplicaciones.add(nuevaAplicacion);

                        // Verificación mediante Log
                        Log.d("AplicacionActivity", "Aplicacion guardada: " + nuevaAplicacion.getNombreAplicacion());
                        Log.d("AplicacionActivity", "Cantidad de aplicaciones guardadas: " + listaAplicaciones.size());

                        // Notificar al adaptador que los datos han cambiado
                        aplicacionAdapter.notifyDataSetChanged();

                        // Limpiar los campos después de guardar
                        limpiarLotes();

                        Toast.makeText(this, "Aplicacion guardada exitosamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Esta aplicacion ya existe", Toast.LENGTH_SHORT).show();
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
    private void limpiarLotes() {
        editTextNombreAplicacion.setText("");
        editTextDescripcionAplicacion.setText("");
        spinnerLotes.setSelection(0);
        fechaButton.setText("Seleccionar Fecha");
        fechaSeleccionada = "";
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

}
