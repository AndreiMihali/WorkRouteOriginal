package com.example.workroute.model;

import android.widget.ImageView;

public class SubscribedUser {

    private String profilePhoto;
    private String userName;
    private String userWorkDirection;
    private String status;


    public SubscribedUser(String profilePhoto, String userName, String userWorkDirection,String status) {
        this.profilePhoto = profilePhoto;
        this.userName = userName;
        this.userWorkDirection = userWorkDirection;
        this.status=status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
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
