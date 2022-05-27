package com.example.timeowner.object;

import java.text.SimpleDateFormat;

public class Target {
    private String targetID;
    private String targetName;
    private SimpleDateFormat targetStartTime;
    private SimpleDateFormat targetEndTime;
    private String targetDetails;
    private int targetIsCompleted;
    private String targetUserId;

    public Target(String targetID, String targetName, SimpleDateFormat targetStartTime, SimpleDateFormat targetEndTime, String targetDetails, int targetIsCompleted, String targetUserId) {
        this.targetID = targetID;
        this.targetName = targetName;
        this.targetStartTime = targetStartTime;
        this.targetEndTime = targetEndTime;
        this.targetDetails = targetDetails;
        this.targetIsCompleted = targetIsCompleted;
        this.targetUserId = targetUserId;
    }

    public String getTargetID() {
        return targetID;
    }

    public void setTargetID(String targetID) {
        this.targetID = targetID;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public SimpleDateFormat getTargetStartTime() {
        return targetStartTime;
    }

    public void setTargetStartTime(SimpleDateFormat targetStartTime) {
        this.targetStartTime = targetStartTime;
    }

    public SimpleDateFormat getTargetEndTime() {
        return targetEndTime;
    }

    public void setTargetEndTime(SimpleDateFormat targetEndTime) {
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
