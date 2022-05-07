package com.example.workroute.model;

import java.util.Locale;

public class CardItem {
    private int status;
    private String numberCard;
    private String cardName;
    private int cardType;

    public CardItem(int status, String numberCard, String cardName, int cardType) {
        this.status = status;
        this.numberCard = numberCard.replaceFirst("[0-9]{12}", "XXXX XXXX XXXX ");
        this.cardName = cardName.toUpperCase(Locale.ROOT);
        this.cardType = cardType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNumberCard() {
        return numberCard;
    }

    public void setNumberCard(String numberCard) {
        this.numberCard = numberCard.replaceFirst("[0-9]{12}", "XXXX XXXX XXXX ");
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName.toUpperCase(Locale.ROOT);
    }

    public int getCardType() {
        return cardType;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }


}
