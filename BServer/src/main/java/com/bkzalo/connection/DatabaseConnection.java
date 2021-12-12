package com.bkzalo.connection;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    private DatabaseConnection() {
    	// Sang was fixed
    	try {
    		Class.forName("com.mysql.jdbc.Driver");
            String server = "localhost";
            String port = "3306";
            String database = "bkzalo";
            String userName = "root";
            String password = "";
            connection = java.sql.DriverManager.getConnection("jdbc:mysql://" + server + ":" + port + "/" + database, userName, password);
            System.out.println("Connection Success");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public void connectToDatabase() throws SQLException {
    	String server = "localhost";
        String port = "3306";
        String database = "bkzalo";
        String userName = "root";
        String password = "";
        connection = java.sql.DriverManager.getConnection("jdbc:mysql://" + server + ":" + port + "/" + database, userName, password);
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
