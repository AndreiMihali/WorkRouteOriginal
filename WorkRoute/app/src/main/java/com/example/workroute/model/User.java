package com.example.workroute.model;

public class User {
    private String uid;
    private String nombre;
    private int edad;
    private String localidad;
    private String direccion;
    private int nivel;
    private boolean activo;
    private boolean suscrito;
    private int nivelSuscripcion;
    private String fotoPerfil;
    private String alias;
    private boolean conductor;
    private int vecesConectadas;

    public User(String uid, String nombre, int edad, String localidad, String direccion, int nivel, boolean activo, boolean suscrito, int nivelSuscripcion, String fotoPerfil, String alias, boolean conductor, int vecesConectadas) {
        this.uid = uid;
        this.nombre = nombre;
        this.edad = edad;
        this.localidad = localidad;
        this.direccion = direccion;
        this.nivel = nivel;
        this.activo = activo;
        this.suscrito = suscrito;
        this.nivelSuscripcion = nivelSuscripcion;
        this.fotoPerfil = fotoPerfil;
        this.alias = alias;
        this.conductor = conductor;
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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

    public User(){}






}
