package com.example.timeowner.object;

public class Concentration {
    private int concentrationID;
    private int concentrationTime;
    private String concentrationUserID;


    public Concentration() {

    }

    public int getConcentrationID() {
        return concentrationID;
    }

    public void setConcentrationID(int concentrationID) {
        this.concentrationID = concentrationID;
    }

    public int getConcentrationTime() {
        return concentrationTime;
    }

    public void setConcentrationTime(int concentrationTime) {
        this.concentrationTime = concentrationTime;
    }

    public String getConcentrationUserID() {
        return concentrationUserID;
    }

    public void setConcentrationUserID(String concentrationUserID) {
        this.concentrationUserID = concentrationUserID;
    }

    public Concentration(int concentrationID, int concentrationTime, String concentrationUserID) {
        this.concentrationID = concentrationID;
        this.concentrationTime = concentrationTime;
        this.concentrationUserID = concentrationUserID;
    }
}
