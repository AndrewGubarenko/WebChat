package com.andrii.hubarenko.webchat.repositories;

import com.andrii.hubarenko.webchat.domain.Message;
import com.andrii.hubarenko.webchat.domain.User;
import com.andrii.hubarenko.webchat.repositories.repositories.CustomCRUDRepository;
import com.andrii.hubarenko.webchat.services.ConnectionService;
import org.apache.log4j.Logger;

import java.sql.*;

/**
 * @author Andrii Hubarenko
 */
public class UserRepository implements CustomCRUDRepository<User, String> {

    private ConnectionService connectionService;
    private static final Logger LOGGER = Logger.getLogger(UserRepository.class);

    public UserRepository () {
        connectionService = new ConnectionService();
    }


    @Override
    public boolean create(User user) {
        boolean result = false;
        try (Connection con = connectionService.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement("INSERT into USERS (NickName) VALUES (?)")){
            preparedStatement.setString(1, user.getNickName());
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.warn(ex.getMessage());
        }
        return result;
    }

    @Override
    public User get(String nickName) {
        User user = null;
        try (Connection con = connectionService.getConnection();
             PreparedStatement statementToGetUser = con.prepareStatement("SELECT * " +
                                                                             "from USERS " +
                                                                             "WHERE nickname=(?)");
             Statement statementToGetMessages = con.createStatement()) {
            statementToGetUser.setString(1, nickName);
            ResultSet resultSet = statementToGetUser.executeQuery();
            if(resultSet.next()) {
                user = new User(resultSet.getLong("id"), resultSet.getString("nickName"));
                resultSet = statementToGetMessages.executeQuery("SELECT * " +
                                                                    "from MESSAGES " +
                                                                    "ORDER BY id DESC " +
                                                                    "LIMIT 50");
                while(resultSet.next()) {
                    user.getMessageList().add(new Message(resultSet.getLong("id"),
                            resultSet.getString("message"),
                            resultSet.getString("author"),
                            resultSet.getLong("user_id")));
                }
            }

        } catch (SQLException ex) {
            LOGGER.warn(ex.getMessage());
        }
        return user;
    }

    /**
     * Method for updating messages (may be realized in future for editing user's data)
     * @param user
     * @return updated User
     */
    @Override
    public User update(User user) {
        return null;
    }

    /**
     * Method for deleting messages (may be realized in future for removing users)
     * @param nickName
     * @return String message about status of operation
     */
    @Override
    public String delete(String nickName) {
        return null;
    }
}
