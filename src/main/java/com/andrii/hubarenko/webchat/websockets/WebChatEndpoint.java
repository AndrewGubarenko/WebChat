package com.andrii.hubarenko.webchat.websockets;

import com.andrii.hubarenko.webchat.domain.Message;
import com.andrii.hubarenko.webchat.domain.User;
import com.andrii.hubarenko.webchat.services.MessageService;
import com.andrii.hubarenko.webchat.services.UserService;
import com.andrii.hubarenko.webchat.startupactions.StartUpActions;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ServerEndpoint("/socket")
public class WebChatEndpoint {

    private static final Logger LOGGER = Logger.getLogger(StartUpActions.class);
    private UserService userService = new UserService();
    private MessageService messageService = new MessageService();

    private static final Set<WebChatEndpoint> connections = new HashSet<>();
    private Session session;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) throws IOException {
        this.session = session;
        connections.add(this);
        session.getBasicRemote().sendText("Enter your nick name: " );
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        if(message.contains("nickName")) {
            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.readValue(message, User.class);
            List result = userService.joinChat(user);
            session.getBasicRemote().sendText(mapper.writeValueAsString(result.get(1)));
        } else if(message.contains("\"message\"")) {
            ObjectMapper mapper = new ObjectMapper();
            Message mes = mapper.readValue(message, Message.class);
            Message result = messageService.createMessage(mes);

            for(WebChatEndpoint client: connections)
                client.session.getBasicRemote().sendText(mapper.writeValueAsString(result));

        } else if(message.contains("refresh")) {
            ObjectMapper mapper = new ObjectMapper();
            List<Message> result = messageService.getAllMessages();
            session.getBasicRemote().sendText(mapper.writeValueAsString(result));
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        session.getBasicRemote().sendText("Connection closed");
    }

    @OnError
    public void onError(Session session, Throwable throwable) throws IOException {
        session.getBasicRemote().sendText(throwable.getLocalizedMessage());
        LOGGER.warn(throwable.getMessage());
    }
}
