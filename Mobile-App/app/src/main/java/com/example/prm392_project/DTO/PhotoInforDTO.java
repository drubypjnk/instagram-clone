package com.example.prm392_project.DTO;

import java.io.Serializable;

public class PhotoInforDTO implements Serializable {
    private int photoId;
    private String content;
    private String url;


    public PhotoInforDTO(int photoId, String content, String url) {
        this.photoId = photoId;
        this.content = content;
        this.url = url;
    }

    public PhotoInforDTO() {
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
