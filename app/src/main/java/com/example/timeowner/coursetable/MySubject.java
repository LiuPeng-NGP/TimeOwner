package com.example.timeowner.coursetable;

import com.zhuangfei.timetable.model.Schedule;
import com.zhuangfei.timetable.model.ScheduleEnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 课程类
 * @author jiangx
 */

public class MySubject implements ScheduleEnable {

    String name;//课程名
    int day;//周几上
    String room ;//教室
    int start;//第几节
    int step;//持续节数
    String teacher ;//老师
    List<Integer> weekList = new ArrayList<>();//上课周表
    int colorRandom;//课程颜色
    //Map表,包含学分、用户ID、类型、学期
//    Map<String,Object> extras=new HashMap<>();
//    int credit;
//    int type;
//    String userId;
//    String term;
    //对应key


    public MySubject(int day,String name,String room,int start,int step,String teacher,List<Integer> weekList,int colorRandom){
        super();
        this.day = day;
        this.name = name;
        this.room = room;
        this.start = start;
        this.step = step;
        this.teacher = teacher;
        this.weekList = weekList;
        this.colorRandom = colorRandom;
//        this.credit = credit;
//        this.type = type;
//        this.userId = userId;
//        this.term = term;
    }

    public MySubject(){
        //TODO Auto-generated constructor stub
    }

    public int getColorRandom() {//随机选取，但要保证不同
        return colorRandom;
    }

    public void setColorRandom(int colorRandom) {
        this.colorRandom = colorRandom;
    }

    //待补充
    public List<Integer> getWeekList() {
        return weekList;
    }

    public void setWeekList(List<Integer> weekList) {
        this.weekList = weekList;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

//    public int getCredit() {
//        return credit;
//    }
//
//    public void setCredit(int credit) {
//        this.credit = credit;
//    }
//
//    public int getType() {
//        return type;
//    }

//    public void setType(int type) {
//        this.type = type;
//    }
//
//    public String getTerm() {
//        return term;
//    }
//
//    public void setTerm(String term) {
//        this.term = term;
//    }
//
//    public String getUserId() {
//        return userId;
//    }
//
//    public void setUserId(String userId) {
//        this.userId = userId;
//    }

    @Override
    public Schedule getSchedule() {
        Schedule schedule = new Schedule();
        schedule.setName(getName());
        schedule.setDay(getDay());
        schedule.setRoom(getRoom());
        schedule.setStart(getStart());
        schedule.setStep(getStep());
        schedule.setTeacher(getTeacher());
        schedule.setWeekList(getWeekList());
        schedule.setColorRandom(getColorRandom());
//        schedule.putExtras("credit",getCredit());
//        schedule.putExtras("type",getType());
//        schedule.putExtras("userId",getUserId());
//        schedule.putExtras("term",getTerm());
        return schedule;
    }
}
