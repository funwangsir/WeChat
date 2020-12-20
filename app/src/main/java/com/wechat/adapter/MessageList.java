package com.wechat.adapter;

import com.wechat.entity.Message;
import com.wechat.entity.User;

public class MessageList {
    private User sendUser;
    private User receiveUser;
    private Message message;

    public MessageList(User sendUser,User receiveUser, Message message) {
        this.sendUser = sendUser;
        this.receiveUser = receiveUser;
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public User getSendUser() {
        return sendUser;
    }

    public void setSendUser(User sendUser) {
        this.sendUser = sendUser;
    }

    public User getReceiveUser() {
        return receiveUser;
    }

    public void setReceiveUser(User receiveUser) {
        this.receiveUser = receiveUser;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
