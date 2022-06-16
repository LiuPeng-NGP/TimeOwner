package com.example.timeowner.dbconnect;

import com.example.timeowner.object.Course;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DBConnectCourse extends DBConnect{
    public void insert(Course course) {

        String query = "INSERT INTO table_course (course_id, " +
                "course_name, " +
                "course_day, " +
                "course_room, " +
                "course_start, " +
                "course_step, " +
                "course_teacher, " +
                "course_week_list, " +
                "course_color, " +
                "course_credit, " +
                "course_type, " +
                "course_term) " +

                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


        //open connection
        if (this.OpenConnection()) {
            //create command and assign the query and connection from the constructor

            try {

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                preparedStatement.setInt(1,course.getCourseID());
                preparedStatement.setString(2,course.getCourseName());
                preparedStatement.setInt(3,course.getCourseDay());
                preparedStatement.setString(4,course.getCourseRoom());
                preparedStatement.setInt(5,course.getCourseStart());
                preparedStatement.setInt(6,course.getCourseStep());
                preparedStatement.setString(7,course.getCourseTeacher());
                preparedStatement.setString(8,course.getCourseWeekList());
                preparedStatement.setInt(9,course.getCourseColor());
                preparedStatement.setInt(10,course.getCourseCredit());
                preparedStatement.setInt(11,course.getCourseType());
                preparedStatement.setString(12,course.getCourseTerm());

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
    public void update(Course course) {
        String query = "UPDATE table_course SET course_name = ?," +
                "course_day, " +
                "course_room, " +
                "course_start, " +
                "course_step, " +
                "course_teacher, " +
                "course_week_list, " +
                "course_color, " +
                "course_credit, " +
                "course_type, " +
                "course_term) " +
                "WHERE course_id = ? ";

        //Open connection
        if (this.OpenConnection()) {
            //create mysql command
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                preparedStatement.setInt(12,course.getCourseID());
                preparedStatement.setString(1,course.getCourseName());
                preparedStatement.setInt(2,course.getCourseDay());
                preparedStatement.setString(3,course.getCourseRoom());
                preparedStatement.setInt(4,course.getCourseStart());
                preparedStatement.setInt(5,course.getCourseStep());
                preparedStatement.setString(6,course.getCourseTeacher());
                preparedStatement.setString(7,course.getCourseWeekList());
                preparedStatement.setInt(8,course.getCourseColor());
                preparedStatement.setInt(9,course.getCourseCredit());
                preparedStatement.setInt(10,course.getCourseType());
                preparedStatement.setString(11,course.getCourseTerm());
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
    public void delete(int courseId){
        String query = "DELETE FROM table_course WHERE course_id = ? ";

        //Open connection
        if (this.OpenConnection()) {
            //create mysql command
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1,courseId);



                //Execute query
                preparedStatement.execute();

                //close connection
                this.CloseConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    //Select statement
    public List<Course> selectAll(String cuserUserID) {
        String query = "SELECT * FROM table_course, table_cuser WHERE course_id = cuser_course_id and cuser_user_id = ?";

        //Create a class[] to store the result
        List<Course> list = new ArrayList<Course>();
        //Open connection
        if (this.OpenConnection()) {
            try {
                //Create Command
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                //Create a data reader and Execute the command
                preparedStatement.setString(1,cuserUserID);
                ResultSet resultSet= preparedStatement.executeQuery();
                //Read the data and store them in the list
                while (resultSet.next()) {
                    Course course = new Course();
                    course.setCourseID(resultSet.getInt(1));
                    course.setCourseName(resultSet.getString(2));
                    course.setCourseDay(resultSet.getInt(3));
                    course.setCourseRoom(resultSet.getString(4));
                    course.setCourseStart(resultSet.getInt(5));
                    course.setCourseStep(resultSet.getInt(6));
                    course.setCourseTeacher(resultSet.getString(7));
                    course.setCourseWeekList(resultSet.getString(8));
                    course.setCourseColor(resultSet.getInt(9));
                    course.setCourseCredit(resultSet.getInt(10));
                    course.setCourseType(resultSet.getInt(11));
                    course.setCourseTerm(resultSet.getString(12));

                    list.add(course);
                }

                //close Data Reader
                resultSet.close();

                //close Connection
                this.CloseConnection();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return list;
        } else {
            return list;
        }
    }
}
