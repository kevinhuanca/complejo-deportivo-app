package com.ulp.canchas.ui.reserva;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulp.canchas.model.Cancha;
import com.ulp.canchas.model.PagarView;

public class PagarCanchaViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<PagarView> mPagar;

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

    public void obtenerDatos(Bundle bundle) {
        Cancha cancha = bundle.getSerializable("cancha", Cancha.class);
        String fecha = bundle.getString("fecha");
        String hora = bundle.getString("hora");

        PagarView datos = new PagarView(cancha, fecha, hora);
        mPagar.setValue(datos);
    }

    public void verificarPago() {
        // llamada api
    }

}