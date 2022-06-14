package com.example.timeowner.dbconnect;

import com.example.timeowner.object.Habit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DBConnectHabit extends DBConnect{
    public void insert(Habit habit) {

        String query = "INSERT INTO table_habit (habit_id, " +
                "habit_name, " +
                "habit_count, " +
                "habit_today_is_completed, " +
                "habit_user_id) " +
                "VALUES(?, ?, ?, ?, ?)";


        //open connection
        if (this.OpenConnection()) {
            //create command and assign the query and connection from the constructor

            try {

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                preparedStatement.setInt(1,habit.getHabitID());
                preparedStatement.setString(2,habit.getHabitName());
                preparedStatement.setInt(3,habit.getHabitCount());
                preparedStatement.setInt(4,habit.getHabitTodayIsCompleted());
                preparedStatement.setString(5,habit.getHabitUserID());

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
    public void update(Habit habit) {
        String query = "UPDATE table_habit SET habit_name = ?," +
                "habit_count = ? " +
                "habit_today_is_completed = ? " +
                "habit_user_id = ? " +
                "WHERE habit_id = ? ";

        //Open connection
        if (this.OpenConnection()) {
            //create mysql command
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                preparedStatement.setInt(6,habit.getHabitID());
                preparedStatement.setString(1,habit.getHabitName());
                preparedStatement.setInt(2,habit.getHabitCount());
                preparedStatement.setInt(4,habit.getHabitTodayIsCompleted());
                preparedStatement.setString(5,habit.getHabitUserID());
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
    public void delete(int habitId){
        String query = "DELETE FROM table_habit WHERE habit_id = ? ";

        //Open connection
        if (this.OpenConnection()) {
            //create mysql command
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1,habitId);



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
    public List<Habit> selectAll(String habitUserID) {
        String query = "SELECT * FROM table_habit WHERE habit_user_id = ?";

        //Create a class[] to store the result
        List<Habit> list = new ArrayList<Habit>();
        //Open connection
        if (this.OpenConnection()) {
            try {
                //Create Command
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                //Create a data reader and Execute the command
                preparedStatement.setString(1,habitUserID);
                ResultSet resultSet= preparedStatement.executeQuery();
                //Read the data and store them in the list
                while (resultSet.next()) {
                    Habit habit = new Habit();
                    habit.setHabitID(resultSet.getInt(1));
                    habit.setHabitName(resultSet.getString(2));
                    habit.setHabitCount(resultSet.getInt(3));
                    habit.setHabitTodayIsCompleted(resultSet.getInt(4));
                    habit.setHabitUserID(resultSet.getString(5));

                    list.add(habit);
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
