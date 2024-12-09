package com.ulp.canchas.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reserva implements Serializable {

    private int id;
    private String fechaHora;
    private BigDecimal precio;
    private int usuarioId;
    private Usuario usuario;
    private int canchaId;
    private Cancha cancha;

    public Reserva() {
    }

    public Reserva(int id, String fechaHora, BigDecimal precio, int usuarioId, Usuario usuario, int canchaId, Cancha cancha) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.precio = precio;
        this.usuarioId = usuarioId;
        this.usuario = usuario;
        this.canchaId = canchaId;
        this.cancha = cancha;
    }

    public String getFecha() {
        if (fechaHora == null || fechaHora.isEmpty())
            return "";

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime localDateTime = LocalDateTime.parse(fechaHora, formatter);
        return localDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public String getHora() {
        if (fechaHora == null || fechaHora.isEmpty())
            return "";

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime localDateTime = LocalDateTime.parse(fechaHora, formatter);
        return localDateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getCanchaId() {
        return canchaId;
    }

    public void setCanchaId(int canchaId) {
        this.canchaId = canchaId;
    }

    public Cancha getCancha() {
        return cancha;
    }

    public void setCancha(Cancha cancha) {
        this.cancha = cancha;
    }
}
