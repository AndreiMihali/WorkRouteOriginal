package com.example.workroute.model;

public class User {
    private String uid;
    private String nombre;
    private int edad;
    private String fecha_naci;
    private String localidad;
    private boolean activo;
    private boolean online;
    private String fotoPerfil;
    private boolean conductor;
    private int vecesConectadas;
    private String workAddress;

    public User() {
    }

    public User(String uid, String nombre, int edad, String fecha_naci, String localidad, boolean activo,
                boolean online, String fotoPerfil, boolean conductor, int vecesConectadas, String workAddress) {
        this.uid = uid;
        this.nombre = nombre;
        this.edad = edad;
        this.fecha_naci = fecha_naci;
        this.localidad = localidad;
        this.activo = activo;
        this.online = online;
        this.fotoPerfil = fotoPerfil;
        this.conductor = conductor;
        this.vecesConectadas = vecesConectadas;
        this.workAddress = workAddress;
    }


    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getFecha_naci() {
        return fecha_naci;
    }

    public void setFecha_naci(String fecha_naci) {
        this.fecha_naci = fecha_naci;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public boolean isConductor() {
        return conductor;
    }

    public void setConductor(boolean conductor) {
        this.conductor = conductor;
    }

    public int getVecesConectadas() {
        return vecesConectadas;
    }

    public void setVecesConectadas(int vecesConectadas) {
        this.vecesConectadas = vecesConectadas;
    }
}
