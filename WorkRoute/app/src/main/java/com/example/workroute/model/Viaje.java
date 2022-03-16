package com.example.workroute.model;

public class Viaje {
    private String uidConductor;
    private String uidUusario;
    private String destino;
    private String comienzo;
    private String fecha;
    private String matricula;

    public Viaje(String uidConductor, String uidUusario, String destino, String comienzo,String fecha,String matricula) {
        this.uidConductor = uidConductor;
        this.uidUusario = uidUusario;
        this.destino = destino;
        this.comienzo = comienzo;
        this.fecha=fecha;
        this.matricula=matricula;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Viaje(){}

    public String getUidConductor() {
        return uidConductor;
    }

    public void setUidConductor(String uidConductor) {
        this.uidConductor = uidConductor;
    }

    public String getUidUusario() {
        return uidUusario;
    }

    public void setUidUusario(String uidUusario) {
        this.uidUusario = uidUusario;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getComienzo() {
        return comienzo;
    }

    public void setComienzo(String comienzo) {
        this.comienzo = comienzo;
    }
}
