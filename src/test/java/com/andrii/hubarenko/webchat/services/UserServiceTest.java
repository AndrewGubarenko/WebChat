package com.andrii.hubarenko.webchat.services;

import com.andrii.hubarenko.webchat.domain.User;
import com.andrii.hubarenko.webchat.startupactions.StartUpActions;
import com.andrii.hubarenko.webchat.testsupport.DestroyTestDB;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Andrii Hubarenko
 */
class UserServiceTest {

    private static UserService service;

    @BeforeAll
    static void init() {
        new StartUpActions().createTables();
        service = new UserService();
    }

    @AfterAll
    static void destroy() {
        new DestroyTestDB().removeTables();
    }

    /**
     * Testing does method create new or return existing User from DB
     * and are it's fields are correct
     */
    @Test
    void joinChat() {
        List result = service.joinChat(new User("Olaf"));
        assertEquals(201, result.get(0));
        User user = (User) result.get(1);
        assertTrue(user.getId() > 0);
        assertEquals("Olaf", user.getNickName());
        List newResult = service.joinChat(new User("Olaf"));
        assertEquals(200, newResult.get(0));
    }
}