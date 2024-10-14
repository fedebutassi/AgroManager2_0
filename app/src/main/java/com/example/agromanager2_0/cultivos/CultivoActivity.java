package com.example.agromanager2_0.cultivos;

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

import com.example.agromanager2_0.lotes.Lote;
import com.example.agromanager2_0.lotes.LoteStorage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CultivoActivity extends AppCompatActivity {

    private EditText editTextCultivo, editTextDescripcionCultivo;
    private EditText editTextAreaCubiertaPorCultivo;
    private Spinner spinnerLotes;
    private Button fechaButton, guardarButton;
    private RecyclerView recyclerViewCultivos;
    private CultivoAdapter cultivoAdapter;
    private List<Cultivo> listaCultivos;
    private String fechaSeleccionada = "";

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cultivo);

        // Habilitar el botón atrás en la ActionBar
        Toolbar toolbar = findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cultivos");

        // Inicializar vistas
        editTextCultivo = findViewById(R.id.editTextCultivo);
        editTextDescripcionCultivo = findViewById(R.id.editTextDescripcionCultivo);
        spinnerLotes = findViewById(R.id.spinnerLotes);
        editTextAreaCubiertaPorCultivo = findViewById(R.id.editTextAreaCubiertaPorCultivo);
        fechaButton = findViewById(R.id.buttonSeleccionarFechaCultivo);
        guardarButton = findViewById(R.id.buttonGuardarCultivo);

        recyclerViewCultivos = findViewById(R.id.recyclerViewCultivos);

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
        listaCultivos = CultivoStorage.getCultivos();

        // Inicializa el RecyclerView y el adaptador en onCreate o similar
        RecyclerView recyclerView = findViewById(R.id.recyclerViewCultivos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializa la lista de labores desde LaborStorage
        listaCultivos = CultivoStorage.getCultivos();


        // Configura el adaptador con la lista de labores
        cultivoAdapter = new CultivoAdapter(listaCultivos);
        recyclerView.setAdapter(cultivoAdapter);

        fechaButton.setOnClickListener(v -> mostrarDatePicker());

        guardarButton.setOnClickListener(v -> {
            String Cultivo = editTextCultivo.getText().toString();
            String descripcionCultivo = editTextDescripcionCultivo.getText().toString();
            int areaCubiertaPorCultivo = Integer.parseInt(editTextAreaCubiertaPorCultivo.getText().toString());
            if (spinnerLotes.getSelectedItem() != null) {
                String loteSeleccionado = spinnerLotes.getSelectedItem().toString();

                if (!Cultivo.isEmpty() && !fechaSeleccionada.isEmpty() && !loteSeleccionado.isEmpty()) {
                    // Verificar si la labor ya existe para evitar duplicados
                    boolean existeCultivo = false;
                    for (Cultivo cultivo : listaCultivos) {
                        if (cultivo.getCultivo().equals(Cultivo) && cultivo.getFechaCultivo().equals(fechaSeleccionada)) {
                            existeCultivo = true;
                            break;
                        }
                    }

                    if (!existeCultivo) {
                        // Crear nueva labor
                        Cultivo nuevoCultivo = new Cultivo(Cultivo, fechaSeleccionada, loteSeleccionado, areaCubiertaPorCultivo,descripcionCultivo);

                        // Guardar la labor en LaborStorage
                        CultivoStorage.addCultivo(nuevoCultivo);

                        // Agregar directamente a la lista temporal
                        listaCultivos.add(nuevoCultivo);

                        // Verificación mediante Log
                        Log.d("CultivoActivity", "Cultivo guardado: " + nuevoCultivo.getCultivo());
                        Log.d("CultivoActivity", "Cantidad de cultivos guardados: " + listaCultivos.size());

                        // Notificar al adaptador que los datos han cambiado
                        cultivoAdapter.notifyDataSetChanged();

                        // Limpiar los campos después de guardar
                        limpiarLotes();

                        Toast.makeText(this, "Cultivo guardado exitosamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Este cultivo ya existe", Toast.LENGTH_SHORT).show();
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
        editTextCultivo.setText("");
        editTextDescripcionCultivo.setText("");
        spinnerLotes.setSelection(0);
        fechaButton.setText("Seleccionar Fecha");
        fechaSeleccionada = "";
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

}