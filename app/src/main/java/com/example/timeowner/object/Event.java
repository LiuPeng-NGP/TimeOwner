package com.example.timeowner.object;

import java.text.SimpleDateFormat;

public class Event {
    private int eventID;
    private String eventName;
    private String eventTime;
    private String eventDetails;
    private int eventIsCompleted;
    private String eventUserID;

    public Event(int eventID, String eventName, String eventTime, String eventDetails, int eventIsCompleted, String eventUserID) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventTime = eventTime;
        this.eventDetails = eventDetails;
        this.eventIsCompleted = eventIsCompleted;
        this.eventUserID = eventUserID;
    }

    public Event() {

    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(String eventDetails) {
        this.eventDetails = eventDetails;
    }

    public int getEventIsCompleted() {
        return eventIsCompleted;
    }

    public void setEventIsCompleted(int eventIsCompleted) {
        this.eventIsCompleted = eventIsCompleted;
    }

    public String getEventUserID() {
        return eventUserID;
    }

    public void setEventUserID(String eventUserID) {
        this.eventUserID = eventUserID;
    }
}

