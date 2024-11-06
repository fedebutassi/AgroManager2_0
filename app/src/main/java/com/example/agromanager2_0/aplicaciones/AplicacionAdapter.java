package com.example.agromanager2_0.aplicaciones;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.agromanager2_0.R;
import com.example.agromanager2_0.database.MyDataBaseHelper;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class AplicacionAdapter extends RecyclerView.Adapter<AplicacionAdapter.AplicacionViewHolder> {

    private final ArrayList<Aplicacion> listaAplicaciones;
    private final MyDataBaseHelper dbHelper;
    private final Context context;

    public AplicacionAdapter(ArrayList<Aplicacion> listaAplicaciones, MyDataBaseHelper dbHelper, Context context) {
        this.listaAplicaciones = listaAplicaciones;
        this.dbHelper = dbHelper;
        this.context = context;
    }

    @NonNull
    @Override
    public AplicacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_aplicacion, parent, false);
        return new AplicacionViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AplicacionViewHolder holder, int position) {
        Aplicacion aplicacion = listaAplicaciones.get(position);

        // Añadir log para verificar el valor de areaCubierta

        holder.nombreAplicacion.setText(aplicacion.getNombreAplicacion());
        holder.fechaAplicacion.setText("Fecha: " + aplicacion.getFechaAplicacion());
        holder.loteAsociado.setText("Lote: " + aplicacion.getLote());
        holder.areaCubierta.setText("Área cubierta: " + aplicacion.getAreaCubierta() + " has.");
        holder.descripcionAplicacion.setText("Descripción: " + aplicacion.getDescripcionAplicacacion());

        // Configuración del click largo para eliminar
        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Eliminar Aplicación")
                    .setMessage("¿Estás seguro de que deseas eliminar esta aplicación?")
                    .setPositiveButton("Sí", (dialog, which) -> eliminarAplicacion(position))
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return listaAplicaciones.size();
    }

    // Método para eliminar una aplicación
    public void eliminarAplicacion(int posicion) {
        Aplicacion aplicacion = listaAplicaciones.get(posicion);

        // Suponiendo que tienes un método eliminarAplicacion en MyDataBaseHelper
        if (dbHelper.eliminarAplicacion(aplicacion.getNombreAplicacion())) {
            listaAplicaciones.remove(posicion);
            notifyItemRemoved(posicion);
            Toast.makeText(context, "Aplicación eliminada", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Error al eliminar la aplicación: " + aplicacion.getNombreAplicacion(), Toast.LENGTH_SHORT).show();
        }
    }

    public static class AplicacionViewHolder extends RecyclerView.ViewHolder {
        TextView nombreAplicacion, fechaAplicacion, loteAsociado, areaCubierta, descripcionAplicacion;

        public AplicacionViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreAplicacion = itemView.findViewById(R.id.textViewNombreAplicacion);
            fechaAplicacion = itemView.findViewById(R.id.textViewFechaAplicacion);
            loteAsociado = itemView.findViewById(R.id.textViewLoteAsociado);
            areaCubierta = itemView.findViewById(R.id.textViewAreaCubierta);
            descripcionAplicacion = itemView.findViewById(R.id.textViewDescripcionAplicacion);
        }
    }
}
