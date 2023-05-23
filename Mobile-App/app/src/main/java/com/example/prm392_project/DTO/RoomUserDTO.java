package com.example.prm392_project.DTO;

public class RoomUserDTO {
    private String UserId;
    private String Username;
    private String FullName;
    private PhotoInforDTO avartarImage;

    public RoomUserDTO(String userId, String username, String fullName, PhotoInforDTO avartarImage) {
        UserId = userId;
        Username = username;
        FullName = fullName;
        this.avartarImage = avartarImage;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public PhotoInforDTO getAvartarImage() {
        return avartarImage;
    }

    public void setAvartarImage(PhotoInforDTO avartarImage) {
        this.avartarImage = avartarImage;
    }
}
