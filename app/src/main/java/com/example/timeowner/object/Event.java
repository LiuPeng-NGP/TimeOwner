package com.example.timeowner.object;

import java.text.SimpleDateFormat;

public class Event {
    private int eventID;
    private String eventName;
    private String eventUserID;

    public Event() {
    }

    public Event(int eventID, String eventName, String eventUserID) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventUserID = eventUserID;
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

    public String getEventUserID() {
        return eventUserID;
    }

    public void setEventUserID(String eventUserID) {
        this.eventUserID = eventUserID;
    }
}

