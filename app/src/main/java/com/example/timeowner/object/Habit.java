package com.example.timeowner.object;

public class Habit {
    private String habitID;
    private String habitName;
    private int habitCount;
    private int habitTodayIsCompleted;
    private String userID;

    public String getHabitID() {
        return habitID;
    }

    public void setHabitID(String habitID) {
        this.habitID = habitID;
    }

    public String getHabitName() {
        return habitName;
    }

    public void setHabitName(String habitName) {
        this.habitName = habitName;
    }

    public int getHabitCount() {
        return habitCount;
    }

    public void setHabitCount(int habitCount) {
        this.habitCount = habitCount;
    }

    public int getHabitTodayIsCompleted() {
        return habitTodayIsCompleted;
    }

    public void setHabitTodayIsCompleted(int habitTodayIsCompleted) {
        this.habitTodayIsCompleted = habitTodayIsCompleted;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Habit(String habitID, String habitName, int habitCount, int habitTodayIsCompleted, String userID) {
        this.habitID = habitID;
        this.habitName = habitName;
        this.habitCount = habitCount;
        this.habitTodayIsCompleted = habitTodayIsCompleted;
        this.userID = userID;
    }
}
