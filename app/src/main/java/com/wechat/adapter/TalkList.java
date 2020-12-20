package com.wechat.adapter;

import com.wechat.entity.Message;
import com.wechat.entity.User;

public class TalkList implements Comparable<TalkList>{
    private User user;//通过Message过滤掉发送消息者的id后，通过id查询出接收消息者的所有信息，便于传递给聊天界面
    private Message newMessage;//最新的消息

    public TalkList( User user, Message newMessage) {
        this.user = user;
        this.newMessage = newMessage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Message getNewMessage() {
        return newMessage;
    }

    public void setNewMessage(Message newMessage) {
        this.newMessage = newMessage;
    }

    @Override
    public int compareTo(TalkList talkList) {
        //按照时间降序
        long time = Long.parseLong(talkList.getNewMessage().getCreateTime()) - Long.parseLong(this.getNewMessage().getCreateTime());
        return (int)time;
    }
}
