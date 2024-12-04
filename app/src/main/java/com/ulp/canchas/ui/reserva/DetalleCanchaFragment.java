package com.ulp.canchas.ui.reserva;

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
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.ulp.canchas.R;
import com.ulp.canchas.databinding.FragmentDetalleCanchaBinding;
import com.ulp.canchas.model.Cancha;

public class DetalleCanchaFragment extends Fragment {

    private DetalleCanchaViewModel viewModel;
    private FragmentDetalleCanchaBinding binding;

    public static DetalleCanchaFragment newInstance() {
        return new DetalleCanchaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(DetalleCanchaViewModel.class);
        binding = FragmentDetalleCanchaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel.getMCancha().observe(getViewLifecycleOwner(), new Observer<Cancha>() {
            @Override
            public void onChanged(Cancha cancha) {
                binding.tvDetalleNombre.setText("Nombre: "+cancha.getNombre());
                binding.tvDetalleTipo.setText("Tipo: "+cancha.getTipo().getNombre());
                binding.tvDetalleDescripcion.setText("Descripcion: "+cancha.getDescripcion());
                int j = cancha.getCapacidad()/2;
                binding.tvDetalleCapacidad.setText("Capacidad: "+j+"c"+j+" ("+cancha.getCapacidad()+")");
                binding.tvDetallePrecio.setText("Precio: $ "+cancha.getPrecio());
                Glide.with(getContext())
                        .load("http://192.168.0.14:5218/ca/"+cancha.getImagen())
                        .placeholder(R.drawable.default_imagen)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.ivDetalleImagen);
            }
        });

        binding.btDetalleReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.horarioCanchaFragment, getArguments());
            }
        });

        viewModel.obtenerDatos(getArguments());

        return root;
    }

}