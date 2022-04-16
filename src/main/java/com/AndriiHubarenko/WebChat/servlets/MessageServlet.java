package com.AndriiHubarenko.WebChat.servlets;

import com.AndriiHubarenko.WebChat.domain.Message;
import com.AndriiHubarenko.WebChat.services.MessageService;
import com.AndriiHubarenko.WebChat.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "MessageServlet", value = "/user/message")
public class MessageServlet extends HttpServlet {

    private MessageService service;

    @Override
    public void init() {
        this.service = new MessageService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Message> result = service.getAllMessages();
        response.setStatus(200);
        response.getWriter().write(mapper.writeValueAsString(result));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        String requestData = request.getReader().lines().collect(Collectors.joining());
        Message message = mapper.readValue(requestData, Message.class);
        Message result = service.createMessage(message);
        response.setStatus(200);
        response.getWriter().write(mapper.writeValueAsString(result));
    }
}
