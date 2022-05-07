package com.example.workroute.model;

public class ProfileItem {
    private int imageView;
    private String desc;
    public ProfileItem(int imageView, String desc) {
        this.imageView = imageView;
        this.desc = desc;
    }

    public ProfileItem() {
    }

    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
