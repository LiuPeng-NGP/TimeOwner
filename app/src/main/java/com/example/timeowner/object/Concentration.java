package com.example.timeowner.object;

public class Concentration {
    private int concentrationID;
    private String concentrationStartTime;
    private String concentrationEndTime;
    private String concentrationUserID;

    public Concentration(int concentrationID, String concentrationStartTime, String concentrationEndTime, String concentrationUserID) {
        this.concentrationID = concentrationID;
        this.concentrationStartTime = concentrationStartTime;
        this.concentrationEndTime = concentrationEndTime;
        this.concentrationUserID = concentrationUserID;
    }

    public int getConcentrationID() {
        return concentrationID;
    }

    public void setConcentrationID(int concentrationID) {
        this.concentrationID = concentrationID;
    }

    public String getConcentrationStartTime() {
        return concentrationStartTime;
    }

    public void setConcentrationStartTime(String concentrationStartTime) {
        this.concentrationStartTime = concentrationStartTime;
    }

    public String getConcentrationEndTime() {
        return concentrationEndTime;
    }

    public void setConcentrationEndTime(String concentrationEndTime) {
        this.concentrationEndTime = concentrationEndTime;
    }

    public String getConcentrationUserID() {
        return concentrationUserID;
    }

    public void setConcentrationUserID(String concentrationUserID) {
        this.concentrationUserID = concentrationUserID;
    }
}
