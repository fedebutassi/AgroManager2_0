    package com.example.agromanager2_0.labores;

    import android.annotation.SuppressLint;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.TextView;
    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;

    import com.example.agromanager2_0.R;

    import java.util.List;

    public class LaborAdapter extends RecyclerView.Adapter<LaborAdapter.LaborViewHolder> {

        private final List<Labor> labores;

        public LaborAdapter(List<Labor> labores) {
            this.labores = labores;
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


        }

        @Override
        public int getItemCount() {
            return labores.size();
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
