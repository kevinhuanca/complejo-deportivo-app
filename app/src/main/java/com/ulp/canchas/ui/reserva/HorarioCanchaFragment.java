package com.ulp.canchas.ui.reserva;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.ulp.canchas.R;
import com.ulp.canchas.databinding.FragmentHorarioCanchaBinding;
import com.ulp.canchas.model.Cancha;
import com.ulp.canchas.model.HoraView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HorarioCanchaFragment extends Fragment {

    private HorarioCanchaViewModel viewModel;
    private FragmentHorarioCanchaBinding binding;
    private HorarioAdapter horarioAdapter;
    private String fecha = LocalDate.now()+"";

    public static HorarioCanchaFragment newInstance() {
        return new HorarioCanchaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(HorarioCanchaViewModel.class);
        binding = FragmentHorarioCanchaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btElegirFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker<Long> datePicker = viewModel.crearCalendario();
                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        long tresHorasEnMilisegundos = 3 * 60 * 60 * 1000;
                        long nuevaSeleccion = selection + tresHorasEnMilisegundos;
                        Date date = new Date(nuevaSeleccion);

                        String fechaParaMostrar = new SimpleDateFormat("EEEE d MMMM yyyy", Locale.getDefault()).format(date);
                        binding.tvFechaSeleccionada.setText(fechaParaMostrar);

                        LocalDate fechaParaApi = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        Cancha c = getArguments().getSerializable("cancha", Cancha.class);
                        viewModel.obtenerHorasDisponibles(c.getId(), fechaParaApi);

                        fecha = fechaParaApi+"";
                    }
                });
                datePicker.show(getActivity().getSupportFragmentManager(), "CalendarioDialogo");
            }
        });

        viewModel.getMHoras().observe(getViewLifecycleOwner(), new Observer<List<HoraView>>() {
            @Override
            public void onChanged(List<HoraView> horas) {
                horarioAdapter = new HorarioAdapter(horas, inflater);
                GridLayoutManager glm = new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false);
                binding.rvListaHorarios.setAdapter(horarioAdapter);
                binding.rvListaHorarios.setLayoutManager(glm);
            }
        });

        viewModel.getMBoton().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean b) {
                binding.btIrAPagar.setEnabled(b);
            }
        });

        binding.btIrAPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getArguments().putString("fecha", fecha);
                getArguments().putString("hora", horarioAdapter.getSelected().getHora());
                Navigation.findNavController(view).navigate(R.id.pagarCanchaFragment, getArguments());
            }
        });

        viewModel.cargarHorarios(getArguments());

        return root;
    }

}