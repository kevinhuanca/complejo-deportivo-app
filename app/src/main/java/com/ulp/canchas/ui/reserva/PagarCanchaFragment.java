package com.ulp.canchas.ui.reserva;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.ulp.canchas.R;
import com.ulp.canchas.databinding.FragmentPagarCanchaBinding;
import com.ulp.canchas.model.PagarView;

public class PagarCanchaFragment extends Fragment {

    private PagarCanchaViewModel viewModel;
    private FragmentPagarCanchaBinding binding;

    public static PagarCanchaFragment newInstance() {
        return new PagarCanchaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(PagarCanchaViewModel.class);
        binding = FragmentPagarCanchaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel.getMPagar().observe(getViewLifecycleOwner(), new Observer<PagarView>() {
            @Override
            public void onChanged(PagarView pagarView) {
                binding.tvPagarCancha.setText(pagarView.getCancha().getNombre());
                binding.tvPagarFecha.setText(pagarView.getFecha());
                binding.tvPagarHora.setText(pagarView.getHora());
                binding.tvPagarTotal.setText("$ "+pagarView.getCancha().getPrecio()+"");
            }
        });

        viewModel.getMDialogo().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                new MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Horario no disponible")
                        .setMessage("Alguien mas ya reservo este horario, por favor elije otro.")
                        .show();
            }
        });

        viewModel.getMCerrar().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                getActivity().finish();
            }
        });

        binding.btPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.verificarPago();
            }
        });

        viewModel.obtenerDatos(getArguments());

        return root;
    }

}