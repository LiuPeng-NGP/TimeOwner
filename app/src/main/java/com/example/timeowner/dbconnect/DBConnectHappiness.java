package com.example.timeowner.dbconnect;

import com.example.timeowner.object.Happiness;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DBConnectHappiness extends DBConnect{
    public void insert(Happiness happiness) {

        String query = "INSERT INTO table_happiness (happiness_id, " +
                "happiness_text) " +
                "VALUES(?, ?)";


        //open connection
        if (this.OpenConnection()) {
            //create command and assign the query and connection from the constructor

            try {

                PreparedStatement preparedStatement = connection.prepareStatement(query);


                preparedStatement.setInt(1,happiness.getHappinessID());
                preparedStatement.setString(2,happiness.getHappinessText());


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
    public void update(Happiness happiness) {
        String query = "UPDATE table_happiness SET happiness_text = ?," +
                "WHERE happiness_id = ? ";

        //Open connection
        if (this.OpenConnection()) {
            //create mysql command
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                preparedStatement.setInt(2,happiness.getHappinessID());
                preparedStatement.setString(1,happiness.getHappinessText());

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
    public void delete(int happinessId){
        String query = "DELETE FROM table_happiness WHERE happiness_id = ? ";

        //Open connection
        if (this.OpenConnection()) {
            //create mysql command
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1,happinessId);



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
    public List<Happiness> selectAll() {
        String query = "SELECT * FROM table_happiness";

        //Create a class[] to store the result
        List<Happiness> list = new ArrayList<Happiness>();
        //Open connection
        if (this.OpenConnection()) {
            try {
                //Create Command
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                //Create a data reader and Execute the command
                ResultSet resultSet= preparedStatement.executeQuery();
                //Read the data and store them in the list
                while (resultSet.next()) {
                    Happiness happiness = new Happiness();
                    happiness.setHappinessID(resultSet.getInt(1));
                    happiness.setHappinessText(resultSet.getString(2));

                    list.add(happiness);
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