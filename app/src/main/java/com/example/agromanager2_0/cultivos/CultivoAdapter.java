package com.example.agromanager2_0.cultivos;

import android.annotation.SuppressLint;
import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.agromanager2_0.R;
import java.util.*;

public class CultivoAdapter extends RecyclerView.Adapter<CultivoAdapter.CultivoViewHolder>{

    private final List<Cultivo> cultivos;

    public CultivoAdapter(List<Cultivo> cultivos) {
        this.cultivos = cultivos;
    }

    @NonNull
    @Override
    public CultivoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cultivo, parent, false);
        return new CultivoAdapter.CultivoViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CultivoAdapter.CultivoViewHolder holder, int position) {
        Cultivo cultivo = cultivos.get(position);
        holder.Cultivo.setText(cultivo.getCultivo());
        holder.fechaCultivo.setText("Fecha: " + cultivo.getFechaCultivo());
        holder.loteAsociado.setText("Lote: " + cultivo.getLoteCultivo());
        holder.areaCubiertaPorCultivo.setText("Area cubierta (has): " + cultivo.getAreaCubiertaPorCultivo());
        holder.descripcioCultivo.setText("Descripción: " + cultivo.getDescripcionCultivo());
    }

    @Override
    public int getItemCount() {
        return cultivos.size();
    }

    public static class CultivoViewHolder extends RecyclerView.ViewHolder {
        TextView Cultivo, fechaCultivo, loteAsociado,areaCubiertaPorCultivo, descripcioCultivo;

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
