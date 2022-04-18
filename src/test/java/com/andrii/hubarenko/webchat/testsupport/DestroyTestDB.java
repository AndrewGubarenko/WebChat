package com.andrii.hubarenko.webchat.testsupport;

import com.andrii.hubarenko.webchat.services.ConnectionService;
import com.andrii.hubarenko.webchat.startupactions.StartUpActions;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Andrii Hubarenko
 * These actions must be executed on sturtup
 */
public class DestroyTestDB {
    private ConnectionService connectionService;
    private static final Logger LOGGER = Logger.getLogger(StartUpActions.class);

    public DestroyTestDB() {
        connectionService = new ConnectionService();
    }

    public void removeTables() {
        try (Connection con = connectionService.getConnection();
             Statement statement = con.createStatement()){
            statement.executeUpdate("DROP TABLE Messages ");
            statement.executeUpdate("DROP TABLE Users");
        } catch (SQLException ex) {
            LOGGER.warn(ex.getMessage());
        }
    }
}
