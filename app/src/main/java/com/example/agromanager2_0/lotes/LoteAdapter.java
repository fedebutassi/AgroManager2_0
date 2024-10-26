package com.example.agromanager2_0.lotes;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agromanager2_0.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.*;

public class LoteAdapter extends RecyclerView.Adapter<LoteAdapter.LoteViewHolder> {

    private final List<Lote> lotes;

    public LoteAdapter(List<Lote> lotes) {
        this.lotes = lotes;
    }

    @NonNull
    @Override
    public LoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lote, parent, false);
        return new LoteViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull LoteViewHolder holder, int position) {
        Lote lote = lotes.get(position);

        holder.nombreLote.setText(lote.getNombre());
        holder.superficieLote.setText("Superficie: " + lote.getHectareas() +" has.");

        // Mostrar ubicación en el botón Ver en Google Maps
        if (lote.getUbicacion() != null) {
            LatLng ubicacion = lote.getUbicacion();

            holder.verMapaButton.setOnClickListener(v -> {
                // Abrir Google Maps con la ubicación seleccionada
                Uri gmmIntentUri = Uri.parse("geo:" + ubicacion.latitude + "," + ubicacion.longitude + "?q=" + ubicacion.latitude + "," + ubicacion.longitude);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");


                holder.itemView.getContext().startActivity(mapIntent);
            });
        } else {
            holder.verMapaButton.setEnabled(false);
            holder.verMapaButton.setText("Ubicación no seleccionada");
        }
    }



    @Override
    public int getItemCount() {
        return lotes.size();
    }

    public static class LoteViewHolder extends RecyclerView.ViewHolder {
        TextView nombreLote, superficieLote;
        Button verMapaButton;

        public LoteViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreLote = itemView.findViewById(R.id.nombre_lote);
            superficieLote = itemView.findViewById(R.id.superficie_lote);
            verMapaButton = itemView.findViewById(R.id.ver_mapa_button);
            itemView.setOnClickListener(view -> {

            });
        }
    }

}

