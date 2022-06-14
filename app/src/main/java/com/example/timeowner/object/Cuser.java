package com.example.timeowner.object;

public class Cuser {
    private int cuserID;
    private String cuserUserID;
    private int cuserCourseID;


    public Cuser(int cuserID, String cuserUserID, int cuserCourseID) {
        this.cuserID = cuserID;
        this.cuserUserID = cuserUserID;
        this.cuserCourseID = cuserCourseID;
    }

    public int getCuserID() {
        return cuserID;
    }

    public void setCuserID(int cuserID) {
        this.cuserID = cuserID;
    }

    public String getCuserUserID() {
        return cuserUserID;
    }

    public void setCuserUserID(String cuserUserID) {
        this.cuserUserID = cuserUserID;
    }

    public int getCuserCourseID() {
        return cuserCourseID;
    }

    public void setCuserCourseID(int cuserCourseID) {
        this.cuserCourseID = cuserCourseID;
    }
}
