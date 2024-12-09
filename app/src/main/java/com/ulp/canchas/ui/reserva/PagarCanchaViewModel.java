package com.ulp.canchas.ui.reserva;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.ulp.canchas.MainActivity;
import com.ulp.canchas.R;
import com.ulp.canchas.model.Cancha;
import com.ulp.canchas.model.PagarView;
import com.ulp.canchas.model.Reserva;
import com.ulp.canchas.request.ApiClient;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagarCanchaViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<PagarView> mPagar;
    private MutableLiveData<Boolean> mDialogo;
    private MutableLiveData<Boolean> mCerrar;

    public PagarCanchaViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<PagarView> getMPagar() {
        if (mPagar == null) {
            mPagar = new MutableLiveData<>();
        }
        return mPagar;
    }

    public LiveData<Boolean> getMDialogo() {
        if (mDialogo == null) {
            mDialogo = new MutableLiveData<>();
        }
        return mDialogo;
    }

    public LiveData<Boolean> getMCerrar() {
        if (mCerrar == null) {
            mCerrar = new MutableLiveData<>();
        }
        return mCerrar;
    }

    public void obtenerDatos(Bundle bundle) {
        Cancha cancha = bundle.getSerializable("cancha", Cancha.class);
        String fecha = bundle.getString("fecha");
        String hora = bundle.getString("hora");

        PagarView datos = new PagarView(cancha, fecha, hora);
        mPagar.setValue(datos);
    }

    public void verificarPago() {
        PagarView datos = mPagar.getValue();
        LocalDateTime fechaHora = LocalDateTime.parse(datos.getFecha()+"T"+datos.getHora()+":00");
        BigDecimal precio = datos.getCancha().getPrecio();
        int canchaId = datos.getCancha().getId();

        ApiClient.CanchasService api = ApiClient.getApiCanchas();
        Call<String> llamada = api.guardar(ApiClient.getToken(context), fechaHora, precio, canchaId);

        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(context, PagoHechoActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    mCerrar.setValue(true);
                } else {
                    mDialogo.setValue(true);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Toast.makeText(context, "Error del servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

}