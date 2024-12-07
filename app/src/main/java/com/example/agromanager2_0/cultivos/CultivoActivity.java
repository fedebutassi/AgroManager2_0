package com.example.agromanager2_0.cultivos;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
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

public class CultivoActivity extends AppCompatActivity {

    private EditText editTextCultivo, editTextDescripcionCultivo; // Sin cambios
    private EditText editTextAreaCubiertaPorCultivo; // Sin cambios
    private Spinner spinnerLotes; // Sin cambios
    private Button fechaButton;
    private CultivoAdapter cultivoAdapter; // Sin cambios
    private static List<Cultivo> listaCultivos = new ArrayList<>();    private String fechaSeleccionada = ""; // Sin cambios

    MyDataBaseHelper miDb;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cultivo);

        miDb = new MyDataBaseHelper(this);

        Toolbar toolbar = findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cultivos");

        editTextCultivo = findViewById(R.id.editTextCultivo);
        editTextDescripcionCultivo = findViewById(R.id.editTextDescripcionCultivo);
        spinnerLotes = findViewById(R.id.spinnerLotes);
        editTextAreaCubiertaPorCultivo = findViewById(R.id.editTextAreaCubiertaPorCultivo);
        fechaButton = findViewById(R.id.buttonSeleccionarFechaCultivo);
        // Sin cambios
        Button guardarButton = findViewById(R.id.buttonGuardarCultivo);

        // Sin cambios
        RecyclerView recyclerViewCultivos = findViewById(R.id.recyclerViewCultivos);
        recyclerViewCultivos.setLayoutManager(new LinearLayoutManager(this)); // Inicialización movida al principio

        List<Lote> lotesDesdeDb = miDb.obtenerLotesLista();
        List<String> nombresLotes = new ArrayList<>();
        for (Lote lote : lotesDesdeDb) {
            if (lote != null && lote.getNombre() != null) {  // Verifica que lote y nombre no sean nulos
                nombresLotes.add(lote.getNombre());
            }
        }

        if (nombresLotes.isEmpty()) {
            Toast.makeText(this, "No hay lotes disponibles.", Toast.LENGTH_SHORT).show();
        } else {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombresLotes);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerLotes.setAdapter(adapter);
        }

        listaCultivos = CultivoStorage.getCultivos();
        cultivoAdapter = new CultivoAdapter(listaCultivos,miDb,this);
        recyclerViewCultivos.setAdapter(cultivoAdapter);

        fechaButton.setOnClickListener(view -> mostrarDatePicker()); // Método separado para el DatePicker

        guardarButton.setOnClickListener(view -> {
            String nombreCultivo = editTextCultivo.getText().toString(); // Variable renombrada para claridad
            String descripcionCultivo = editTextDescripcionCultivo.getText().toString();
            String areaCubiertaPorCultivo = editTextAreaCubiertaPorCultivo.getText().toString();
            if (spinnerLotes.getSelectedItem() != null) {
                String loteSeleccionado = spinnerLotes.getSelectedItem().toString();

                if (!nombreCultivo.isEmpty() && !fechaSeleccionada.isEmpty() && !loteSeleccionado.isEmpty()) {
                    // Verificar si la labor ya existe para evitar duplicados
                    boolean existeCultivo = false; // Nueva lógica para evitar duplicados
                    for (Cultivo cultivo : listaCultivos) {
                        if (cultivo.getCultivo().equals(nombreCultivo) && cultivo.getFechaCultivo().equals(fechaSeleccionada)) {
                            existeCultivo = true;
                            break;
                        }
                    }

                    if (!existeCultivo) {
                        Cultivo nuevoCultivo = new Cultivo(nombreCultivo, fechaSeleccionada,  areaCubiertaPorCultivo, descripcionCultivo);

                        boolean isInserted = miDb.insertarDatosCultivos(nombreCultivo, fechaSeleccionada,  areaCubiertaPorCultivo, descripcionCultivo);


                        if (isInserted) {

                            listaCultivos.add(nuevoCultivo);

                            cultivoAdapter.notifyDataSetChanged();

                            limpiarLotes();

                            Toast.makeText(this, "Cultivo guardado exitosamente en la base de datos", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Error al guardar el cultivo en la base de datos", Toast.LENGTH_SHORT).show();
                        }
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
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Mostrar un DatePicker para seleccionar la fecha
    private void mostrarDatePicker() { // Nuevo método para mostrar el DatePicker
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
    private void limpiarLotes() { // Nuevo método para limpiar los campos
        editTextCultivo.setText("");
        editTextDescripcionCultivo.setText("");
        spinnerLotes.setSelection(0);
        editTextAreaCubiertaPorCultivo.setText("");
        fechaButton.setText("Seleccionar Fecha");
        fechaSeleccionada = "";
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}
