package com.andrii.hubarenko.webchat.domain;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.Objects;

public class Message {
    private long id;
    private String message;
    private String author;
    private long user_id;

    public Message() {
    }

    public Message(String message, String author, long user_id) {
        this.message = message;
        this.author = author;
        this.user_id = user_id;
    }

    public Message(long id, String message, String author, long user_id) {
        this.id = id;
        this.message = message;
        this.author = author;
        this.user_id = user_id;
    }

    @JsonGetter("id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @JsonGetter("message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @JsonGetter("author")
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @JsonGetter("user_id")
    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message1 = (Message) o;
        return id == message1.id && message.equals(message1.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
