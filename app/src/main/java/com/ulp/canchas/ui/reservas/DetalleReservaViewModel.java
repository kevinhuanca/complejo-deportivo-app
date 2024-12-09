package com.ulp.canchas.ui.reservas;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulp.canchas.model.Reserva;

import java.util.List;

public class DetalleReservaViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<Reserva> mReserva;

    public DetalleReservaViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Reserva> getMReserva() {
        if (mReserva == null) {
            mReserva = new MutableLiveData<>();
        }
        return mReserva;
    }

    public void obtenerDatos(Bundle bundle) {
        Reserva r = bundle.getSerializable("reserva", Reserva.class);
        if (r != null) {
            mReserva.setValue(r);
        }
    }

}