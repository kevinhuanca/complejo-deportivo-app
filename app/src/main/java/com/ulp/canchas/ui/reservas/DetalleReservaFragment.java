package com.ulp.canchas.ui.reservas;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ulp.canchas.R;
import com.ulp.canchas.databinding.FragmentDetalleReservaBinding;
import com.ulp.canchas.model.Reserva;
import com.ulp.canchas.request.ApiClient;

public class DetalleReservaFragment extends Fragment {

    private DetalleReservaViewModel viewModel;
    private FragmentDetalleReservaBinding binding;

    public static DetalleReservaFragment newInstance() {
        return new DetalleReservaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(DetalleReservaViewModel.class);
        binding = FragmentDetalleReservaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel.getMReserva().observe(getViewLifecycleOwner(), new Observer<Reserva>() {
            @Override
            public void onChanged(Reserva reserva) {
                binding.tvRNombre.setText("Cancha: "+reserva.getCancha().getNombre());
                binding.tvRFecha.setText("Fecha: "+reserva.getFecha());
                binding.tvRHora.setText("Hora: "+reserva.getHora());
                binding.tvRDescripcion.setText("Descripcion: "+reserva.getCancha().getDescripcion());
                int j = reserva.getCancha().getCapacidad()/2;
                binding.tvRCapacidad.setText("Capacidad: "+j+"c"+j+" ("+reserva.getCancha().getCapacidad()+")");
                binding.tvRPrecio.setText("Precio: $ "+reserva.getPrecio());
                Glide.with(getContext())
                        .load(ApiClient.URLBASE + "ca/" + reserva.getCancha().getImagen())
                        .placeholder(R.drawable.default_imagen)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.ivRImagen);
            }
        });

        binding.btRVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigateUp();
            }
        });

        viewModel.obtenerDatos(getArguments());

        return root;
    }

}