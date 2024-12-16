package com.ulp.canchas.ui.reserva;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulp.canchas.model.Cancha;
import com.ulp.canchas.model.PagarView;
import com.ulp.canchas.request.ApiClient;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
        try {
            Cancha cancha = bundle.getSerializable("cancha", Cancha.class);
            String fecha = bundle.getString("fecha");
            String hora = bundle.getString("hora");

            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

            Date date = inputFormat.parse(fecha);
            String fechaFormateada = outputFormat.format(date);
            PagarView datos = new PagarView(cancha, fechaFormateada, hora);
            mPagar.setValue(datos);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void verificarPago() {
        PagarView datos = mPagar.getValue();

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaFormate = LocalDate.parse(datos.getFecha(), inputFormatter).format(outputFormatter);

        LocalDateTime fechaHora = LocalDateTime.parse(fechaFormate+"T"+datos.getHora()+":00");
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