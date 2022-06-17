package com.example.timeowner.dbconnect;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.timeowner.object.Target;
import com.example.timeowner.object.User;

import java.io.UTFDataFormatException;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.text.SimpleDateFormat;

public class DBConnectTarget extends DBConnect{
    public void insert(Target target) {

        String query = "INSERT INTO table_target (target_id, " +
                "target_name, " +
                "target_start_time, " +
                "target_end_time, " +
                "target_details, " +
                "target_is_completed, " +
                "target_user_id) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";


        //open connection
        if (this.OpenConnection()) {
            //create command and assign the query and connection from the constructor

            try {

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                preparedStatement.setInt(1,target.getTargetID());
                preparedStatement.setString(2,target.getTargetName());
                preparedStatement.setString(3,target.getTargetStartTime());
                preparedStatement.setString(4,target.getTargetEndTime());
                preparedStatement.setString(5,target.getTargetDetails());
                preparedStatement.setInt(6,target.getTargetIsCompleted());
                preparedStatement.setString(7,target.getTargetUserId());

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
    public void update(Target target) {
        String query = "UPDATE table_target SET target_name = ?," +
                "target_start_time = ? " +
                "target_end_time = ? " +
                "target_is_completed = ? " +
                "target_user_id = ? " +
                "WHERE target_id = ? ";

        //Open connection
        if (this.OpenConnection()) {
            //create mysql command
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                preparedStatement.setInt(6,target.getTargetID());
                preparedStatement.setString(1,target.getTargetName());
                preparedStatement.setString(2,target.getTargetStartTime());
                preparedStatement.setString(3,target.getTargetEndTime());
                preparedStatement.setInt(4,target.getTargetIsCompleted());
                preparedStatement.setString(5,target.getTargetUserId());


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
        String query = "DELETE FROM table_target WHERE target_id = ? ";

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


    //Select statement
    public ArrayList<Target> selectAll(String id) {
        String query = "SELECT * FROM table_target WHERE target_user_id = ?";

        //Create a class[] to store the result
        ArrayList<Target> list = new ArrayList<Target>();
        //Open connection
        if (this.OpenConnection()) {
            try {
                //Create Command
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                //Create a data reader and Execute the command
                preparedStatement.setString(1,id);
                ResultSet resultSet= preparedStatement.executeQuery();
                //Read the data and store them in the list
                while (resultSet.next()) {
                    Target target = new Target();
                    target.setTargetID(resultSet.getInt(1));
                    target.setTargetName(resultSet.getString(2));
                    target.setTargetStartTime(resultSet.getString(3));
                    target.setTargetEndTime(resultSet.getString(4));
                    target.setTargetDetails(resultSet.getString(5));
                    target.setTargetIsCompleted(resultSet.getInt(6));
                    target.setTargetUserId(resultSet.getString(7));

                    list.add(target);
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




    public Target select(String id){
        String query = "SELECT * FROM table_target WHERE target_id = ? ";
        Target target = new Target();
        //Open connection
        if (this.OpenConnection()) {
            //create mysql command
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1,id);


                //Execute query
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    target.setTargetName(resultSet.getString(1));
                    target.setTargetStartTime(resultSet.getString(2));
                    target.setTargetEndTime(resultSet.getString(3));
                    target.setTargetDetails(resultSet.getString(4));
                    target.setTargetIsCompleted(resultSet.getInt(5));
                    target.setTargetUserId(resultSet.getString(6));

                    return target;
                }


                //close connection
                this.CloseConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return target;
    }
}
