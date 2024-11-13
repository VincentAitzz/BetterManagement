package com.vincentaitzz.bettermanagement.model;

public class Schedule {
    private int id;
    private String horaInicio;
    private String horaTermino;
    private String persona;
    private String fecha;
    private int usuarioId;

    public Schedule(int id, String horaInicio, String horaTermino, String persona, String fecha, int usuarioId) {
        this.id = id;
        this.horaInicio = horaInicio;
        this.horaTermino = horaTermino;
        this.persona = persona;
        this.fecha = fecha;
        this.usuarioId = usuarioId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraTermino() {
        return horaTermino;
    }

    public void setHoraTermino(String horaTermino) {
        this.horaTermino = horaTermino;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }
}
