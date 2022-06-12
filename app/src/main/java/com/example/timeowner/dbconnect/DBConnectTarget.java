package com.example.timeowner.dbconnect;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.timeowner.object.Target;
import com.example.timeowner.object.User;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
                "VALUES(?, ?, ?, ?, ?, ?)";


        //open connection
        if (this.OpenConnection()) {
            //create command and assign the query and connection from the constructor

            try {

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                preparedStatement.setString(1,target.getTargetID());
                preparedStatement.setString(2,target.getTargetName());
                preparedStatement.setString(3,sdf.format(target.getTargetStartTime()));
                preparedStatement.setString(4,sdf.format(target.getTargetEndTime()));
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
    public void update(User target) {
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
                preparedStatement.setString(6,target.getUserID());
                preparedStatement.setString(2,target.getUserPassword());
                preparedStatement.setString(3,target.getUserEmail());
                preparedStatement.setString(4,target.getUserName());
                preparedStatement.setBytes(5,target.bitmapToBLOB());
                preparedStatement.setString(1,target.getUserID());


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
    public List<User> selectAll() {
        String query = "SELECT * FROM curriculum";

        //Create a class[] to store the result
        List<User> list = new ArrayList<User>();

        //Open connection
        if (this.OpenConnection()) {
            try {
                //Create Command
                Statement cmd =connection.createStatement();
                //Create a data reader and Execute the command
                ResultSet resultSet= cmd.executeQuery(query);

                //Read the data and store them in the list
                while (resultSet.next()) {
                    Blob blob = resultSet.getBlob(5);
                    int blobLength = (int) blob.length();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(blob.getBytes(1,blobLength), 0 ,blobLength);
                    User target = new User(resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            bitmap,
                            resultSet.getString(6));
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






    public User select(int id){
        String query = "SELECT * FROM table_target WHERE target_id = ? ";
        User target = new User();
        //Open connection
        if (this.OpenConnection()) {
            //create mysql command
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1,id);


                //Execute query
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    Blob blob = resultSet.getBlob(5);
                    int blobLength = (int) blob.length();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(blob.getBytes(1,blobLength), 0 ,blobLength);
                    target.setUserID(resultSet.getString(1));
                    target.setUserPassword(resultSet.getString(2));
                    target.setUserEmail(resultSet.getString(3));
                    target.setUserName( resultSet.getString(4));
                    target.setUserPicture(bitmap);
                    target.setUserRecentChannel(resultSet.getString(6));
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
