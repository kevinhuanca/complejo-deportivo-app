package com.ulp.canchas.ui.reserva;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.ulp.canchas.model.Cancha;
import com.ulp.canchas.model.HoraView;
import com.ulp.canchas.request.ApiClient;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HorarioCanchaViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<List<HoraView>> mHoras;

    public HorarioCanchaViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<List<HoraView>> getMHoras() {
        if (mHoras == null) {
            mHoras = new MutableLiveData<>();
        }
        return mHoras;
    }

    public MaterialDatePicker<Long> crearCalendario() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.add(Calendar.HOUR, -3);
        calendar.add(Calendar.DATE, -1);
        long hoyLocal = calendar.getTimeInMillis();
        calendar.add(Calendar.DATE, 14);
        long dosSemanas = calendar.getTimeInMillis();

        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
        constraintsBuilder.setValidator(new CalendarConstraints.DateValidator() {
            @Override
            public boolean isValid(long date) {
                return date >= hoyLocal && date <= dosSemanas;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeLong(hoyLocal);
                dest.writeLong(dosSemanas);
            }
        });

        return MaterialDatePicker.Builder.datePicker()
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .setCalendarConstraints(constraintsBuilder.build())
                        .build();
    }

    public void obtenerHorasDisponibles(int idCancha, LocalDate fecha) {
        ApiClient.CanchasService api = ApiClient.getApiCanchas();
        Call<List<HoraView>> llamada = api.disponibles(ApiClient.getToken(context), idCancha, fecha);

        llamada.enqueue(new Callback<List<HoraView>>() {
            @Override
            public void onResponse(Call<List<HoraView>> call, Response<List<HoraView>> response) {
                if (response.isSuccessful()) {
                    mHoras.postValue(response.body());
                } else {
                    try {
                        mHoras.postValue(new ArrayList<>());
                        String errorBody = response.errorBody().string();
                        Toast.makeText(context, errorBody, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<HoraView>> call, Throwable throwable) {
                Toast.makeText(context, "Error del servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void cargarHorarios(Bundle bundle) {
        Cancha c = bundle.getSerializable("cancha", Cancha.class);
        if (c == null)
            return;
        obtenerHorasDisponibles(c.getId(), LocalDate.now());
    }

}