package com.example.prm392_project.DTO;

public class CommentDTO {
    private Integer commentId;
    private Integer postId;
    private String content;
    private String author;
    private String createDate;

    private String avatar;

    public CommentDTO() {
    }

    public CommentDTO(Integer commentId, Integer postId, String content, String author, String createDate) {
        this.commentId = commentId;
        this.postId = postId;
        this.content = content;
        this.author = author;
        this.createDate = createDate;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
