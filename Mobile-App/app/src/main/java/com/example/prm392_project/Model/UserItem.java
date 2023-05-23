package com.example.prm392_project.Model;

public class UserItem {
    private String imageSource;

    private String name;

    public UserItem() {
    }

    public UserItem(String imageSource, String name) {
        this.imageSource = imageSource;
        this.name = name;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
