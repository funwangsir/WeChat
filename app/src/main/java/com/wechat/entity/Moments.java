package com.wechat.entity;

import android.content.Intent;

//朋友圈对象
//实现Comparable用于对朋友圈集合通过时间戳比较排序
public class Moments implements Comparable<Moments>{
    private int id;//主键
    private String userId;//发朋友圈的微信号
    private String  textContent;//文本内容
    private String imageContent;//图片内容
    private String publishTime;//发布时间
    private int likeNum;//点赞数
    private String likeUserId;//点赞人的微信号，多个微信号以&分割

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getImageContent() {
        return imageContent;
    }

    public void setImageContent(String imageContent) {
        this.imageContent = imageContent;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public String getLikeUserId() {
        return likeUserId;
    }

    public void setLikeUserId(String likeUserId) {
        this.likeUserId = likeUserId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SendMoments{");
        sb.append("id=").append(id);
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", textContent='").append(textContent).append('\'');
        sb.append(", imageContent='").append(imageContent).append('\'');
        sb.append(", publishTime='").append(publishTime).append('\'');
        sb.append(", likeNum=").append(likeNum);
        sb.append(", likeUserId='").append(likeUserId).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int compareTo(Moments moments) {
        //按照时间降序
        long time = Long.parseLong(moments.getPublishTime()) - Long.parseLong(this.publishTime);
        return (int)time;
    }
}
