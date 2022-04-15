package com.AndriiHubarenko.WebChat.services;

import java.sql.*;

public class ConnectionService {

    private static final String url = "jdbc:h2:~/WebChatDB";
    private static final String driver = "org.h2.Driver";
    private static final String login = "sa";
    private static final String password = "";

    public ConnectionService() {
         try {
             Class.forName(driver);
         } catch (ClassNotFoundException e) {
             e.printStackTrace();
         }
    }

    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, login, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

}
