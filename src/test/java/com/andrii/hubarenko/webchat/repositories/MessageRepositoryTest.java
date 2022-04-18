package com.andrii.hubarenko.webchat.repositories;

import com.andrii.hubarenko.webchat.domain.Message;
import com.andrii.hubarenko.webchat.domain.User;
import com.andrii.hubarenko.webchat.startupactions.StartUpActions;
import com.andrii.hubarenko.webchat.testsupport.DestroyTestDB;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Andrii Hubarenko
 */
class MessageRepositoryTest {

    private static UserRepository userRepository;
    private static MessageRepository messageRepository;
    private static Message message;
    private static User user;

    @BeforeAll
    static void init() {
        new StartUpActions().createTables();
        userRepository = new UserRepository();
        messageRepository = new MessageRepository();
        userRepository.create(new User("Olaf"));
        user = userRepository.get("Olaf");
        message = new Message("Test message" ,user.getNickName(), user.getId());
    }

    @AfterAll
    static void destroy() {
        new DestroyTestDB().removeTables();
    }

    /**
     * Testing does query in the method correctly saving Message in DB
     */
    @Test
    void create() {
        assertTrue(messageRepository.create(message));
    }

    /**
     * Testing does query in the method return correct amount of messages
     */
    @Test
    void getList() {
        for(int i = 0; i < 60; i++) {
            messageRepository.create(new Message("Test for list #" + i, user.getNickName(), user.getId()));
        }
        List<Message> list = messageRepository.getList();
        assertEquals(50, list.size());
    }

    /**
     * Testing does query in the method correctly retrieving the last saved Message from DB
     */
    @Test
    void get() {
        assertTrue(messageRepository.create(message));
        Message message = messageRepository.get("Test message");
        assertNotNull(message);
        assertEquals("Test message", message.getMessage());
        assertEquals(this.user.getId(), message.getUser_id());
        assertEquals(this.user.getNickName(), message.getAuthor());
        assertTrue(message.getId() > 0);
    }

    /**
     * Testing does the method throw any exception
     * (if not - the queries are correct)
     */
    @Test
    void onQueryCorrect() {
        assertDoesNotThrow(() -> messageRepository.create(new Message("Test message not throw" ,user.getNickName(), user.getId())));
        assertDoesNotThrow(() -> messageRepository.get("Test message not throw"));
        assertDoesNotThrow(() -> messageRepository.getList());
    }
}