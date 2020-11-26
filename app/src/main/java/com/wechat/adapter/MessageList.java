package com.wechat.adapter;

import com.wechat.entity.Message;

public class MessageList {
    private int imgId;
    private Message message;

    public MessageList(int imgId, Message message) {
        this.imgId = imgId;
        this.message = message;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
