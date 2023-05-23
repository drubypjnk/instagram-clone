package com.example.prm392_project.DTO;

import com.example.prm392_project.Model.Message;

public class MessageDTO {
    private Message message;
    private String author;

    public MessageDTO(Message message, String author) {
        this.message = message;
        this.author = author;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
