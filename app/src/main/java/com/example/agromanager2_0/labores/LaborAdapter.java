package com.example.agromanager2_0.labores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agromanager2_0.R;
import com.example.agromanager2_0.database.MyDataBaseHelper;

import java.util.List;

public class LaborAdapter extends RecyclerView.Adapter<LaborAdapter.LaborViewHolder> {

    private final List<Labor> labores;
    private final MyDataBaseHelper dbHelper;
    private final Context context;

    public LaborAdapter(List<Labor> labores, MyDataBaseHelper dbHelper, Context context) {
        this.labores = labores;
        this.dbHelper = dbHelper;
        this.context = context;
    }

    @NonNull
    @Override
    public LaborViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_labor, parent, false);
        return new LaborViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull LaborViewHolder holder, int position) {
        Labor labor = labores.get(position);
        holder.nombre_labor.setText(labor.getNombreLabor());
        holder.fecha_labor.setText("Fecha: " + labor.getFecha());
        holder.loteAsociado.setText("Lote: " + labor.getLote());
        holder.descripcion_labor.setText("Descripción: " + labor.getDescripcion());

        // Configuración del click largo para eliminar
        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Eliminar Labor")
                    .setMessage("¿Estás seguro de que deseas eliminar esta labor?")
                    .setPositiveButton("Sí", (dialog, which) -> eliminarLabor(position))
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return labores.size();
    }

    public void eliminarLabor(int posicion) {
        Labor labor = labores.get(posicion);

        // Suponiendo que tienes un método eliminarLabor en MyDataBaseHelper
        if (dbHelper.eliminarLabor(labor.getNombreLabor())) {  // Cambia a `labor.getNombreLabor()` si el método usa el nombre
            labores.remove(posicion);
            notifyItemRemoved(posicion);
            Toast.makeText(context, "Labor eliminada", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Error al eliminar la labor", Toast.LENGTH_SHORT).show();
        }
    }

    public static class LaborViewHolder extends RecyclerView.ViewHolder {
        TextView nombre_labor, fecha_labor, loteAsociado, descripcion_labor;

        public LaborViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre_labor = itemView.findViewById(R.id.textViewNombreLabor);
            fecha_labor = itemView.findViewById(R.id.textViewFechaLabor);
            loteAsociado = itemView.findViewById(R.id.textViewLoteAsociado);
            descripcion_labor = itemView.findViewById(R.id.textViewDescripcionLabor);
        }
    }
}
