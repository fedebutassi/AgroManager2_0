package com.example.agromanager2_0;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;


import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ImageButton imageButton5 = findViewById(R.id.imageButton5);
        imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog(view);
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
}