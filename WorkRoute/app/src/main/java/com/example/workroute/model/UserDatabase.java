package com.example.workroute.model;

import java.util.ArrayList;

public class UserDatabase {
    private int nivel;
    private boolean activo;
    private boolean online;
    private String fotoPerfil;
    private int followers;
    private int following;
    private int opinions;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    private String uid;

    public UserDatabase(int nivel, boolean activo, boolean online, String fotoPerfil, int followers, int following, int opinions,String uid) {
        this.nivel = nivel;
        this.activo = activo;
        this.online = online;
        this.fotoPerfil = fotoPerfil;
        this.followers = followers;
        this.following = following;
        this.opinions = opinions;
        this.uid=uid;
    }

    public UserDatabase(){}


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


    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getOpinions() {
        return opinions;
    }

    public void setOpinions(int opinions) {
        this.opinions = opinions;
    }

}
