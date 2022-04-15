package com.AndriiHubarenko.WebChat.startUpActions;

import com.AndriiHubarenko.WebChat.services.ConnectionService;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Andrii Hubarenko
 * These actions must be executed on sturtup
 */
public class StartUpActions {

    private ConnectionService connectionService;

    public StartUpActions() {
        connectionService = new ConnectionService();
    }

    public void createTables() {
        try {
            Connection con = connectionService.getConnection();
            Statement statement = con.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS USERS (id int NOT NULL PRIMARY KEY AUTO_INCREMENT, NickName varchar(255) UNIQUE NOT NULL)");
            statement.execute("CREATE TABLE IF NOT EXISTS MESSAGES (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, Message VARCHAR(255) NOT NULL, Author VARCHAR(255) NOT NULL, user_id INT NOT NULL, FOREIGN KEY (user_id) REFERENCES USERS(id))");
            statement.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
