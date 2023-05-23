package com.example.prm392_project.DTO;

import java.io.Serializable;

public class UserLoginDTO implements Serializable {
    public String user_id;
    public String username;
    public String password;
    public int status;

    public UserLoginDTO() {
    }

    public UserLoginDTO(String user_id, String username, String password, int status) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.status = status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
