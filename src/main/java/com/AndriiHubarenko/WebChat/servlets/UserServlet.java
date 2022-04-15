package com.AndriiHubarenko.WebChat.servlets;

import com.AndriiHubarenko.WebChat.domain.User;
import com.AndriiHubarenko.WebChat.repositories.UserRepository;
import com.AndriiHubarenko.WebChat.services.ConnectionService;
import com.AndriiHubarenko.WebChat.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "UserServlet", value = "/user")
public class UserServlet extends HttpServlet {
    UserService service;

    public UserServlet() {
        service = new UserService();
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
