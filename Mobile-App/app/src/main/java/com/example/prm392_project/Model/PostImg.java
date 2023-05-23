package com.example.prm392_project.Model;

public class PostImg {
    int id;
    int image;
    public PostImg() {
    }

    public PostImg(int id, int image) {
        this.id = id;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
