package com.andrii.hubarenko.webchat.repositories;

import com.andrii.hubarenko.webchat.domain.User;
import com.andrii.hubarenko.webchat.startupactions.StartUpActions;
import com.andrii.hubarenko.webchat.testsupport.DestroyTestDB;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Andrii Hubarenko
 */
class UserRepositoryTest {
    private static UserRepository repository;

    @BeforeAll
    static void init() {
        new StartUpActions().createTables();
        repository = new UserRepository();
        repository.create(new User("Olaf"));
    }

    @AfterAll
    static void destroy() {
        new DestroyTestDB().removeTables();
    }

    /**
     * Testing does query in the method correctly saving User in DB
     */
    @Test
    void create() {
        assertTrue(repository.create(new User("Helga")));
    }

    /**
     * Testing does query in the method correctly retrieving the User from DB
     */
    @Test
    void get() {
        User user = repository.get("Olaf");
        assertNotNull(user);
        assertEquals("Olaf", user.getNickName());
        assertTrue(user.getId() > 0);
    }

    /**
     * Testing does the method throw any exception
     * (if not - the queries are correct)
     */
    @Test
    void onQueryCorrect() {
        assertDoesNotThrow(() -> repository.create(new User("Sten")));
        assertDoesNotThrow(() -> repository.get("Sten"));
    }

}