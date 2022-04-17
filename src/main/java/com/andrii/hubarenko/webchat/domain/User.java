package com.andrii.hubarenko.webchat.domain;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonPropertyOrder({ "nickName", "id" })
public class User {

    private long id;
    private String nickName;
    private List<Message> messageList = new ArrayList<>();

    public User(){}
    public User(String nickName) {
        this.nickName = nickName;
    }
    public User(long id, String nickName) {
        this.id = id;
        this.nickName = nickName;
    }
    @JsonGetter("id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @JsonGetter("nickName")
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @JsonGetter("messageList")
    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id && nickName.equals(user.nickName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickName);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
