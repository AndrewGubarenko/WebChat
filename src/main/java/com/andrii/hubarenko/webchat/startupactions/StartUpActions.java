package com.andrii.hubarenko.webchat.startupactions;

import com.andrii.hubarenko.webchat.services.ConnectionService;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Andrii Hubarenko
 * These actions must be executed on sturtup
 */
public class StartUpActions {

    private ConnectionService connectionService;
    private static final Logger LOGGER = Logger.getLogger(StartUpActions.class);

    public StartUpActions() {
        connectionService = new ConnectionService();
    }

    public void createTables() {
        try (Connection con = connectionService.getConnection();
             Statement statement = con.createStatement()){
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS USERS (id int NOT NULL PRIMARY KEY AUTO_INCREMENT, NickName varchar(255) UNIQUE NOT NULL)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS MESSAGES (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, Message VARCHAR(255) NOT NULL, Author VARCHAR(255) NOT NULL, user_id INT NOT NULL, FOREIGN KEY (user_id) REFERENCES USERS(id))");
        } catch (SQLException ex) {
            LOGGER.warn(ex.getMessage());
        }
    }
}
