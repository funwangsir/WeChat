package com.wechat.entity;

public class Message {
    private int id;//主键
    private String sendUserId;//发送消息的微信号
    private String receiveUserId;//收到消息的微信号
    private String textMessage;//发送的文本消息
    private String imageMessage;//图片消息
    private String createTime;//消息发送时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }

    public String getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(String receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getImageMessage() {
        return imageMessage;
    }

    public void setImageMessage(String imageMessage) {
        this.imageMessage = imageMessage;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Message{");
        sb.append("id=").append(id);
        sb.append(", sendUserId='").append(sendUserId).append('\'');
        sb.append(", receiveUserId='").append(receiveUserId).append('\'');
        sb.append(", textMessage='").append(textMessage).append('\'');
        sb.append(", imageMessage='").append(imageMessage).append('\'');
        sb.append(", createTime='").append(createTime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
