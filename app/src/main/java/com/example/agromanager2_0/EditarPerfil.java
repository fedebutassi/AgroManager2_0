package com.example.agromanager2_0;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;

public class EditarPerfil extends AppCompatActivity {
    private EditText editTextNombre, editTextApellido, editTextCorreo, editTextContrasena;
    private Button buttonGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextApellido = findViewById(R.id.editTextApellido);
        editTextCorreo = findViewById(R.id.editTextCorreo);
        editTextContrasena = findViewById(R.id.editTextPassword);
        buttonGuardar = findViewById(R.id.buttonGuardarPerfil);

        // Habilitar el botón atrás en la ActionBar
        Toolbar toolbar = findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Editar Perfil");

        // Cargar datos del perfil desde SharedPreferences
        cargarDatosPerfil();

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarDatosPerfil();
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

    private void cargarDatosPerfil() {
        SharedPreferences sharedPreferences = getSharedPreferences("Perfil", MODE_PRIVATE);
        editTextNombre.setText(sharedPreferences.getString("nombre", ""));
        editTextApellido.setText(sharedPreferences.getString("apellido", ""));
        editTextCorreo.setText(sharedPreferences.getString("correo", ""));
        editTextContrasena.setText(sharedPreferences.getString("contrasena", ""));
    }

    private void guardarDatosPerfil() {
        SharedPreferences sharedPreferences = getSharedPreferences("Perfil", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("nombre", editTextNombre.getText().toString());
        editor.putString("apellido", editTextApellido.getText().toString());
        editor.putString("correo", editTextCorreo.getText().toString());
        editor.putString("contrasena", editTextContrasena.getText().toString());
        editor.apply();
    }
}
