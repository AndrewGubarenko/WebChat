package com.AndriiHubarenko.WebChat.services;

import com.AndriiHubarenko.WebChat.domain.User;
import com.AndriiHubarenko.WebChat.repositories.UserRepository;
import com.AndriiHubarenko.WebChat.repositories.repositories.CustomCRUDRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrii Hubarenko
 */
public class UserService {

    private CustomCRUDRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    /**@author Andrii Hubarenko
     * Method create new user by nick name or return user, if it exist, with the last 50 messages in the chat
     * @param user
     * @return user
     */
    public List joinChat(User user) {
        List result = new ArrayList(2);
        User createdUser = (User) userRepository.get(user.getNickName());
        if (createdUser != null) {
            result.add(200);
            result.add(createdUser);
            return result;
        } else if(userRepository.create(user)){
            result.add(201);
            result.add(userRepository.get(user.getNickName()));
        }
        return result;
    }
}
