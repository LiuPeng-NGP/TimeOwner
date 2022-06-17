package com.example.timeowner.dbconnect;

import com.example.timeowner.object.Concentration;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DBConnectConcentration extends DBConnect{
    public void insert(Concentration concentration) {

        String query = "INSERT INTO table_concentration (concentration_id, " +
                "concentration_time, " +
                "concentration_user_id) " +
                "VALUES(?, ?, ?)";


        //open connection
        if (this.OpenConnection()) {
            //create command and assign the query and connection from the constructor

            try {

                PreparedStatement preparedStatement = connection.prepareStatement(query);

                preparedStatement.setInt(1,concentration.getConcentrationID());
                preparedStatement.setInt(2,concentration.getConcentrationTime());
                preparedStatement.setString(3,concentration.getConcentrationUserID());


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
    public void update(Concentration concentration) {
        String query = "UPDATE table_concentration SET concentration_time = ?," +
                "concentration_user_id " +
                "WHERE concentration_id = ? ";

        //Open connection
        if (this.OpenConnection()) {
            //create mysql command
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);


                preparedStatement.setInt(3,concentration.getConcentrationID());
                preparedStatement.setInt(1,concentration.getConcentrationTime());
                preparedStatement.setString(2,concentration.getConcentrationUserID());

                preparedStatement.execute();

                //close connection
                this.CloseConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //Delete statement
    public void delete(int concentrationId){
        String query = "DELETE FROM table_concentration WHERE concentration_id = ? ";

        //Open connection
        if (this.OpenConnection()) {
            //create mysql command
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1,concentrationId);



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
    public ArrayList<Concentration> selectAll(String concentrationUserID) {
        String query = "SELECT * FROM table_concentration WHERE concentration_user_id = ?";

        //Create a class[] to store the result
        ArrayList<Concentration> list = new ArrayList<Concentration>();
        //Open connection
        if (this.OpenConnection()) {
            try {
                //Create Command
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                //Create a data reader and Execute the command
                preparedStatement.setString(1,concentrationUserID);
                ResultSet resultSet= preparedStatement.executeQuery();
                //Read the data and store them in the list
                while (resultSet.next()) {
                    Concentration concentration = new Concentration();
                    concentration.setConcentrationID(resultSet.getInt(1));
                    concentration.setConcentrationTime(resultSet.getInt(2));
                    concentration.setConcentrationUserID(resultSet.getString(3));

                    list.add(concentration);
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



    public int selectSum(String concentrationUserID) {
        String query = "SELECT sum(concentration_time) FROM table_concentration WHERE concentration_user_id = ?";


        int totalMinutes=0;

        //Open connection
        if (this.OpenConnection()) {
            try {
                //Create Command
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                //Create a data reader and Execute the command
                preparedStatement.setString(1,concentrationUserID);
                ResultSet resultSet= preparedStatement.executeQuery();
                //Read the data and store them in the list
                while (resultSet.next()) {
                    totalMinutes=resultSet.getInt(1);
                }

                //close Data Reader
                resultSet.close();

                //close Connection
                this.CloseConnection();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return totalMinutes;
        } else {
            return totalMinutes;
        }
    }
}
