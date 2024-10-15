package com.example.agromanager2_0;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.agromanager2_0.database.MyDataBaseHelper;

public class Registro extends AppCompatActivity {
    MyDataBaseHelper miDb;
    EditText nombre, apellido, bornDate, localidad, ingresoEmail, passwordField;
    Button confirmarRegistro, cancelarRegistro;


    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);

        miDb = new MyDataBaseHelper(this);

        nombre = findViewById(R.id.nombre);
        apellido = findViewById(R.id.apellido);
        bornDate = findViewById(R.id.bornDate);
        localidad = findViewById(R.id.localidad);
        ingresoEmail = findViewById(R.id.ingresoEmail);
        passwordField = findViewById(R.id.passwordField);
        cancelarRegistro = findViewById(R.id.cancelarRegistro);
        confirmarRegistro = findViewById(R.id.confirmarRegistro);

        confirmarRegistro.setOnClickListener(v -> {
            String nombreStr = nombre.getText().toString();
            String apellidoStr = apellido.getText().toString();
            String fechaNacimientoStr = bornDate.getText().toString();
            String localidadStr = localidad.getText().toString();
            String emailStr = ingresoEmail.getText().toString();
            String passwordStr = passwordField.getText().toString();

            boolean isInserted = miDb.insertarDatosUsuario(nombreStr,apellidoStr,fechaNacimientoStr,localidadStr,emailStr,passwordStr);
            limpiarFormRegistro();
            if (isInserted)
                Toast.makeText(Registro.this, "Datos guardados exitosamente", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(Registro.this, "Error al guardar los datos", Toast.LENGTH_SHORT).show();

            if (nombreStr.isEmpty() || apellidoStr.isEmpty() || emailStr.isEmpty() || passwordStr.isEmpty()) {
                Toast.makeText(Registro.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

        });

        cancelarRegistro.setOnClickListener(v -> {
            limpiarFormRegistro();
            finish();
        });




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Limpiar los campos del formulario
    private void limpiarFormRegistro() {
        nombre.setText("");
        apellido.setText("");
        bornDate.setText("");
        localidad.setText("");
        ingresoEmail.setText("");
        passwordField.setText("");
    }
}