package com.example.prm392_project.Model;

public class ActivityItem {
    private String imageSource;

    private String content;

    private boolean isFollowNotification;


    private String target;
    private String userId;
    public ActivityItem() {
    }

    public ActivityItem(String imageSource, String content, boolean isFollowNotification) {
        this.imageSource = imageSource;
        this.content = content;
        this.isFollowNotification = isFollowNotification;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isFollowNotification() {
        return isFollowNotification;
    }

    public void setFollowNotification(boolean followNotification) {
        isFollowNotification = followNotification;
    }

    public String getPostId() {
        return target;
    }

    public void setPostId(String postId) {
        this.target = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
