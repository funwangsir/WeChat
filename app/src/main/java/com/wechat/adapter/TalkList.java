package com.wechat.adapter;

import com.wechat.entity.Message;
import com.wechat.entity.User;

public class TalkList {
    private int imageId;
    private User user;//通过Message过滤掉发送消息者的id后，通过id查询出接收消息者的所有信息，便于传递给聊天界面
    private String newMessage;//最新的消息

    public TalkList(int imageId, User user, String newMessage) {
        this.imageId = imageId;
        this.user = user;
        this.newMessage = newMessage;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNewMessage() {
        return newMessage;
    }

    public void setNewMessage(String newMessage) {
        this.newMessage = newMessage;
    }
}
