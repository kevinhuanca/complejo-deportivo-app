package com.ulp.canchas.ui.reserva;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ulp.canchas.R;
import com.ulp.canchas.databinding.FragmentCanchasBinding;
import com.ulp.canchas.model.Cancha;

import java.util.List;

public class CanchasFragment extends Fragment {

    private CanchasViewModel viewModel;
    private FragmentCanchasBinding binding;

    public static CanchasFragment newInstance() {
        return new CanchasFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(CanchasViewModel.class);
        binding = FragmentCanchasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel.getMCanchas().observe(getViewLifecycleOwner(), new Observer<List<Cancha>>() {
            @Override
            public void onChanged(List<Cancha> canchas) {
                CanchaAdapter ca = new CanchaAdapter(canchas, inflater);
                GridLayoutManager glm = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
                binding.rvListaCanchas.setAdapter(ca);
                binding.rvListaCanchas.setLayoutManager(glm);
            }
        });

        viewModel.datosCanchas();

        return root;
    }

}