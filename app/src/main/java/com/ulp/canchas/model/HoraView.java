package com.ulp.canchas.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class HoraView implements Serializable {

    private String hora;
    private boolean seleccionado;

    private static final SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm:ss");
    private static final SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm");

    public HoraView() {
    }

    public HoraView(String hora, boolean seleccionado) {
        this.hora = hora;
        this.seleccionado = seleccionado;
    }

    public String getHora() {
        return formatTime(hora);
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    private String formatTime(String hora) {
        if (hora != null) {
            try {
                Date date = inputFormat.parse(hora);
                return outputFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
