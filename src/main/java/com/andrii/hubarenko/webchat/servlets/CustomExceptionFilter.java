package com.andrii.hubarenko.webchat.servlets;

import com.andrii.hubarenko.webchat.repositories.UserRepository;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "CustomExceptionFilter", urlPatterns = "/*")
public class CustomExceptionFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(UserRepository.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            servletResponse.getWriter().write("Something went wrong!");
            LOGGER.warn(e.getMessage());
        }
    }
}
