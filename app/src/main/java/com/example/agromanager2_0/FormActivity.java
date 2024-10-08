package com.example.agromanager2_0;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class FormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        String tipo = getIntent().getStringExtra("tipo");

        TextView textView = findViewById(R.id.textViewTipo);
        textView.setText("Formulario para: " + tipo);

        // Aquí puedes agregar más campos de formulario según el tipo.
    }
}