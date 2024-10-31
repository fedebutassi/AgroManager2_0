package com.example.agromanager2_0.aplicaciones;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agromanager2_0.R;

import java.util.ArrayList;

public class AplicacionAdapter extends RecyclerView.Adapter<AplicacionAdapter.AplicacionViewHolder> {

    private final ArrayList<Aplicacion> listaAplicaciones;

    public AplicacionAdapter(ArrayList<Aplicacion> listaAplicaciones) {
        this.listaAplicaciones = listaAplicaciones;
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
        Aplicacion aplicacion = listaAplicaciones.get(position); // Usa listaAplicaciones
        holder.nombreAplicacion.setText(aplicacion.getNombreAplicacion());
        holder.fechaAplicacion.setText("Fecha: " + aplicacion.getFechaAplicacion());
        holder.loteAsociado.setText("Lote: " + aplicacion.getLote()); // Asegúrate de que getLote() esté implementado
        holder.areaCubierta.setText("Área cubierta: " + aplicacion.getAreaCubierta() + " has.");
        holder.descripcionAplicacion.setText("Descripción: " + aplicacion.getDescripcionAplicacacion()); // Asegúrate de que getDescripcionAplicacion() esté implementado
    }

    @Override
    public int getItemCount() {
        return listaAplicaciones.size(); // Usa listaAplicaciones
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