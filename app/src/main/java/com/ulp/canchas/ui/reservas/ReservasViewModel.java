package com.ulp.canchas.ui.reservas;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulp.canchas.model.Reserva;
import com.ulp.canchas.request.ApiClient;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservasViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<List<Reserva>> mReservas;

    public ReservasViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<List<Reserva>> getMReservas() {
        if (mReservas == null) {
            mReservas = new MutableLiveData<>();
        }
        return mReservas;
    }

    public void datosReservas() {
        ApiClient.CanchasService api = ApiClient.getApiCanchas();
        Call<List<Reserva>> llamada = api.reservas(ApiClient.getToken(context));

        llamada.enqueue(new Callback<List<Reserva>>() {
            @Override
            public void onResponse(Call<List<Reserva>> call, Response<List<Reserva>> response) {
                if (response.isSuccessful()) {
                    mReservas.postValue(response.body());
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Toast.makeText(context, errorBody, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Reserva>> call, Throwable throwable) {
                Toast.makeText(context, "Error del servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

}