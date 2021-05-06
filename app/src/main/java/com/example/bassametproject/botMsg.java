package com.example.bassametproject;

public class botMsg extends Message{

    public botMsg() {
    }

    public botMsg(String message, User sender, String createdAt) {
        super(message,sender,createdAt);
    }

    @Override
    public int getType() {
        return Message.BOT_MESSAGE;
    }

}
