package com.example.bassametproject;
import com.example.bassametproject.User;
public class userMsg extends Message {

    public userMsg() {
    }

    public userMsg(String message, User sender, String createdAt) {
        super(message,sender,createdAt);
    }

    @Override
    public int getType() {
        return Message.USER_MESSAGE;
    }

}