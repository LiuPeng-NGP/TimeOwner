package com.example.timeowner.dbconnect;

import com.example.timeowner.object.Event;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DBConnectEvent extends DBConnect{
    public void insert(Event event) {

        String query = "INSERT INTO table_event (event_id, " +
                "event_name, " +
                "event_time, " +
                "event_details, " +
                "event_today_is_completed, " +
                "event_user_id) " +
                "VALUES(?, ?, ?, ?, ?, ?)";


        //open connection
        if (this.OpenConnection()) {
            //create command and assign the query and connection from the constructor

            try {

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                preparedStatement.setInt(1,event.getEventID());
                preparedStatement.setString(2,event.getEventName());
                preparedStatement.setString(3, event.getEventTime());
                preparedStatement.setString(4, event.getEventDetails());
                preparedStatement.setInt(5,event.getEventIsCompleted());
                preparedStatement.setString(6,event.getEventUserID());

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
    public void update(Event event) {
        String query = "UPDATE table_event SET event_name = ?," +
                "event_time, " +
                "event_details, " +
                "event_today_is_completed, " +
                "event_user_id) " +
                "WHERE event_id = ? ";

        //Open connection
        if (this.OpenConnection()) {
            //create mysql command
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                preparedStatement.setInt(6,event.getEventID());
                preparedStatement.setString(1,event.getEventName());
                preparedStatement.setString(2,event.getEventTime());
                preparedStatement.setString(3,event.getEventDetails());
                preparedStatement.setInt(4,event.getEventIsCompleted());
                preparedStatement.setString(5,event.getEventUserID());
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
    public void delete(int eventId){
        String query = "DELETE FROM table_event WHERE event_id = ? ";

        //Open connection
        if (this.OpenConnection()) {
            //create mysql command
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1,eventId);



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
    public List<Event> selectAll(String eventUserID) {
        String query = "SELECT * FROM table_event WHERE event_user_id = ?";

        //Create a class[] to store the result
        List<Event> list = new ArrayList<Event>();
        //Open connection
        if (this.OpenConnection()) {
            try {
                //Create Command
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                //Create a data reader and Execute the command
                preparedStatement.setString(1,eventUserID);
                ResultSet resultSet= preparedStatement.executeQuery();
                //Read the data and store them in the list
                while (resultSet.next()) {
                    Event event = new Event();
                    event.setEventID(resultSet.getInt(1));
                    event.setEventName(resultSet.getString(2));
                    event.setEventTime(resultSet.getString(3));
                    event.setEventDetails(resultSet.getString(4));
                    event.setEventIsCompleted(resultSet.getInt(5));
                    event.setEventUserID(resultSet.getString(6));

                    list.add(event);
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
