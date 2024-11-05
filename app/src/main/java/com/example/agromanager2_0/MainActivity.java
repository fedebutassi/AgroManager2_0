package com.example.agromanager2_0;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class MainActivity extends AppCompatActivity {
    private MyDataBaseHelper miDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        miDb = new MyDataBaseHelper(this); // Inicializa tu base de datos

        EditText editTextEmail = findViewById(R.id.email);
        EditText editTextPassword = findViewById(R.id.password);
        Button botonIngresar = findViewById(R.id.botonIngresar);
        Button botonRegistro = findViewById(R.id.botonRegistrarse);
        Button botonForgotPassword = findViewById(R.id.olvidasteContra);


        botonIngresar.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

            if (!email.isEmpty() && !password.isEmpty()) {

                boolean isValid = miDb.validarUsuario(email, password);

                if (isValid) {
                    // Guarda el email en SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("userSession", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("emailUsuario", email);
                    editor.apply();

                    Toast.makeText(MainActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this, Home.class);
                    startActivity(intent);
                    finish();
                }

                else {
                    Toast.makeText(MainActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            }
        });

        botonRegistro.setOnClickListener(v -> irARegistro());

        botonForgotPassword.setOnClickListener(view -> irAOlvidoPassword());


    }

    public void irAPantallaInicio() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void irARegistro() {
        Intent intent = new Intent(this, Registro.class);
        startActivity(intent);
    }

    public void irAOlvidoPassword(){
        Intent intent = new Intent(this, OlvidoPassword.class);
        startActivity(intent);
    }

}