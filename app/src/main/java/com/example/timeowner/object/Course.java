package com.example.timeowner.object;

public class Course {
    private int courseID;
    private String courseName;
    private int courseDay;
    private String courseRoom;
    private int courseStart;
    private int courseStep;
    private String courseTeacher;
    private String courseWeekList;
    private int courseColor;
    private int courseCredit;
    private int courseType;
    private String courseTerm;

    public Course(int courseID, String courseName, int courseDay, String courseRoom, int courseStart, int courseStep, String courseTeacher, String courseWeekList, int courseColor, int courseCredit, int courseType, String courseTerm) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.courseDay = courseDay;
        this.courseRoom = courseRoom;
        this.courseStart = courseStart;
        this.courseStep = courseStep;
        this.courseTeacher = courseTeacher;
        this.courseWeekList = courseWeekList;
        this.courseColor = courseColor;
        this.courseCredit = courseCredit;
        this.courseType = courseType;
        this.courseTerm = courseTerm;
    }

    public Course() {

    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCourseDay() {
        return courseDay;
    }

    public void setCourseDay(int courseDay) {
        this.courseDay = courseDay;
    }

    public String getCourseRoom() {
        return courseRoom;
    }

    public void setCourseRoom(String courseRoom) {
        this.courseRoom = courseRoom;
    }

    public int getCourseStart() {
        return courseStart;
    }

    public void setCourseStart(int courseStart) {
        this.courseStart = courseStart;
    }

    public int getCourseStep() {
        return courseStep;
    }

    public void setCourseStep(int courseStep) {
        this.courseStep = courseStep;
    }

    public String getCourseTeacher() {
        return courseTeacher;
    }

    public void setCourseTeacher(String courseTeacher) {
        this.courseTeacher = courseTeacher;
    }

    public String getCourseWeekList() {
        return courseWeekList;
    }

    public void setCourseWeekList(String courseWeekList) {
        this.courseWeekList = courseWeekList;
    }

    public int getCourseColor() {
        return courseColor;
    }

    public void setCourseColor(int courseColor) {
        this.courseColor = courseColor;
    }

    public int getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(int courseCredit) {
        this.courseCredit = courseCredit;
    }

    public int getCourseType() {
        return courseType;
    }

    public void setCourseType(int courseType) {
        this.courseType = courseType;
    }

    public String getCourseTerm() {
        return courseTerm;
    }

    public void setCourseTerm(String courseTerm) {
        this.courseTerm = courseTerm;
    }
}
