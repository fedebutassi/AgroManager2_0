package com.example.agromanager2_0.labores;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.agromanager2_0.R;
import java.util.*;

import com.example.agromanager2_0.database.MyDataBaseHelper;
import com.example.agromanager2_0.lotes.Lote;

public class LaboresActivity extends AppCompatActivity {

    private EditText nombreLaborEditText, descripcionLaborEditText;
    private Spinner spinnerLotes;
    private Button fechaButton, guardarButton;
    private RecyclerView recyclerViewLabores;
    private LaborAdapter laborAdapter;
    private List<Labor> listaLabores;
    private String fechaSeleccionada = "";
    private MyDataBaseHelper miDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labores);

        miDb = new MyDataBaseHelper(this);

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
                        if (labor.getNombreLabor().equals(nombreLabor) && labor.getFecha().equals(fechaSeleccionada)) {
                            existeLabor = true;
                            break;
                        }
                    }

                    if (!existeLabor) {
                        // Crear nueva labor
                        Labor nuevaLabor = new Labor(nombreLabor, fechaSeleccionada, loteSeleccionado, descripcion);;
                        boolean insertadoExitosamente = miDb.insertarDatosLabores(nombreLabor, descripcion, fechaSeleccionada);

                        if (insertadoExitosamente) {
                            // Agregar directamente a la lista temporal
                            listaLabores.add(nuevaLabor);

                            // Verificación mediante Log
                            Log.d("LaboresActivity", "Labor guardada: " + nuevaLabor.getNombreLabor());
                            Log.d("LaboresActivity", "Cantidad de labores guardadas: " + listaLabores.size());

                            // Notificar al adaptador que los datos han cambiado
                            laborAdapter.notifyDataSetChanged();

                            // Limpiar los campos después de guardar
                            limpiarLabores();

                            Toast.makeText(this, "Labor guardada exitosamente en la base de datos", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Error al guardar la labor en la base de datos", Toast.LENGTH_SHORT).show();
                        }
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
    private void limpiarLabores() {
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