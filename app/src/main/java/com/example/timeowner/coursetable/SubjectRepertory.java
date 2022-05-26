package com.example.timeowner.coursetable;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据仓库
 * @author jiangx
 * 待修改
 */

public class SubjectRepertory {

    public static List<MySubject> loadDefaultSubjects() {
        List<MySubject> courses = new ArrayList<>();
        //数据库获取数据
        //待补充
        int course_num = 0;
        while(course_num != 0){
            //获取
            String name = "";
            int day = 0;
            String room = "";
            int start = 0;
            int step = 0;
            String teacher = "";
            String weekListString = "";//周表的存储形式
            List<Integer> weekList = new ArrayList<>();
            weekList = getWeekList(weekListString);
            int colorRandom = 0;
            int credit = 0;
            int type = 0;
            String courseId = "";
            String term = "";
            Map<String,Object> extras=new HashMap<>();
            extras.put("credit",credit);
            extras.put("type",type);
            extras.put("courseId",courseId);
            extras.put("term",term);
            courses.add(new MySubject(day,name,room,start,step,teacher,weekList,colorRandom,extras));
        };
        return courses;
    }

    //对字符串 "1,2,3,4"类解析,返回包含课程的周
    public static List<Integer> getWeekList(String weeksString){
        List<Integer> weekList=new ArrayList<>();
        if(weeksString==null||weeksString.length()==0) return weekList;

        if(weeksString.contains(",")){
            String[] arr=weeksString.split(",");
            for(int i=0;i<arr.length;i++){
                weekList.addAll(getWeekList2(arr[i]));
            }
        }else{
            weekList.addAll(getWeekList2(weeksString));
        }
        return weekList;
    }
    public static List<Integer> getWeekList2(String weeksString){
        List<Integer> weekList=new ArrayList<>();
        int first=-1,end=-1,index=-1;
        if((index=weeksString.indexOf("-"))!=-1){
            first=Integer.parseInt(weeksString.substring(0,index));
            end=Integer.parseInt(weeksString.substring(index+1));
        }else{
            first=Integer.parseInt(weeksString);
            end=first;
        }
        for(int i=first;i<=end;i++)
            weekList.add(i);
        return weekList;
    }
}