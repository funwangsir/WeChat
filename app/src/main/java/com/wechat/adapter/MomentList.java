package com.wechat.adapter;

import com.wechat.entity.Moments;
import com.wechat.entity.User;

public class MomentList {
    private int imgId;
    private User user;
    private Moments moments;

    public MomentList(int imgId, User user, Moments moments) {
        this.imgId = imgId;
        this.user = user;
        this.moments = moments;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Moments getMoments() {
        return moments;
    }

    public void setMoments(Moments moments) {
        this.moments = moments;
    }
}
