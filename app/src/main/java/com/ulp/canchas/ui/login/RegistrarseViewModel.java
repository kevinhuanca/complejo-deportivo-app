package com.ulp.canchas.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
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

public class RegistrarseViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<Boolean> mCerrar;

    public RegistrarseViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Boolean> getMCerrar() {
        if (mCerrar == null) {
            mCerrar = new MutableLiveData<>();
        }
        return mCerrar;
    }

    public void registrarse(String nombre, String apellido, String email, String reEmail, String clave, String reClave) {
        if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || reEmail.isEmpty() || clave.isEmpty() || reClave.isEmpty()) {
            return;
        }

        ApiClient.CanchasService api = ApiClient.getApiCanchas();
        Call<String> llamada = api.registrar(nombre, apellido, email, reEmail, clave, reClave);

        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, response.body(), Toast.LENGTH_LONG).show();
                    mCerrar.setValue(true);
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
