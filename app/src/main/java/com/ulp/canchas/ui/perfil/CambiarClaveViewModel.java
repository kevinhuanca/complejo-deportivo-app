package com.ulp.canchas.ui.perfil;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulp.canchas.request.ApiClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CambiarClaveViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<Boolean> mVolver;

    public CambiarClaveViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Boolean> getMVolver(){
        if(mVolver == null){
            mVolver = new MutableLiveData<>();
        }
        return mVolver;
    }

    public void cambiarClave(String actual, String nueva, String repetida) {
        if (actual.isEmpty() || nueva.isEmpty() || repetida.isEmpty()){
            return;
        }

        ApiClient.CanchasService api = ApiClient.getApiCanchas();
        Call<String> llamada = api.clave(ApiClient.getToken(context), actual, nueva, repetida);

        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    mVolver.setValue(true);
                    Toast.makeText(context, response.body(), Toast.LENGTH_LONG).show();
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
            public void onFailure(Call<String> call, Throwable throwable) {
                Toast.makeText(context, "Error del servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

}