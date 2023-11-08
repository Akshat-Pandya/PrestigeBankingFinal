package com.example.prestigebankingfinal;

public class Model {
    private String cardName;
    private String cardBenefits;
    private int cardImage;

    public Model(String cardName, String cardBenefits, int cardImage) {
        this.cardName = cardName;
        this.cardBenefits = cardBenefits;
        this.cardImage = cardImage;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardBenefits() {
        return cardBenefits;
    }

    public void setCardBenefits(String cardBenefits) {
        this.cardBenefits = cardBenefits;
    }

    public int getCardImage() {
        return cardImage;
    }

    public void setCardImage(int cardImage) {
        this.cardImage = cardImage;
    }
}
