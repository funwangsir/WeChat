package com.wechat.adapter;

import com.wechat.entity.Moments;
import com.wechat.entity.User;

public class MomentList {
    private User user;
    private Moments moments;

    public MomentList(User user, Moments moments) {
        this.user = user;
        this.moments = moments;
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
