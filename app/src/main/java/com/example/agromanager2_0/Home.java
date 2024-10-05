package com.example.agromanager2_0;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import labores.LaboresActivity;
import lotes.NuevoLoteActivity;


public class Home extends AppCompatActivity {

    private boolean loteCreado = false; // Controla si hay un lote creado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        ImageButton botonAgregar = findViewById(R.id.imageButton5);
        botonAgregar.setOnClickListener(v -> mostrarMenu(v));
    }
    //actualiza la variable loteCreado cuando se guarde un lote.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            loteCreado = true; // Indica que se creo un lote
        }
    }


    // Método para mostrar el menú emergente
    private void mostrarMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_nuevo, popup.getMenu());

        // Habilitar/deshabilitar opciones según el estado de loteCreado
        popup.getMenu().findItem(R.id.menu_nueva_labor).setEnabled(loteCreado);
        popup.getMenu().findItem(R.id.menu_nuevo_cultivo).setEnabled(loteCreado);
        popup.getMenu().findItem(R.id.menu_nueva_aplicacion).setEnabled(loteCreado);

        popup.setOnMenuItemClickListener(this::onMenuItemClick);
        popup.show();
    }


    private boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_nuevo_lote) {
            abrirNuevaPantalla("Lotes");
            return true;
        } else if (id == R.id.menu_nueva_labor) {
            abrirNuevaPantalla("Labores");
            return true;
        } else if (id == R.id.menu_nuevo_cultivo) {
            abrirNuevaPantalla("Cultivos");
            return true;
        } else if (id == R.id.menu_nueva_aplicacion) {
            abrirNuevaPantalla("Aplicaciones");
            return true;
        } else {
            return false;
        }
    }


    // Método para abrir la pantalla correspondiente
    private void abrirNuevaPantalla(String pantalla) {
        Intent intent;
        switch (pantalla) {
            case "Lotes":
                intent = new Intent(this, NuevoLoteActivity.class);
                startActivityForResult(intent, 1);
                break;
            case "Labores":
                intent = new Intent(this, LaboresActivity.class);
                startActivity(intent);
                break;
        }
    }


}
