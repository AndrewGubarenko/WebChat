package com.AndriiHubarenko.WebChat.repositories;

import com.AndriiHubarenko.WebChat.domain.Message;
import com.AndriiHubarenko.WebChat.repositories.repositories.CustomCRUDRepository;
import com.AndriiHubarenko.WebChat.services.ConnectionService;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrii Hubarenko
 */
public class MessageRepository implements CustomCRUDRepository<Message, String>{

    private ConnectionService connectionService;
    private static final Logger LOGGER = Logger.getLogger(MessageRepository.class);

    public MessageRepository () {
        connectionService = new ConnectionService();
    }

    @Override
    public boolean create(Message message) {
        boolean result = false;
        try (Connection con = connectionService.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement("INSERT into MESSAGES (message, author, user_id) VALUES (?,?,?)")){
            preparedStatement.setString(1, message.getMessage());
            preparedStatement.setString(2, message.getAuthor());
            preparedStatement.setLong(3, message.getUser_id());
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.warn(ex.getMessage());
        }
        return result;
    }

    public List<Message> getList() {
        List<Message> listResult = new ArrayList<>();
        try (Connection con = connectionService.getConnection();
             Statement statement = con.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM MESSAGES ORDER BY id DESC");
            while(resultSet.next()) {
                listResult.add(new Message(Long.parseLong(resultSet.getString("id")),
                        resultSet.getString("message"),
                        resultSet.getString("author"),
                        Long.parseLong(resultSet.getString("user_id"))));
            }
        }catch (SQLException ex) {
            LOGGER.warn(ex.getMessage());
        }
        return listResult;
    }

    /**
     * Method using for retreiving of single message by attributes
     * @param message
     * @return Message
     */
    @Override
    public Message get(String message) {
        Message foundMessage = null;
        try (Connection con = connectionService.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM MESSAGES WHERE message=(?)")){
            preparedStatement.setString(1, message);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                foundMessage = new Message(Long.parseLong(resultSet.getString("id")),
                        resultSet.getString("message"),
                        resultSet.getString("author"),
                        Long.parseLong(resultSet.getString("user_id")));
            }
        }catch (SQLException ex) {
            LOGGER.warn(ex.getMessage());
        }
        return foundMessage;
    }

    /**
     * Method for updating messages (may be realized in future for editing messages)
     * @param message
     * @return updated message
     */
    @Override
    public Message update(Message message) {
        return null;
    }

    /**
     * Method for deleting messages (may be realized in future for removing messages)
     * @param message
     * @return String message about status of operation
     */
    @Override
    public String delete(String message) {
        return null;
    }
}
