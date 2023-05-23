package com.example.prm392_project.DTO;

public class MessageBodyDTO {
    private String userId;
    private int roomId;
    private String messageContent;

    public MessageBodyDTO(String userId, int roomId, String messageContent) {
        this.userId = userId;
        this.roomId = roomId;
        this.messageContent = messageContent;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
}
