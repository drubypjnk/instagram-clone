package com.example.prm392_project.Model;

import java.util.Date;
import java.util.List;

public class Room {
    private int roomId;
    private String roomTitle;
    private Date createDate;
    private Date deleteDate;
    private boolean deleteFlag;
    private List<String> roomMembers;

    public Room(int roomId, String roomTitle, Date createDate, Date deleteDate, boolean deleteFlag, List<String> roomMembers) {
        this.roomId = roomId;
        this.roomTitle = roomTitle;
        this.createDate = createDate;
        this.deleteDate = deleteDate;
        this.deleteFlag = deleteFlag;
        this.roomMembers = roomMembers;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomTitle() {
        return roomTitle;
    }

    public void setRoomTitle(String roomTitle) {
        this.roomTitle = roomTitle;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public List<String> getRoomMembers() {
        return roomMembers;
    }

    public void setRoomMembers(List<String> roomMembers) {
        this.roomMembers = roomMembers;
    }
}
