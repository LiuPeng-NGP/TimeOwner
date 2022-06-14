package com.example.timeowner.dbconnect;

import com.example.timeowner.object.Cuser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DBConnectCuser extends DBConnect{
    public void insert(Cuser cuser) {

        String query = "INSERT INTO table_cuser (cuser_id, " +
                "curse_user_id, " +
                "curse_course_id) " +
                "VALUES(?, ?, ?)";


        //open connection
        if (this.OpenConnection()) {
            //create command and assign the query and connection from the constructor

            try {

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                preparedStatement.setInt(1,cuser.getCuserID());
                preparedStatement.setString(2,cuser.getCuserUserID());
                preparedStatement.setInt(3,cuser.getCuserCourseID());

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
    public void update(Cuser cuser) {
        String query = "UPDATE table_cuser SET curse_user_id = ?," +
                "curse_course_id = ? " +
                "WHERE cuser_id = ? ";

        //Open connection
        if (this.OpenConnection()) {
            //create mysql command
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                preparedStatement.setInt(3,cuser.getCuserID());
                preparedStatement.setString(1,cuser.getCuserUserID());
                preparedStatement.setInt(2,cuser.getCuserID());

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
    public void delete(int cuserId){
        String query = "DELETE FROM table_cuser WHERE cuser_id = ? ";

        //Open connection
        if (this.OpenConnection()) {
            //create mysql command
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1,cuserId);



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
    public List<Cuser> selectAll(String cuserUserID) {
        String query = "SELECT * FROM table_cuser WHERE cuser_user_id = ?";

        //Create a class[] to store the result
        List<Cuser> list = new ArrayList<Cuser>();
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
                    Cuser cuser = new Cuser();
                    cuser.setCuserID(resultSet.getInt(1));
                    cuser.setCuserUserID(resultSet.getString(2));
                    cuser.setCuserCourseID(resultSet.getInt(3));


                    list.add(cuser);
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
