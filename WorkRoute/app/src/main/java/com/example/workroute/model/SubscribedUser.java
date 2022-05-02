package com.example.workroute.model;

import android.widget.ImageView;

public class SubscribedUser {

    private int profilePhoto;
    private String userName;
    private String userWorkDirection;


    public SubscribedUser(int profilePhoto, String userName, String userWorkDirection, int img) {
        this.profilePhoto = profilePhoto;
        this.userName = userName;
        this.userWorkDirection = userWorkDirection;

    }


    public int getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(int profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserWorkDirection() {
        return userWorkDirection;
    }

    public void setUserWorkDirection(String userWorkDirection) {
        this.userWorkDirection = userWorkDirection;
    }



}
