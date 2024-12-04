package com.ulp.canchas.ui.reserva;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulp.canchas.model.Cancha;
import com.ulp.canchas.request.ApiClient;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CanchasViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<List<Cancha>> mCanchas;

    public CanchasViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<List<Cancha>> getMCanchas() {
        if (mCanchas == null) {
            mCanchas = new MutableLiveData<>();
        }
        return mCanchas;
    }

    public void datosCanchas() {
        ApiClient.CanchasService api = ApiClient.getApiCanchas();
        Call<List<Cancha>> llamada = api.todos(ApiClient.getToken(context));

        llamada.enqueue(new Callback<List<Cancha>>() {
            @Override
            public void onResponse(Call<List<Cancha>> call, Response<List<Cancha>> response) {
                if (response.isSuccessful()) {
                    mCanchas.postValue(response.body());
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
            public void onFailure(Call<List<Cancha>> call, Throwable throwable) {
                Toast.makeText(context, "Error del servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

}