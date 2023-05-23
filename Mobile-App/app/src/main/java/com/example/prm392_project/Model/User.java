package com.example.prm392_project.Model;

public class User {
    private int id;
    private String userName;
    private String name;
    private Boolean gender;
    private String desc;
    private String email;
    private String address;

    public User() {
    }

    public User(int id, String userName, String name, Boolean gender, String desc, String email, String address) {
        this.id = id;
        this.userName = userName;
        this.name = name;
        this.gender = gender;
        this.desc = desc;
        this.email = email;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
