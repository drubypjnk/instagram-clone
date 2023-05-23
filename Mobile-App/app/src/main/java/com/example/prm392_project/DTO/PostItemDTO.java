package com.example.prm392_project.DTO;

import java.io.Serializable;

public class PostItemDTO implements Serializable {
    public int PostId ;
    public String Title ;
    public String Content ;
    public String Image ;
    public String UserId ;
    public boolean isDelete ;

    public PostItemDTO(int postId, String title, String content, String image, String userId, boolean isDelete) {
        PostId = postId;
        Title = title;
        Content = content;
        Image = image;
        UserId = userId;
        this.isDelete = isDelete;
    }

    public int getPostId() {
        return PostId;
    }

    public void setPostId(int postId) {
        PostId = postId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }
}
