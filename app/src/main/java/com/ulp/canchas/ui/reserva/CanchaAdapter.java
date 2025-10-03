package com.ulp.canchas.ui.reserva;

import android.graphics.Color;
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
import com.ulp.canchas.model.Cancha;
import com.ulp.canchas.request.ApiClient;

import java.util.List;

public class CanchaAdapter extends RecyclerView.Adapter<CanchaAdapter.ViewHolderCancha> {
    private List<Cancha> canchas;
    private LayoutInflater inflater;

    public CanchaAdapter(List<Cancha> canchas, LayoutInflater inflater) {
        this.canchas = canchas;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolderCancha onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_cancha, parent, false);
        return new ViewHolderCancha(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCancha holder, int position) {
        Cancha cancha = canchas.get(position);
        int j = cancha.getCapacidad()/2;
        holder.tvNombre.setText(cancha.getNombre()+" ("+cancha.getTipo().getNombre()+")");
        holder.tvDescripcion.setText("Capacidad maxima: "+j+"c"+j);
        holder.tvPrecio.setText("Precio: $ "+cancha.getPrecio());
        Glide.with(holder.itemView)
                .load(ApiClient.URLBASE + "ca/" + cancha.getImagen())
                .placeholder(R.drawable.default_imagen)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivImagen);

        holder.btReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("cancha", cancha);
                Navigation.findNavController(view).navigate(R.id.detalleCanchaFragment, bundle);
            }
        });

        if (!cancha.isEstado()) {
            holder.btReservar.setText("No disponible");
            holder.btReservar.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        return canchas.size();
    }

    public class ViewHolderCancha extends RecyclerView.ViewHolder {
        TextView tvNombre, tvDescripcion, tvPrecio;
        ImageView ivImagen;
        Button btReservar;

        public ViewHolderCancha(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvCanchaNombre);
            tvDescripcion = itemView.findViewById(R.id.tvCanchaDescripcion);
            tvPrecio = itemView.findViewById(R.id.tvCanchaPrecio);
            ivImagen = itemView.findViewById(R.id.ivCanchaImagen);
            btReservar = itemView.findViewById(R.id.btCanchaReservar);
        }
    }
}
