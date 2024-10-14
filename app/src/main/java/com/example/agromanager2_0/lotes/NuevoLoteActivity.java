package com.example.agromanager2_0.lotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.agromanager2_0.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;


public class NuevoLoteActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static ArrayList<Lote> listaLotes = new ArrayList<>();
    private LoteAdapter loteAdapter;
    private GoogleMap mMap;
    private LatLng selectedLocation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_lote);

        // Inicializar el fragmento del mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);


        Toolbar toolbar = findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Lotes");

        Button guardarButton = findViewById(R.id.buttonGuardarLote);
        EditText nombreEditText = findViewById(R.id.editTextNombreLote);
        EditText superficieEditText = findViewById(R.id.editTextSuperficie);

        // Configurar RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerViewLotes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loteAdapter = new LoteAdapter(listaLotes);
        recyclerView.setAdapter(loteAdapter);

        // Listener para el botón Guardar
        guardarButton.setOnClickListener(v -> {
            String nombreLote = nombreEditText.getText().toString();
            String superficie = superficieEditText.getText().toString();

            if (!nombreLote.isEmpty() && !superficie.isEmpty() && selectedLocation != null) {
                Lote nuevoLote = new Lote(nombreLote, superficie, selectedLocation);
                LoteStorage.addLote(nuevoLote);
                listaLotes.add(nuevoLote);
                loteAdapter.notifyDataSetChanged();

                nombreEditText.setText("");
                superficieEditText.setText("");
                mMap.clear();

                // Enviar un resultado de éxito a Home para habilitar las opciones en el menú
                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);


                // Notificar al adaptador que los datos han cambiado
                loteAdapter.notifyDataSetChanged();

                Toast.makeText(this, "Lote guardado exitosamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Por favor, complete todos los campos y seleccione una ubicación", Toast.LENGTH_SHORT).show();
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
}
