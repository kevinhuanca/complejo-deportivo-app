package com.ulp.canchas.ui.reserva;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulp.canchas.model.Cancha;

public class DetalleCanchaViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<Cancha> mCancha;

    public DetalleCanchaViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Cancha> getMCancha() {
        if (mCancha == null) {
            mCancha = new MutableLiveData<>();
        }
        return mCancha;
    }

    public void obtenerDatos(Bundle bundle) {
        Cancha c = bundle.getSerializable("cancha", Cancha.class);
        if (c != null) {
            mCancha.setValue(c);
        }
    }

}