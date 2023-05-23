package com.example.prm392_project.Model;


public class FriendItem {
    private int id;
    private int img;
    private String username;
    private String name;

    public FriendItem() {
    }

    public FriendItem(int id, int img, String username, String name) {
        this.id = id;
        this.img = img;
        this.username = username;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
