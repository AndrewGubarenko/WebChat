package com.AndriiHubarenko.WebChat.repositories;

import com.AndriiHubarenko.WebChat.domain.Message;
import com.AndriiHubarenko.WebChat.repositories.repositories.CustomCRUDRepository;
import com.AndriiHubarenko.WebChat.services.ConnectionService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrii Hubarenko
 */
public class MessageRepository implements CustomCRUDRepository<Message, String>{

    private ConnectionService connectionService;

    public MessageRepository () {
        connectionService = new ConnectionService();
    }


    @Override
    public boolean create(Message message) {
        boolean result = false;
        Connection con = null;
        try {
            con = connectionService.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement("INSERT into MESSAGES (message, author, user_id) VALUES (?,?,?)");
            preparedStatement.setString(1, message.getMessage());
            preparedStatement.setString(2, message.getAuthor());
            preparedStatement.setLong(3, message.getUser_id());
            result = preparedStatement.executeUpdate() > 0;
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } finally {
            try {
                if (con.isClosed() || con != null) {
                    con.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return result;
    }

    public List<Message> getList() {
        Connection con = null;
        List<Message> listResult = new ArrayList<>();
        try {
            con = connectionService.getConnection();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM MESSAGES");
            while(resultSet.next()) {
                listResult.add(new Message(Long.parseLong(resultSet.getString("id")),
                        resultSet.getString("message"),
                        resultSet.getString("author"),
                        Long.parseLong(resultSet.getString("user_id"))));
            }
            statement.close();
        }catch (SQLException ex) {
            ex.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } finally {
            try {
                if (con.isClosed() || con != null) {
                    con.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
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
        Connection con = null;
        Message foundMessage = null;
        try {
            con = connectionService.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM MESSAGES WHERE message=(?)");
            preparedStatement.setString(1, message);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                foundMessage = new Message(Long.parseLong(resultSet.getString("id")),
                        resultSet.getString("message"),
                        resultSet.getString("author"),
                        Long.parseLong(resultSet.getString("user_id")));
            }
            preparedStatement.close();
        }catch (SQLException ex) {
            ex.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } finally {
            try {
                if (con.isClosed() || con != null) {
                    con.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
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
