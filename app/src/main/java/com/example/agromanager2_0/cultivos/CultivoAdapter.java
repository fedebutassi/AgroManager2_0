package com.example.agromanager2_0.cultivos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.*;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.example.agromanager2_0.R;
import com.example.agromanager2_0.database.MyDataBaseHelper;

import java.util.List;

public class CultivoAdapter extends RecyclerView.Adapter<CultivoAdapter.CultivoViewHolder> {

    private final List<Cultivo> cultivos;
    private final MyDataBaseHelper dbHelper;
    private final Context context;

    public CultivoAdapter(List<Cultivo> cultivos, MyDataBaseHelper dbHelper, Context context) {
        this.cultivos = cultivos;
        this.dbHelper = dbHelper;
        this.context = context;
    }

    @NonNull
    @Override
    public CultivoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cultivo, parent, false);
        return new CultivoViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CultivoViewHolder holder, int position) {
        Cultivo cultivo = cultivos.get(position);
        Log.d("CultivoAdapter", "Area cubierta por cultivo: " + cultivo.getAreaCubiertaPorCultivo());

        holder.Cultivo.setText(cultivo.getCultivo());
        holder.fechaCultivo.setText("Fecha: " + cultivo.getFechaCultivo());
        holder.loteAsociado.setText("Lote: " + cultivo.getLoteCultivo());
        holder.areaCubiertaPorCultivo.setText("Área cubierta (has): " + cultivo.getAreaCubiertaPorCultivo());
        holder.descripcioCultivo.setText("Descripción: " + cultivo.getDescripcionCultivo());

        // Configuración del click largo para eliminar
        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Eliminar Cultivo")
                    .setMessage("¿Estás seguro de que deseas eliminar este cultivo?")
                    .setPositiveButton("Sí", (dialog, which) -> eliminarCultivo(position))
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return cultivos.size();
    }

    public void eliminarCultivo(int posicion) {
        Cultivo cultivo = cultivos.get(posicion);

        // Suponiendo que tienes un método eliminarCultivo en MyDataBaseHelper
        if (dbHelper.eliminarCultivo(cultivo.getCultivo())) {
            cultivos.remove(posicion);
            notifyItemRemoved(posicion);
            Toast.makeText(context, "Cultivo eliminado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Error al eliminar el cultivo: " + cultivo.getNombre(), Toast.LENGTH_SHORT).show();
        }
    }

    public static class CultivoViewHolder extends RecyclerView.ViewHolder {
        TextView Cultivo, fechaCultivo, loteAsociado, areaCubiertaPorCultivo, descripcioCultivo;

        public CultivoViewHolder(@NonNull View itemView) {
            super(itemView);
            Cultivo = itemView.findViewById(R.id.textViewCultivo);
            fechaCultivo = itemView.findViewById(R.id.textViewFechaCultivo);
            loteAsociado = itemView.findViewById(R.id.textViewLoteAsociado);
            areaCubiertaPorCultivo = itemView.findViewById(R.id.textViewAreaCubiertaPorCultivo);
            descripcioCultivo = itemView.findViewById(R.id.textViewDescripcionCultivo);
        }
    }
}
