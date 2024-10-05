package com.example.agromanager2_0;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

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

        Button botonIngresar = findViewById(R.id.botonIngresar);

        botonIngresar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                irAPantallaInicio();
            }
        });

        Button botonRegistro = findViewById(R.id.botonRegistrarse);

        botonRegistro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
               irARegistro();
            }
        });

        Button botonForgotPassword = findViewById(R.id.olvidasteContra);

        botonForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irAOlvidoPassword();
            }
        });

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