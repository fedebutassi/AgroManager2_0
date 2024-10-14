package com.example.agromanager2_0.settings;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agromanager2_0.R;
import com.example.agromanager2_0.lotes.Lote;
import com.example.agromanager2_0.lotes.LoteAdapter;

import java.util.ArrayList;

public class listadoLotes extends AppCompatActivity {
    public static ArrayList<Lote> listaLotes = new ArrayList<>();
    private LoteAdapter loteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listado_lotes);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewLotes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loteAdapter = new LoteAdapter(listaLotes);
        recyclerView.setAdapter(loteAdapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}