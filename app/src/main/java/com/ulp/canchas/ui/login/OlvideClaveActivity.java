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
import com.ulp.canchas.databinding.ActivityOlvideClaveBinding;

public class OlvideClaveActivity extends AppCompatActivity {

    private OlvideClaveViewModel viewModel;
    private ActivityOlvideClaveBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(OlvideClaveViewModel.class);
        binding = ActivityOlvideClaveBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.btEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.recuperarClave(binding.etOlvideEmail.getText().toString());
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