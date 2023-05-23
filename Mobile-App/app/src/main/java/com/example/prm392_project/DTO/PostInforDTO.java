package com.example.prm392_project.DTO;

import java.io.Serializable;

public class PostInforDTO implements Serializable {
    public int postId;
    public String title;
    public String content;
    public PhotoInforDTO postImage;

    public PostInforDTO() {
    }

    public PostInforDTO(int postId, String title, String content, PhotoInforDTO postImage) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.postImage = postImage;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PhotoInforDTO getPostImage() {
        return postImage;
    }

    public void setPostImage(PhotoInforDTO postImage) {
        this.postImage = postImage;
    }
}
