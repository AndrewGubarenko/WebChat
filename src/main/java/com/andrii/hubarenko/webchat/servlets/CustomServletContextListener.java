package com.andrii.hubarenko.webchat.servlets;

import com.andrii.hubarenko.webchat.startupactions.StartUpActions;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class CustomServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        new StartUpActions().createTables();
    }

}
