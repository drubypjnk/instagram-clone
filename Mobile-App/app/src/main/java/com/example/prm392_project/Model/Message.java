package com.example.prm392_project.Model;

import java.util.Date;

public class Message {
    private int message_id;
    private Date createdDate;
    private String content;
    private String author;
    private int react_id;
    private boolean delete_flag;

    public Message(int message_id, Date createdDate, String content, String author, int react_id, boolean delete_flag) {
        this.message_id = message_id;
        this.createdDate = createdDate;
        this.content = content;
        this.author = author;
        this.react_id = react_id;
        this.delete_flag = delete_flag;
    }

    public int getMessage_id() {
        return message_id;
    }

    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

    public int getReact_id() {
        return react_id;
    }

    public void setReact_id(int react_id) {
        this.react_id = react_id;
    }

    public boolean isDelete_flag() {
        return delete_flag;
    }

    public void setDelete_flag(boolean delete_flag) {
        this.delete_flag = delete_flag;
    }
}
