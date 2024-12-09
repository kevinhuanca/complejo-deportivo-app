package com.ulp.canchas.ui.reservas;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ulp.canchas.R;
import com.ulp.canchas.databinding.FragmentReservasBinding;
import com.ulp.canchas.model.Cancha;
import com.ulp.canchas.model.Reserva;

import java.util.List;

public class ReservasFragment extends Fragment {

    private ReservasViewModel viewModel;
    private FragmentReservasBinding binding;

    public static ReservasFragment newInstance() {
        return new ReservasFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(ReservasViewModel.class);
        binding = FragmentReservasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel.getMReservas().observe(getViewLifecycleOwner(), new Observer<List<Reserva>>() {
            @Override
            public void onChanged(List<Reserva> reservas) {
                ReservaAdapter adapter = new ReservaAdapter(reservas, inflater);
                GridLayoutManager glm = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
                binding.rvListaReservas.setAdapter(adapter);
                binding.rvListaReservas.setLayoutManager(glm);
            }
        });

        viewModel.datosReservas();

        return root;
    }

}