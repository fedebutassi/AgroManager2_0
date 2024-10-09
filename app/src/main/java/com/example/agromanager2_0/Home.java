package com.example.agromanager2_0;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ImageButton imageButton5 = findViewById(R.id.signomas);
        imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog(view);
            }
        });

        Button buttonMenuSuperior = findViewById(R.id.menuSuperior);
        buttonMenuSuperior.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view2){
                showBottomSheetDialog2(view2);
            }
        });


    }


    private void showBottomSheetDialog(View view) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.bottom_sheet_menu, null);

        bottomSheetDialog.setContentView(bottomSheetView);

        bottomSheetView.findViewById(R.id.nuevoLote).setOnClickListener(v -> {
            openFormActivity("Nuevo Lote");
            bottomSheetDialog.dismiss();
        });

        bottomSheetView.findViewById(R.id.nuevaLabor).setOnClickListener(v -> {
            openFormActivity("Nueva Labor");
            bottomSheetDialog.dismiss();
        });

        bottomSheetView.findViewById(R.id.nuevaAplicacion).setOnClickListener(v -> {
            openFormActivity("Nueva Aplicación");
            bottomSheetDialog.dismiss();
        });

        bottomSheetView.findViewById(R.id.nuevoCultivo).setOnClickListener(v -> {
            openFormActivity("Nuevo Cultivo");
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();
    }

    private void openFormActivity(String tipo) {
        Intent intent = new Intent(this, FormActivity.class);
        intent.putExtra("tipo", tipo);
        startActivity(intent);
    }

    private void showBottomSheetDialog2(View view2){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.menusuperior, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        bottomSheetView.findViewById(R.id.configuracion).setOnClickListener(v ->{
            openMenuSuperior("Configuracion");
            bottomSheetDialog.dismiss();
        });

        bottomSheetView.findViewById(R.id.editarPerfil).setOnClickListener(v ->{
            openMenuSuperior("Editar Perfil");
            bottomSheetDialog.dismiss();
        });

        bottomSheetView.findViewById(R.id.cerrarSesion).setOnClickListener(v ->{
            AlertDialog.Builder builder = new AlertDialog.Builder(null);
            builder.setMessage(R.string.dialogoCancelar);

            builder.setPositiveButton(R.string.salirConfirmar, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            builder.setNegativeButton(R.string.dialogoCancelar, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
        });


        bottomSheetDialog.show();
    }

    private void openMenuSuperior(String tipo) {
        Intent intent = new Intent(this, MenuSuperior.class);
        intent.putExtra("tipo", tipo);
        startActivity(intent);
    }
}