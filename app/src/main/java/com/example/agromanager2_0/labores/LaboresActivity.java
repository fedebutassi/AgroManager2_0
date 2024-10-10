package com.example.agromanager2_0.labores;



import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.agromanager2_0.R;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.example.agromanager2_0.lotes.Lote;
import com.example.agromanager2_0.lotes.LoteStorage;

public class LaboresActivity extends AppCompatActivity {

    private EditText nombreLaborEditText, descripcionLaborEditText;
    private Spinner spinnerLotes;
    private Button fechaButton, guardarButton;
    private RecyclerView recyclerViewLabores;
    private LaborAdapter laborAdapter;
    private List<Labor> listaLabores;
    private String fechaSeleccionada = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labores);


        // Habilitar el botón atrás en la ActionBar
        Toolbar toolbar = findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Labores");

        // Inicializar vistas
        nombreLaborEditText = findViewById(R.id.editTextNombreLabor);
        descripcionLaborEditText = findViewById(R.id.editTextDescripcionLabor);
        spinnerLotes = findViewById(R.id.spinnerLotes);
        fechaButton = findViewById(R.id.buttonSeleccionarFecha);
        guardarButton = findViewById(R.id.buttonGuardarLabor);
        recyclerViewLabores = findViewById(R.id.recyclerViewLabores);

        // Cargar lotes en el Spinner
        List<String> nombresLotes = new ArrayList<>();
        for (Lote lote : LoteStorage.getLotes()) {
            nombresLotes.add(lote.getNombre());
        }

        // Log para comprobar cuántos lotes se están cargando
        Log.d("LaboresActivity", "Lotes disponibles: " + nombresLotes.size());

        // Adaptador para el Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombresLotes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLotes.setAdapter(adapter);

        // Inicializar la lista de labores una sola vez
        listaLabores = LaborStorage.getLabores();  // Carga las labores solo una vez

        // Inicializa el RecyclerView y el adaptador en onCreate o similar
        RecyclerView recyclerView = findViewById(R.id.recyclerViewLabores);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializa la lista de labores desde LaborStorage
        listaLabores = LaborStorage.getLabores();


        // Configura el adaptador con la lista de labores
        laborAdapter = new LaborAdapter(listaLabores);
        recyclerView.setAdapter(laborAdapter);

        // Configurar el botón para seleccionar la fecha
        fechaButton.setOnClickListener(v -> mostrarDatePicker());

        guardarButton.setOnClickListener(v -> {
            String nombreLabor = nombreLaborEditText.getText().toString();
            String descripcion = descripcionLaborEditText.getText().toString();

            if (spinnerLotes.getSelectedItem() != null) {
                String loteSeleccionado = spinnerLotes.getSelectedItem().toString();

                if (!nombreLabor.isEmpty() && !fechaSeleccionada.isEmpty() && !loteSeleccionado.isEmpty()) {
                    // Verificar si la labor ya existe para evitar duplicados
                    boolean existeLabor = false;
                    for (Labor labor : listaLabores) {
                        if (labor.getNombre().equals(nombreLabor) && labor.getFecha().equals(fechaSeleccionada)) {
                            existeLabor = true;
                            break;
                        }
                    }

                    if (!existeLabor) {
                        // Crear nueva labor
                        Labor nuevaLabor = new Labor(nombreLabor, fechaSeleccionada, loteSeleccionado, descripcion);

                        // Guardar la labor en LaborStorage
                        LaborStorage.addLabor(nuevaLabor);

                        // Agregar directamente a la lista temporal
                        listaLabores.add(nuevaLabor);

                        // Verificación mediante Log
                        Log.d("LaboresActivity", "Labor guardada: " + nuevaLabor.getNombre());
                        Log.d("LaboresActivity", "Cantidad de labores guardadas: " + listaLabores.size());

                        // Notificar al adaptador que los datos han cambiado
                        laborAdapter.notifyDataSetChanged();

                        // Limpiar los campos después de guardar
                        limpiarLotes();

                        Toast.makeText(this, "Labor guardada exitosamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Esta labor ya existe", Toast.LENGTH_SHORT).show();
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
        nombreLaborEditText.setText("");
        descripcionLaborEditText.setText("");
        spinnerLotes.setSelection(0);
        fechaButton.setText("Seleccionar Fecha");
        fechaSeleccionada = "";
    }

    @Override
    protected void onResume() {
        super.onResume();


    }





}