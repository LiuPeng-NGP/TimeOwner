package com.example.timeowner.dbconnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    protected Connection connection;
    protected String driver;
    protected String server;
    protected String database;
    protected String uid;
    protected String password;

    //Constructor
    public DBConnect() {
        Initialize();
    }

    //Initialize values
    protected void Initialize() {
        driver = "com.mysql.jdbc.Driver";
        server = "121.37.70.47";
        database = "TimeOwner";
        uid = "huaweiyun";
        password = "123456hwy";
    }

    //open connection to database
    protected Boolean OpenConnection() {
        String connectionString;
        connectionString = "jdbc:mysql://" + server + ":3306/" + database + "?useSSL=false";
        try{
            Class.forName(driver);
            connection = DriverManager.getConnection(connectionString,
                    uid,
                    password);
            return true;
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    //Close connection
    protected Boolean CloseConnection() {
        try {
            connection.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

}