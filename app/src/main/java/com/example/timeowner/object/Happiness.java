package com.example.timeowner.object;

public class Happiness {
    private int happinessID;
    private String happinessText;

    public Happiness(int happinessID, String happinessText) {
        this.happinessID = happinessID;
        this.happinessText = happinessText;
    }

    public int getHappinessID() {
        return happinessID;
    }

    public void setHappinessID(int happinessID) {
        this.happinessID = happinessID;
    }

    public String getHappinessText() {
        return happinessText;
    }

    public void setHappinessText(String happinessText) {
        this.happinessText = happinessText;
    }
}
