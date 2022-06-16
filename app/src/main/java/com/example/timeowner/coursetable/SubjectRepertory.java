package com.example.timeowner.coursetable;

import static java.sql.DriverManager.println;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timeowner.dbconnect.DBConnectCourseTable;
import com.example.timeowner.object.Target;
import com.example.timeowner.target.TargetListAdapter;
import com.example.timeowner.target.TargetMainActivity;

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

    public static final int UPDATE_TEXT = 1;
    public static List<MySubject> list1 =new ArrayList<MySubject>();

    public static List<MySubject> loadDefaultSubjects() {

        List<MySubject> courses = new ArrayList<>();

//        String userId = "191001";
        //数据库获取数据

        @SuppressLint("HandlerLeak") Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == UPDATE_TEXT){
                    list1 = (List<MySubject>) msg.obj;

//                    int a = list1.size();
//                    println(a+"");
//                    println("\n");

                    for (int i = 0; i < list1.size();i++){
                        String temp1 = list1.get(i).getName();
                        int temp2 = list1.get(i).getDay();
                        String temp3 = list1.get(i).getRoom();
                        int temp4 = list1.get(i).getStart();
                        int temp5 = list1.get(i).getStep();
                        String temp6 = list1.get(i).getTeacher();
                        String temp7 = turnToString(list1.get(i).getWeekList());
                        int temp8 = list1.get(i).getColorRandom();
//                        int temp9 = list1.get(i).getCredit();
//                        int temp10 = list1.get(i).getType();
//                        String temp11 = list1.get(i).getUserId();
//                        String temp12 = list1.get(i).getTerm();

                        MySubject list = new MySubject(temp2,temp1,temp3,temp4,temp5,temp6,getWeekList(temp7),temp8);

                        courses.add(list);
                    }

                }
            }
        };
        //线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                DBConnectCourseTable dbc = new DBConnectCourseTable();
                list1 = dbc.select();

                Message msg = new Message();
                msg.what = UPDATE_TEXT;
                msg.obj = list1;
                handler.sendMessage(msg);

                try{
                    Thread.sleep(100);
                }catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            }
        }).start();

        return courses;
    }

    //List转String
    public static String turnToString(List<Integer> list){
        StringBuilder s = new StringBuilder();
        int len = list.size();
        for (int i = 0; i < len - 1; i++){
            s.append(list.get(i).toString());
            s.append(",");
        }
        s.append(list.get(len-1).toString());
        return s.toString();
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