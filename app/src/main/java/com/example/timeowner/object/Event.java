package com.example.timeowner.object;

import java.text.SimpleDateFormat;

public class Event {
    private String eventID;
    private String eventName;
    private SimpleDateFormat eventTime;
    private int eventIsCompleted;
    private String userID;

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public SimpleDateFormat getEventTime() {
        return eventTime;
    }

    public void setEventTime(SimpleDateFormat eventTime) {
        this.eventTime = eventTime;
    }

    public int getEventIsCompleted() {
        return eventIsCompleted;
    }

    public void setEventIsCompleted(int eventIsCompleted) {
        this.eventIsCompleted = eventIsCompleted;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Event(String eventID, String eventName, SimpleDateFormat eventTime, int eventIsCompleted, String userID) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventTime = eventTime;
        this.eventIsCompleted = eventIsCompleted;
        this.userID = userID;
    }
}

