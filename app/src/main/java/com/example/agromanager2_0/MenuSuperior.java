package com.example.agromanager2_0;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MenuSuperior extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String tipo = getIntent().getStringExtra("tipo");

        if ("Editar Perfil".equals(tipo)) {
            setContentView(R.layout.activity_editar_perfil);
        } else {
            setContentView(R.layout.activity_menu_superior);

            TextView textView = findViewById(R.id.textViewConfiguracion);
            textView.setText("Configuración: " + tipo);
        }
    }
}
