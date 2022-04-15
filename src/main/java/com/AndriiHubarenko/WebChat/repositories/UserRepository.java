package com.AndriiHubarenko.WebChat.repositories;

import com.AndriiHubarenko.WebChat.domain.Message;
import com.AndriiHubarenko.WebChat.domain.User;
import com.AndriiHubarenko.WebChat.repositories.repositories.CustomCRUDRepository;
import com.AndriiHubarenko.WebChat.services.ConnectionService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Andrii Hubarenko
 */
public class UserRepository implements CustomCRUDRepository<User, String> {

    private ConnectionService connectionService;

    public UserRepository () {
        connectionService = new ConnectionService();
    }


    @Override
    public boolean create(User user) {
        Connection con = null;
        boolean result = false;
        try {
            con = connectionService.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement("INSERT into USERS (NickName) VALUES (?)");
            preparedStatement.setString(1, user.getNickName());
            result = preparedStatement.executeUpdate() > 0;

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

    @Override
    public User get(String nickName) {
        Connection con = null;
        User user = null;
        try {
            con = connectionService.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * from USERS WHERE nickname=(?)");
            preparedStatement.setString(1, nickName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                user = new User(Long.parseLong(resultSet.getString("id")), resultSet.getString("nickName"));
            }
            preparedStatement.close();
            PreparedStatement statement = con.prepareStatement("SELECT * from MESSAGES WHERE user_id=(?) ORDER BY id DESC LIMIT 50");
            statement.setLong(1, user.getId());
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                user.getMessageList().add(new Message(Long.parseLong(resultSet.getString("id")),
                        resultSet.getString("message"),
                        resultSet.getString("author"),
                        Long.parseLong(resultSet.getString("user_id"))));
            }
            statement.close();
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
            return user;
        }
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
