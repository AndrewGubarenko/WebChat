package com.AndriiHubarenko.WebChat.services;

import com.AndriiHubarenko.WebChat.domain.Message;
import com.AndriiHubarenko.WebChat.repositories.MessageRepository;

import java.util.List;

/**
 * @author Andrii Hubarenko
 */
public class MessageService {

    private MessageRepository messageRepository;

    public MessageService() {
        this.messageRepository = new MessageRepository();
    }

    /**
     * Method saves the message to DB
     * @param message
     * @return created Message
     */
    public Message createMessage(Message message) {
        if(messageRepository.create(message)) {
            return messageRepository.get(message.getMessage());
        }
        return new Message();
    }

    /**
     * Method retrieves all the messages from DB
     * @return
     */
    public List<Message> getAllMessages () {
        return messageRepository.getList();
    }
}
