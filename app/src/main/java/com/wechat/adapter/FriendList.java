package com.wechat.adapter;
import com.wechat.entity.User;
public class FriendList {
    private int imageId;
    private User user;

    public FriendList(int imageId, User user) {
        this.imageId = imageId;
        this.user = user;
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
}
