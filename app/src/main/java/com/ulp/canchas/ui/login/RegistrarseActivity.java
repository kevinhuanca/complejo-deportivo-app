package com.ulp.canchas.ui.login;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ulp.canchas.R;
import com.ulp.canchas.databinding.ActivityRegistrarseBinding;

public class RegistrarseActivity extends AppCompatActivity {

    private RegistrarseViewModel viewModel;
    private ActivityRegistrarseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(RegistrarseViewModel.class);
        binding = ActivityRegistrarseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.registrarse(
                        binding.etRegistroNombre.getText().toString(),
                        binding.etRegistroApellido.getText().toString(),
                        binding.etRegistroEmail.getText().toString(),
                        binding.etRegistroReEmail.getText().toString(),
                        binding.etRegistroClave.getText().toString(),
                        binding.etRegistroReClave.getText().toString()
                );
            }
        });

        viewModel.getMCerrar().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean cerrar) {
                if (cerrar != null && cerrar) {
                    finish();
                }
            }
        });

    }
}