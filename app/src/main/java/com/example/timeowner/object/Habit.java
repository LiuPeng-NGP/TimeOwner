package com.example.timeowner.object;

public class Habit {
    private int habitID;
    private String habitName;
    private int habitCount;
    private int habitTodayIsCompleted;
    private String habitUserID;

    public Habit(int habitID, String habitName, int habitCount, int habitTodayIsCompleted, String habitUserID) {
        this.habitID = habitID;
        this.habitName = habitName;
        this.habitCount = habitCount;
        this.habitTodayIsCompleted = habitTodayIsCompleted;
        this.habitUserID = habitUserID;
    }

    public Habit() {

    }

    public int getHabitID() {
        return habitID;
    }

    public void setHabitID(int habitID) {
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

    public String getHabitUserID() {
        return habitUserID;
    }

    public void setHabitUserID(String habitUserID) {
        this.habitUserID = habitUserID;
    }
}
