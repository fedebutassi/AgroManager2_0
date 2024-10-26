package com.example.agromanager2_0.lotes;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.agromanager2_0.R;
import com.example.agromanager2_0.database.MyDataBaseHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.*;
public class NuevoLoteActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static ArrayList<Lote> listaLotes = new ArrayList<>();
    private LoteAdapter loteAdapter;
    private GoogleMap mMap;
    private LatLng selectedLocation;

    private MyDataBaseHelper miDb;
    private Button buttonGuardarLote;
    private EditText editTextNombreLote, editTextSuperficie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_lote);

        Toolbar toolbar = findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Lotes");

        miDb = new MyDataBaseHelper(this);

        editTextNombreLote = findViewById(R.id.editTextNombreLote);
        editTextSuperficie = findViewById(R.id.editTextSuperficie);
        buttonGuardarLote = findViewById(R.id.buttonGuardarLote);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewLotes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loteAdapter = new LoteAdapter(listaLotes);
        recyclerView.setAdapter(loteAdapter);

        cargarLotes();

        // Inicializar el fragmento del mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);

        buttonGuardarLote.setOnClickListener(v -> {
            String nombre_campo = editTextNombreLote.getText().toString();
            String hectareas = editTextSuperficie.getText().toString();

            if (selectedLocation != null && selectedLocation.latitude != 0 && selectedLocation.longitude != 0) {
                if (!nombre_campo.isEmpty() && !hectareas.isEmpty()) {
                    try {
                        double superficie = Double.parseDouble(hectareas);

                        double latitud = selectedLocation.latitude;
                        double longitud = selectedLocation.longitude;

                        // Operaciones de base de datos en un hilo separado
                        Executor executor = Executors.newSingleThreadExecutor();
                        executor.execute(() -> {
                            boolean isInserted = miDb.insertarDatosLotes(nombre_campo, superficie, latitud, longitud);
                            runOnUiThread(() -> {
                                if (isInserted) {
                                    Toast.makeText(this, "Lote guardado exitosamente", Toast.LENGTH_SHORT).show();

                                    // Limpiar campos
                                    editTextNombreLote.setText("");
                                    editTextSuperficie.setText("");
                                    mMap.clear();

                                    // Devuelve el resultado
                                    setResult(RESULT_OK);
                                    finish(); // Finaliza la actividad
                                } else {
                                    Toast.makeText(this, "Error al guardar el lote", Toast.LENGTH_SHORT).show();
                                }
                            });
                        });
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, "Superficie inválida", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Seleccione una ubicación en el mapa", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        LatLng initialLocation = new LatLng(-32.177287983945334, -64.11699464249472); // Ubicación inicial por defecto
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, 15));

        mMap.setOnMapLongClickListener(latLng -> {
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(latLng).title("Ubicación seleccionada"));
            selectedLocation = latLng;
        });

        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

    private void moveToLocation(LatLng latLng, String title) {
        if (mMap != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            mMap.addMarker(new MarkerOptions().position(latLng).title(title));
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void cargarLotes() {
        MyDataBaseHelper db = new MyDataBaseHelper(this);
        listaLotes.clear();
        Cursor cursor = miDb.obtenerLotes();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String nombreLote = cursor.getString(1); // Asumiendo que el nombre está en la columna 1
                double hectareas = cursor.getDouble(2); // Asumiendo que la superficie está en la columna 2
                double latitud = cursor.getDouble(3);
                double longitud = cursor.getDouble(4);
                LatLng ubicacion = new LatLng(latitud, longitud);

                Lote lote = new Lote(nombreLote, hectareas, ubicacion);
                listaLotes.add(lote);
            }
            loteAdapter.notifyDataSetChanged(); // Actualiza el RecyclerView
        }
        if (cursor != null) {
            cursor.close(); // Cierra el cursor para evitar fugas de memoria
        }
    }

}
