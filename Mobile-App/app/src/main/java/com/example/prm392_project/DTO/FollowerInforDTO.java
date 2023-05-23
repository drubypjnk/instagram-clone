package com.example.prm392_project.DTO;

import java.io.Serializable;

public class FollowerInforDTO implements Serializable {
    String userId;
    String userName;
    String fullName;

    public FollowerInforDTO(String userId, String userName, String fullName) {
        this.userId = userId;
        this.userName = userName;
        this.fullName = fullName;
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
}
