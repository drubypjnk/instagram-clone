package com.example.prm392_project.DTO;

import java.io.Serializable;
import java.util.List;

public class UserToManageDTO implements Serializable {
    public String userId ;
    public String username ;
    public String fullName ;
    public String email ;
    public boolean isActive ;
    public PhotoInforDTO  avartarImage ;

    public UserToManageDTO() {
    }

    public UserToManageDTO(String userId, String username, String fullName, String email, boolean isActive, PhotoInforDTO avartarImage) {
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.isActive = isActive;
        this.avartarImage = avartarImage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public PhotoInforDTO getAvartarImage() {
        return avartarImage;
    }

    public void setAvartarImage(PhotoInforDTO avartarImage) {
        this.avartarImage = avartarImage;
    }
}
