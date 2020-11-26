package com.wechat.adapter;

import com.wechat.entity.Message;

public class MessageList {
    private int imageId;
    private Message message;

    public MessageList(int imageId, Message message) {
        this.imageId = imageId;
        this.message = message;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
