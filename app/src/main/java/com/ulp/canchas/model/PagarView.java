package com.ulp.canchas.model;

public class PagarView {

    private Cancha cancha;
    private String fecha;
    private String hora;

    public PagarView() {
    }

    public PagarView(Cancha cancha, String fecha, String hora) {
        this.cancha = cancha;
        this.fecha = fecha;
        this.hora = hora;
    }

    public Cancha getCancha() {
        return cancha;
    }

    public void setCancha(Cancha cancha) {
        this.cancha = cancha;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
