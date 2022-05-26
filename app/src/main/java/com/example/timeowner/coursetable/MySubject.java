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

    String name = "";//课程名
    int day = 0;//周几上
    String room = "";//教室
    int start = 0;//第几周开始
    int step = 0;//持续节数
    String teacher = "";//老师
    List<Integer> weekList = new ArrayList<>();//上课周表
    int colorRandom = 0;//课程颜色
    //Map表,包含学分、课程号、类型、学期
    Map<String,Object> extras=new HashMap<>();

    public MySubject(int day,String name,String room,int start,int step,String teacher,List<Integer> weekList,int colorRandom,Map<String,Object> extras){
        super();
        this.day = day;
        this.name = name;
        this.room = room;
        this.start = start;
        this.step = step;
        this.teacher = teacher;
        this.weekList = weekList;
        this.colorRandom = colorRandom;
        this.extras = extras;
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

    public Map<String, Object> getExtra() {
        int credit = 0;
        int type = 0;
        String courseId = "";
        String term = "";
        extras.put("credit",credit);
        extras.put("type",type);
        extras.put("courseId",courseId);
        extras.put("term",term);
        return extras;
    }

    @Override
    public Schedule getSchedule() {
        Schedule schedule = new Schedule();
        schedule.setDay(getDay());
        schedule.setName(getName());
        schedule.setRoom(getRoom());
        schedule.setStart(getStart());
        schedule.setStep(getStep());
        schedule.setTeacher(getTeacher());
        schedule.setWeekList(getWeekList());
        schedule.setColorRandom(getColorRandom());
        schedule.setExtras(getExtra());
        return schedule;
    }
}
