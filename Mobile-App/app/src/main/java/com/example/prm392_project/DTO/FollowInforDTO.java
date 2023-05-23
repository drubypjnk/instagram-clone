package com.example.prm392_project.DTO;

import java.io.Serializable;

public class FollowInforDTO implements Serializable {
    String followId;
    String userId;
    String userName;
    String fullName;
    String avatar;
    boolean flag;
    public FollowInforDTO() {
    }


    public FollowInforDTO(String followId, String userId, String userName, String fullName, String avatar, boolean flag) {
        this.followId = followId;
        this.userId = userId;
        this.userName = userName;
        this.fullName = fullName;
        this.avatar = avatar;
        this.flag = flag;
    }

    public String getFollowId() {
        return followId;
    }

    public void setFollowId(String followId) {
        this.followId = followId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
