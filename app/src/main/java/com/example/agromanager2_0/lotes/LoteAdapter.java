package com.example.agromanager2_0.lotes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.example.agromanager2_0.R;
import com.example.agromanager2_0.database.MyDataBaseHelper;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class LoteAdapter extends RecyclerView.Adapter<LoteAdapter.LoteViewHolder> {

    private final List<Lote> lotes;
    private final MyDataBaseHelper dbHelper;
    private final Context context;

    public LoteAdapter(List<Lote> lotes, MyDataBaseHelper dbHelper, Context context) {
        this.lotes = lotes;
        this.dbHelper = dbHelper;
        this.context = context;
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
        holder.superficieLote.setText("Superficie: " + lote.getHectareas() + " has.");

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

        // Configuración del click largo para eliminar
        holder.itemView.setOnLongClickListener(v -> {
            int posicion = holder.getAdapterPosition();
            if (posicion != RecyclerView.NO_POSITION) {
                new AlertDialog.Builder(context)
                        .setTitle("Eliminar Lote")
                        .setMessage("¿Estás seguro de que deseas eliminar este lote?")
                        .setPositiveButton("Sí", (dialog, which) -> eliminarLote(posicion))
                        .setNegativeButton("No", null)
                        .show();
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return lotes.size();
    }

    public void eliminarLote(int posicion) {
        Lote lote = lotes.get(posicion);

        // Suponiendo que tienes un método eliminarLote en MyDataBaseHelper que recibe el nombre o ID del lote
        if (dbHelper.eliminarLote(lote.getNombre())) {  // Cambia a lote.getId() si tu método usa ID
            lotes.remove(posicion);
            notifyItemRemoved(posicion);
            Toast.makeText(context, "Lote eliminado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Error al eliminar el lote", Toast.LENGTH_SHORT).show();
        }
    }

    public static class LoteViewHolder extends RecyclerView.ViewHolder {
        TextView nombreLote, superficieLote;
        Button verMapaButton;

        public LoteViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreLote = itemView.findViewById(R.id.nombre_lote);
            superficieLote = itemView.findViewById(R.id.superficie_lote);
            verMapaButton = itemView.findViewById(R.id.ver_mapa_button);
        }
    }
}
