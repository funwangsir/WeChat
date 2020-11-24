package com.wechat.entity;

public class Friends {
    private int id;//主键
    private String userId;//微信号
    private String userFriendId;//朋友微信号

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

    public String getUserFriendId() {
        return userFriendId;
    }

    public void setUserFriendId(String userFriendId) {
        this.userFriendId = userFriendId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Friends{");
        sb.append("id=").append(id);
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", userFriendId='").append(userFriendId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
