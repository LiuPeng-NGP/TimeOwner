package com.example.timeowner.dbconnect;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.timeowner.object.User;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBConnectUser extends DBConnect{
    public void insert(User user) {

        String query = "INSERT INTO table_user (user_id, " +
                "user_password, " +
                "user_email, " +
                "user_name, " +
                "user_picture, " +
                "user_recent_channel_id) " +
                "VALUES(?, ?, ?, ?, ?, ?)";


        //open connection
        if (this.OpenConnection()) {
            //create command and assign the query and connection from the constructor

            try {

                PreparedStatement preparedStatement = connection.prepareStatement(query);

                preparedStatement.setString(1,user.getUserID());
                preparedStatement.setString(2,user.getUserPassword());
                preparedStatement.setString(3,user.getUserEmail());
                preparedStatement.setString(4,user.getUserName());
                preparedStatement.setBytes(5,user.bitmapToBLOB());
                preparedStatement.setString(6,user.getUserID());

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
    public void update(User user) {
        String query = "UPDATE table_user SET user_name = ?," +
                "user_password = ? " +
                "user_email = ? " +
                "user_name = ? " +
                "user_picture = ? " +
                "user_recent_channel_id = ? " +
                "WHERE user_id = ? ";

        //Open connection
        if (this.OpenConnection()) {
            //create mysql command
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(6,user.getUserID());
                preparedStatement.setString(2,user.getUserPassword());
                preparedStatement.setString(3,user.getUserEmail());
                preparedStatement.setString(4,user.getUserName());
                preparedStatement.setBytes(5,user.bitmapToBLOB());
                preparedStatement.setString(1,user.getUserID());


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
        String query = "DELETE FROM table_user WHERE user_id = ? ";

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
                    User user = new User(resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            bitmap,
                            resultSet.getString(6));
                    list.add(user);

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
        String query = "SELECT * FROM table_user WHERE user_id = ? ";
        User user = new User();
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
                    user.setUserID(resultSet.getString(1));
                    user.setUserPassword(resultSet.getString(2));
                    user.setUserEmail(resultSet.getString(3));
                    user.setUserName( resultSet.getString(4));
                    user.setUserPicture(bitmap);
                    user.setUserRecentChannel(resultSet.getString(6));
                }


                //close connection
                this.CloseConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }
}
