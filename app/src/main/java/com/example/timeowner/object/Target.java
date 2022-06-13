package com.example.timeowner.object;

import java.text.SimpleDateFormat;

public class Target {
    private int targetID;
    private String targetName;
    private String targetStartTime;
    private String targetEndTime;
    private String targetDetails;
    private int targetIsCompleted;
    private String targetUserId;

    public Target(int targetID, String targetName, String targetStartTime, String targetEndTime, String targetDetails, int targetIsCompleted, String targetUserId) {
        this.targetID = targetID;
        this.targetName = targetName;
        this.targetStartTime = targetStartTime;
        this.targetEndTime = targetEndTime;
        this.targetDetails = targetDetails;
        this.targetIsCompleted = targetIsCompleted;
        this.targetUserId = targetUserId;
    }

    public Target(){}

    public int getTargetID() {
        return targetID;
    }

    public void setTargetID(int targetID) {
        this.targetID = targetID;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getTargetStartTime() {
        return targetStartTime;
    }

    public void setTargetStartTime(String targetStartTime) {
        this.targetStartTime = targetStartTime;
    }

    public String getTargetEndTime() {
        return targetEndTime;
    }

    public void setTargetEndTime(String targetEndTime) {
        this.targetEndTime = targetEndTime;
    }

    public String getTargetDetails() {
        return targetDetails;
    }

    public void setTargetDetails(String targetDetails) {
        this.targetDetails = targetDetails;
    }

    public int getTargetIsCompleted() {
        return targetIsCompleted;
    }

    public void setTargetIsCompleted(int targetIsCompleted) {
        this.targetIsCompleted = targetIsCompleted;
    }

    public String getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
    }
}
