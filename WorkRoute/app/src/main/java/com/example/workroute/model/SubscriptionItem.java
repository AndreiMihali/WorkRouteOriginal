package com.example.workroute.model;

public class SubscriptionItem {
    private String price;
    private String titleSubscription;
    private String descriptionSubscription;
    private String colorBackground;

    public SubscriptionItem(){ }

    public SubscriptionItem(String price,String titleSubscription,String descriptionSubscription,String colorBackground){
        this.price=price;
        this.titleSubscription=titleSubscription;
        this.descriptionSubscription=descriptionSubscription;
        this.colorBackground=colorBackground;
    }

    public String  getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getColorBackground(){
        return colorBackground;
    }

    public void setColorBackground(String colorBackground){
        this.colorBackground=colorBackground;
    }

    public String getTitleSubscription() {
        return titleSubscription;
    }

    public void setTitleSubscription(String titleSubscription) {
        this.titleSubscription = titleSubscription;
    }

    public String getDescriptionSubscription() {
        return descriptionSubscription;
    }

    public void setDescriptionSubscription(String descriptionSubscription) {
        this.descriptionSubscription = descriptionSubscription;
    }
}
