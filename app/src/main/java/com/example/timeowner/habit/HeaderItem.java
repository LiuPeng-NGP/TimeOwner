package com.example.timeowner.habit;

public class HeaderItem extends ListItem {

    private int habitTodayIsCompletedType;

    // here getters and setters
    // for title and so on, built

    public int getHabitTodayIsCompletedType() {
        return habitTodayIsCompletedType;
    }

    public void setHabitTodayIsCompletedType(int habitTodayIsCompletedType) {
        this.habitTodayIsCompletedType = habitTodayIsCompletedType;
    }
    // using date

    public HeaderItem(int habitTodayIsCompletedType) {
        this.habitTodayIsCompletedType = habitTodayIsCompletedType;
    }

    @Override
    public int getType() {
        return TYPE_HEADER;
    }

}