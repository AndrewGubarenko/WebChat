package com.andrii.hubarenko.webchat.services;

import com.andrii.hubarenko.webchat.domain.Message;
import com.andrii.hubarenko.webchat.domain.User;
import com.andrii.hubarenko.webchat.startupactions.StartUpActions;
import com.andrii.hubarenko.webchat.testsupport.DestroyTestDB;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Andrii Hubarenko
 */
class MessageServiceTest {

    private static UserService userService;
    private static MessageService messageService;
    private static User user;

    @BeforeAll
    static void init() {
        new StartUpActions().createTables();
        userService = new UserService();
        messageService = new MessageService();
        user = (User) userService.joinChat(new User("Olaf")).get(1);
    }

    @AfterAll
    static void destroy() {
        new DestroyTestDB().removeTables();
    }

    /**
     * Testing does the method correctly create Message and are the fields correct
     */
    @Test
    void createMessage() {
        Message message = messageService.createMessage(new Message("Test message", user.getNickName(), user.getId()));
        assertEquals("Test message", message.getMessage());
        assertEquals(this.user.getId(), message.getUser_id());
        assertEquals(this.user.getNickName(), message.getAuthor());
        assertTrue(message.getId() > 0);
    }

    /**
     * Testing does the method return correct amount of messages
     * when user updates his screen
     */
    @Test
    void getAllMessages() {
        for(int i = 0; i < 60; i++) {
            messageService.createMessage(new Message("Test for list #" + i, user.getNickName(), user.getId()));
        }
        List<Message> list = messageService.getAllMessages();
        assertEquals(50, list.size());
    }
}