package com.andrii.hubarenko.webchat.servlets;

import com.andrii.hubarenko.webchat.domain.User;
import com.andrii.hubarenko.webchat.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "UserServlet", value = "/user")
public class UserServlet extends HttpServlet {

    private UserService service;

    @Override
    public void init() {
        this.service = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        String requestData = request.getReader().lines().collect(Collectors.joining());
        User user = mapper.readValue(requestData, User.class);
        List result = service.joinChat(user);
        response.setStatus((int) result.get(0));
        response.getWriter().write(mapper.writeValueAsString(result.get(1)));
    }
}
