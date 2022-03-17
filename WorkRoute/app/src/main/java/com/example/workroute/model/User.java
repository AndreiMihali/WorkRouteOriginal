package com.example.workroute.model;

import java.util.ArrayList;
import java.util.Date;

public class User {
    private String uid;
    private String nombre;
    private int edad;
    private Date fecha_naci;
    private String localidad;
    private String direccion;
    private int nivel;
    private boolean activo;
    private boolean suscrito;
    private boolean online;
    private int nivelSuscripcion;
    private String fotoPerfil;
    private boolean conductor;
    private ArrayList<Viaje> viajes;
    private int vecesConectadas;

    public User() {
    }

    public User(String uid, String nombre, int edad, Date fecha_naci, String localidad, String direccion, int nivel, boolean activo, boolean suscrito, boolean online, int nivelSuscripcion, String fotoPerfil, boolean conductor, ArrayList<Viaje> viajes, int vecesConectadas) {
        this.uid = uid;
        this.nombre = nombre;
        this.edad = edad;
        this.fecha_naci = fecha_naci;
        this.localidad = localidad;
        this.direccion = direccion;
        this.nivel = nivel;
        this.activo = activo;
        this.suscrito = suscrito;
        this.online = online;
        this.nivelSuscripcion = nivelSuscripcion;
        this.fotoPerfil = fotoPerfil;
        this.conductor = conductor;
        this.viajes = viajes;
        this.vecesConectadas = vecesConectadas;
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

    public Date getFecha_naci() {
        return fecha_naci;
    }

    public void setFecha_naci(Date fecha_naci) {
        this.fecha_naci = fecha_naci;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean isSuscrito() {
        return suscrito;
    }

    public void setSuscrito(boolean suscrito) {
        this.suscrito = suscrito;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public int getNivelSuscripcion() {
        return nivelSuscripcion;
    }

    public void setNivelSuscripcion(int nivelSuscripcion) {
        this.nivelSuscripcion = nivelSuscripcion;
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

    public ArrayList<Viaje> getViajes() {
        return viajes;
    }

    public void setViajes(ArrayList<Viaje> viajes) {
        this.viajes = viajes;
    }

    public int getVecesConectadas() {
        return vecesConectadas;
    }

    public void setVecesConectadas(int vecesConectadas) {
        this.vecesConectadas = vecesConectadas;
    }
}
