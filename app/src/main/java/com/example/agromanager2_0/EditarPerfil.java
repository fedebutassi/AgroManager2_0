package com.example.agromanager2_0;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.agromanager2_0.database.MyDataBaseHelper;

public class EditarPerfil extends AppCompatActivity {
    private EditText editTextNombre, editTextApellido, editTextCorreo, editTextContrasena;
    private Button buttonGuardar;
    private MyDataBaseHelper myDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        // Inicializar los campos de texto
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

        // Instanciar la base de datos
        myDataBaseHelper = new MyDataBaseHelper(this);

        // Cargar datos del perfil desde la base de datos
        cargarDatosPerfil();

        // Llamar a actualizarPerfil() cuando se presiona el botón de guardar
        buttonGuardar.setOnClickListener(v -> {
            if (actualizarPerfil()) {
                Toast.makeText(this, "Datos actualizados con éxito", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al actualizar los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método para cargar los datos del perfil desde la base de datos
    private void cargarDatosPerfil() {
        Log.d("EditarPerfil", "Método cargarDatosPerfil() llamado");

        String emailUsuario = obtenerEmailUsuario(); // Método que obtiene el correo del usuario desde SharedPreferences

        if (!emailUsuario.isEmpty()) {
            Cursor cursor = myDataBaseHelper.obtenerDatosUsuario(emailUsuario);

            if (cursor != null && cursor.moveToFirst()) {
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                String apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));

                // Asigna los valores a los campos de texto
                editTextNombre.setText(nombre);
                editTextApellido.setText(apellido);
                editTextCorreo.setText(email);
                editTextContrasena.setText(""); // Limpiar el campo de contraseña
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

    // Método para obtener el correo del usuario desde SharedPreferences
    private String obtenerEmailUsuario() {
        SharedPreferences sharedPreferences = getSharedPreferences("Perfil", MODE_PRIVATE);
        return sharedPreferences.getString("correo", "");
    }

    // Método para actualizar los datos del perfil
    private boolean actualizarPerfil() {
        String emailUsuario = obtenerEmailUsuario();
        String nombre = editTextNombre.getText().toString().trim();
        String apellido = editTextApellido.getText().toString().trim();
        String password = editTextContrasena.getText().toString().trim();

        return myDataBaseHelper.actualizarDatosUsuario(emailUsuario, nombre, apellido, "", "", password);
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
}
