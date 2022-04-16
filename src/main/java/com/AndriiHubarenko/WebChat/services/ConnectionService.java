package com.AndriiHubarenko.WebChat.services;

import com.AndriiHubarenko.WebChat.repositories.UserRepository;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionService {

    private static final Logger LOGGER = Logger.getLogger(UserRepository.class);

    private String url;
    private String username;
    private String password;
    private Connection con;

    public ConnectionService() {
        Properties props = new Properties();
        try (InputStream in = getClass().getResourceAsStream("/database.properties")){
            props.load(in);
            url = props.getProperty("jdbc.url");
            username = props.getProperty("jdbc.username");
            password = props.getProperty("jdbc.password");
         Class.forName(props.getProperty("jdbc.driver"));
        } catch (ClassNotFoundException | IOException e) {
            LOGGER.warn(e.getMessage());
        }
    }

    public Connection getConnection() {

        try {
            con = DriverManager.getConnection(url, username, password);
            return con;
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }
        return null;
    }

}
