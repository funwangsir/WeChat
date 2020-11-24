package com.wechat.entity;

import java.io.Serializable;

public class User implements Serializable {
    private String userId;//微信号
    private String password;//密码
    private String name;//真实姓名
    private String nickname;//昵称
    private String avatar;//头像二进制文件
    private String gender;//性别
    private String area;//地区 中国大陆 + 城市
    private String introduction;//个人简介
    private String userPhone;//手机号
    private String loginStatus;//登录状态

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("userId='").append(userId).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", nickname='").append(nickname).append('\'');
        sb.append(", avatar='").append(avatar).append('\'');
        sb.append(", gender='").append(gender).append('\'');
        sb.append(", area='").append(area).append('\'');
        sb.append(", introduction='").append(introduction).append('\'');
        sb.append(", userPhone='").append(userPhone).append('\'');
        sb.append(", loginStatus='").append(loginStatus).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
