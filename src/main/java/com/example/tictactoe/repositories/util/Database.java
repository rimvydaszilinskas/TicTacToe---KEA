package com.example.tictactoe.repositories.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private final static String USERNAME = "rim300197";
    private final static String PASSWORD = "Ef7hf1~dk-5a";
    private final static String CONNSTRING = "jdbc:mysql://den1.mysql1.gear.host/rim300197";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONNSTRING, USERNAME, PASSWORD);
    }
}
