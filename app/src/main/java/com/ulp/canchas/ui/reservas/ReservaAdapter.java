package com.ulp.canchas.ui.reservas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ulp.canchas.R;
import com.ulp.canchas.model.Reserva;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReservaAdapter extends RecyclerView.Adapter<ReservaAdapter.ViewHolderReserva> {
    private List<Reserva> reservas;
    private LayoutInflater inflater;

    public ReservaAdapter(List<Reserva> reservas, LayoutInflater inflater) {
        this.reservas = reservas;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolderReserva onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_reserva, parent, false);
        return new ViewHolderReserva(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderReserva holder, int position) {
        Reserva reserva = reservas.get(position);

        holder.tvFecha.setText("Fecha: "+reserva.getFecha());
        holder.tvHora.setText("Hora: "+reserva.getHora()+" hs");

        Glide.with(holder.itemView)
                .load("http://192.168.0.14:5218/ca/"+reserva.getCancha().getImagen())
                .placeholder(R.drawable.default_imagen)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivImagen);

        holder.btDetalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("reserva", reserva);
                Navigation.findNavController(view).navigate(R.id.detalleReservaFragment, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reservas.size();
    }

    public class ViewHolderReserva extends RecyclerView.ViewHolder {
        TextView tvFecha, tvHora;
        ImageView ivImagen;
        Button btDetalles;

        public ViewHolderReserva(@NonNull View itemView) {
            super(itemView);
            tvFecha = itemView.findViewById(R.id.tvReservaFecha);
            tvHora = itemView.findViewById(R.id.tvReservaHora);
            ivImagen = itemView.findViewById(R.id.ivReservaImagen);
            btDetalles = itemView.findViewById(R.id.btVerDetalles);
        }
    }
}
