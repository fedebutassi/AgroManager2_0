package com.example.agromanager2_0.aplicaciones;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agromanager2_0.R;



import java.util.List;

public class AplicacionAdapter extends RecyclerView.Adapter<AplicacionAdapter.AplicacionViewHolder>{

    private List<Aplicacion> aplicaciones;

    public AplicacionAdapter(List<Aplicacion> aplicaciones) {
        this.aplicaciones = aplicaciones;
    }

    @NonNull
    @Override
    public AplicacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_aplicacion, parent, false);
        return new AplicacionAdapter.AplicacionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AplicacionViewHolder holder, int position) {
        Aplicacion aplicacion = aplicaciones.get(position);
        holder.nombreAplicacion.setText(aplicacion.getNombreAplicacion());
        holder.fechaAplicacion.setText("Fecha: " + aplicacion.getFechaAplicacion());
        holder.loteAsociado.setText("Lote: " + aplicacion.getLote());
        holder.areaCubierta.setText("Area cubierta (has): " + aplicacion.getAreaCubierta());
        holder.descripcionAplicacion.setText("Descripción: " + aplicacion.getDescripcionAplicacacion());
    }

    @Override
    public int getItemCount() {
        return aplicaciones.size();
    }

    public static class AplicacionViewHolder extends RecyclerView.ViewHolder {
        TextView nombreAplicacion, fechaAplicacion, loteAsociado,areaCubierta, descripcionAplicacion;

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
