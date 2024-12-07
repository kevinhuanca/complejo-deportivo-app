package com.ulp.canchas.ui.reserva;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ulp.canchas.R;
import com.ulp.canchas.model.HoraView;

import java.util.ArrayList;
import java.util.List;

public class HorarioAdapter extends RecyclerView.Adapter<HorarioAdapter.ViewHolderHora>{
    private List<HoraView> horas;
    private LayoutInflater inflater;
    private int checkedPosition = 0;

    public HorarioAdapter(List<HoraView> horas, LayoutInflater inflater) {
        this.horas = horas;
        this.inflater = inflater;
    }

    public void SetHoras(List<HoraView> horas) {
        this.horas = new ArrayList<>();
        this.horas = horas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolderHora onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_hora, parent, false);
        return new ViewHolderHora(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderHora holder, int position) {
        holder.bind(horas.get(position));
    }

    @Override
    public int getItemCount() {
        return horas.size();
    }

    public class ViewHolderHora extends RecyclerView.ViewHolder {
        TextView tvHora;
        CardView cardView;

        public ViewHolderHora(@NonNull View itemView) {
            super(itemView);
            tvHora = itemView.findViewById(R.id.tvCardHora);
            cardView = itemView.findViewById(R.id.cardViewHora);
        }

        void bind(final HoraView hora) {
            if (checkedPosition == -1) {
                tvHora.setTextColor(Color.parseColor("#1d192b"));
                cardView.setCardBackgroundColor(Color.parseColor("#e8def8"));
            } else {
                if (checkedPosition == getAdapterPosition()) {
                    tvHora.setTextColor(Color.WHITE);
                    cardView.setCardBackgroundColor(Color.parseColor("#6200ee"));
                } else {
                    tvHora.setTextColor(Color.parseColor("#1d192b"));
                    cardView.setCardBackgroundColor(Color.parseColor("#e8def8"));
                }
            }

            tvHora.setText(hora.getHora());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tvHora.setTextColor(Color.WHITE);
                    cardView.setCardBackgroundColor(Color.parseColor("#6200ee"));
                    if (checkedPosition != getAdapterPosition()) {
                        notifyItemChanged(checkedPosition);
                        checkedPosition = getAdapterPosition();
                    }
                }
            });
        }
    }

    public HoraView getSelected() {
        if (checkedPosition < horas.size()) {
            return horas.get(checkedPosition);
        }
        return null;
    }

}
