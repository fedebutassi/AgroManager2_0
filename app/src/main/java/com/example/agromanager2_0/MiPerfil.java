package com.example.agromanager2_0;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.agromanager2_0.database.MyDataBaseHelper;

import java.util.Objects;

public class MiPerfil extends AppCompatActivity {

    private TextView nombreTextView, apellidoTextView, fechaNacimientoTextView, localidadTextView, emailTextView;
    private MyDataBaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil);

        Toolbar toolbar = findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Mi perfil");

        nombreTextView = findViewById(R.id.nombreTextView);
        apellidoTextView = findViewById(R.id.apellidoTextView);
        fechaNacimientoTextView = findViewById(R.id.fechaNacimientoTextView);
        localidadTextView = findViewById(R.id.localidadTextView);
        emailTextView = findViewById(R.id.emailTextView);

        dbHelper = new MyDataBaseHelper(this);

        mostrarDatosUsuario();
    }

    private void mostrarDatosUsuario() {
        SharedPreferences sharedPreferences = getSharedPreferences("userSession", 0);
        String emailUsuario = sharedPreferences.getString("emailUsuario", "");

        if (!emailUsuario.isEmpty()) {
            Cursor cursor = dbHelper.obtenerDatosUsuario(emailUsuario);
            if (cursor != null && cursor.moveToFirst()) {
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                String apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido"));
                String fechaNacimiento = cursor.getString(cursor.getColumnIndexOrThrow("fecha_nacimiento"));
                String localidad = cursor.getString(cursor.getColumnIndexOrThrow("localidad"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));


                nombreTextView.setText(nombre);
                apellidoTextView.setText(apellido);
                fechaNacimientoTextView.setText(fechaNacimiento);
                localidadTextView.setText(localidad);
                emailTextView.setText(email);
            } else {
                Toast.makeText(this, "No se encontraron datos para este usuario.", Toast.LENGTH_SHORT).show();
            }

            if (cursor != null) {
                cursor.close();
            }
        } else {
            Toast.makeText(this, "No hay sesión activa.", Toast.LENGTH_SHORT).show();
        }
    }
}
