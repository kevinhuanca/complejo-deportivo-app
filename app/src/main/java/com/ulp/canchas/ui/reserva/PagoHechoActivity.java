package com.ulp.canchas.ui.reserva;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ulp.canchas.MainActivity;
import com.ulp.canchas.R;
import com.ulp.canchas.databinding.ActivityPagoHechoBinding;

public class PagoHechoActivity extends AppCompatActivity {

    private ActivityPagoHechoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPagoHechoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btVolverInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PagoHechoActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(PagoHechoActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };

        getOnBackPressedDispatcher().addCallback(this, callback);
    }

}