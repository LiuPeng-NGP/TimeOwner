package com.example.timeowner.dbconnect;

import com.example.timeowner.coursetable.MySubject;
import com.example.timeowner.object.Target;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBConnectCourseTable extends DBConnect{
    //Insert statement
    public void insert(MySubject mySubject) {

        String query = "INSERT INTO table_course (course_name, " +
                "course_day, " +
                "course_room, " +
                "course_start, " +
                "course_step, " +
                "course_teacher, " +
                "course_weeklist, " +
                "course_color) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";


        //open connection
        if (this.OpenConnection()) {
            //create command and assign the query and connection from the constructor

            try {

                PreparedStatement preparedStatement = connection.prepareStatement(query);

                preparedStatement.setString(1,mySubject.getName());
                preparedStatement.setInt(2,mySubject.getDay());
                preparedStatement.setString(3,mySubject.getRoom());
                preparedStatement.setInt(4,mySubject.getStart());
                preparedStatement.setInt(5,mySubject.getStep());
                preparedStatement.setString(6,mySubject.getTeacher());
                //
                preparedStatement.setString(7,turnToString(mySubject.getWeekList()));
                preparedStatement.setInt(8,mySubject.getColorRandom());
                //extra部分
//                preparedStatement.setInt(9,mySubject.getCredit());
//                preparedStatement.setInt(10,mySubject.getType());
//                preparedStatement.setString(11,mySubject.getUserId());
//                preparedStatement.setString(12,mySubject.getTerm());

                //Execute command
                preparedStatement.executeUpdate();

                //close connection
                this.CloseConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //Update statement
    public void update(MySubject mySubject) {
        String query = "UPDATE table_course SET course_day = ?," +
                "course_room = ? " +
                "course_start = ? " +
                "course_step = ? " +
                "course_teacher = ? " +
                "course_weeklist = ? " +
                "course_color = ? " +
                "WHERE course_name = ? ";

        //Open connection
        if (this.OpenConnection()) {
            //create mysql command
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                preparedStatement.setString(11,mySubject.getName());
                preparedStatement.setInt(1,mySubject.getDay());
                preparedStatement.setString(2,mySubject.getRoom());
                preparedStatement.setInt(3,mySubject.getStart());
                preparedStatement.setInt(4,mySubject.getStep());
                preparedStatement.setString(5,mySubject.getTeacher());
                preparedStatement.setString(6,turnToString(mySubject.getWeekList()));
                preparedStatement.setInt(7,mySubject.getColorRandom());
//                preparedStatement.setInt(8,mySubject.getCredit());
//                preparedStatement.setInt(9,mySubject.getType());
//                preparedStatement.setString(10,mySubject.getTerm());


                //Execute query
                preparedStatement.execute();

                //close connection
                this.CloseConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //Delete statement
    public void delete(String id){
        String query = "DELETE FROM table_course WHERE course_name = ? ";

        //Open connection
        if (this.OpenConnection()) {
            //create mysql command
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1,id);



                //Execute query
                preparedStatement.execute();

                //close connection
                this.CloseConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<MySubject> select(){
        String query = "SELECT * FROM table_course";

        List<MySubject> list = new ArrayList<MySubject>();
        //Open connection
        if (this.OpenConnection()) {
            //create mysql command
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                //Execute query
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
//                    MySubject mySubject = new MySubject();
//                    int s = resultSet.getInt(1);
//                    mySubject.setName(resultSet.getString(2));
//                    mySubject.setDay(resultSet.getInt(3));
//                    mySubject.setRoom(resultSet.getString(4));
//                    mySubject.setStart(resultSet.getInt(5));
//                    mySubject.setStep(resultSet.getInt(6));
//                    mySubject.setTeacher(resultSet.getString(7));
//                    mySubject.setWeekList(getWeekList(resultSet.getString(8)));
//                    mySubject.setColorRandom(resultSet.getInt(9));
//                    mySubject.setCredit(resultSet.getInt(10));
//                    mySubject.setType(resultSet.getInt(11));
//                    mySubject.setTerm(resultSet.getString(12));

                    MySubject mySubject = new MySubject(resultSet.getInt(3),
                            resultSet.getString(2),
                            resultSet.getString(4),
                            resultSet.getInt(5),
                            resultSet.getInt(6),
                            resultSet.getString(7),
                            getWeekList(resultSet.getString(8)),
                            resultSet.getInt(9));

                    list.add(mySubject);
                }

                //close Data Reader
                resultSet.close();

                //close connection
                this.CloseConnection();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return list;
        }
        else {
            return null;
        }
    }

    //List转String
    public String turnToString(List<Integer> list){
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
