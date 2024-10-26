package com.example.agromanager2_0;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class MenuSuperior extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_superior);

        String tipo = getIntent().getStringExtra("tipo");

        TextView textView = findViewById(R.id.textViewConfiguracion);
        textView.setText("Configuracion: " + tipo);
    }
}