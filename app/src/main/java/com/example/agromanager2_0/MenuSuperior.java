package com.example.agromanager2_0;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MenuSuperior extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Obtener el tipo de configuración pasado desde el Intent
        String tipo = getIntent().getStringExtra("tipo");

        // Condición para mostrar la vista de edición de perfil o la vista de configuración general
        if ("Editar Perfil".equals(tipo)) {
            // Cargar el layout específico para editar perfil
            setContentView(R.layout.activity_editar_perfil);
        } else {
            // Cargar el layout general del menú superior
            setContentView(R.layout.activity_menu_superior);

            // Configurar el texto de configuración si no es "Editar Perfil"
            TextView textView = findViewById(R.id.textViewConfiguracion);
            textView.setText("Configuración: " + tipo);
        }
    }
}
