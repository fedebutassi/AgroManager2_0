package labores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agromanager2_0.R;

import java.util.List;

public class LaborAdapter extends RecyclerView.Adapter<LaborAdapter.LaborViewHolder> {

    private List<Labor> labores;

    public LaborAdapter(List<Labor> labores) {
        this.labores = labores;
    }

    @NonNull
    @Override
    public LaborViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_labor, parent, false);
        return new LaborViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LaborViewHolder holder, int position) {
        Labor labor = labores.get(position);
        holder.nombreLabor.setText(labor.getNombre());
        holder.fechaLabor.setText("Fecha: " + labor.getFecha());
        holder.loteAsociado.setText("Lote: " + labor.getLote());
        holder.descripcionLabor.setText("Descripción: " + labor.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return labores.size();
    }

    public static class LaborViewHolder extends RecyclerView.ViewHolder {
        TextView nombreLabor, fechaLabor, loteAsociado, descripcionLabor;

        public LaborViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreLabor = itemView.findViewById(R.id.textViewNombreLabor);
            fechaLabor = itemView.findViewById(R.id.textViewFechaLabor);
            loteAsociado = itemView.findViewById(R.id.textViewLoteAsociado);
            descripcionLabor = itemView.findViewById(R.id.textViewDescripcionLabor);
        }
    }
}