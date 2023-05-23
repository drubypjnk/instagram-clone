package com.example.prm392_project.DTO;

import com.example.prm392_project.Model.Message;
import com.example.prm392_project.Model.Room;
import com.example.prm392_project.Model.User;

import java.util.Date;
import java.util.List;

public class RoomDTO {
    private Room room;
    private RoomUserDTO receiver;
    private Message lastMassage;

    public RoomDTO(Room room, RoomUserDTO receiver, Message lastMassage) {
        this.room = room;
        this.receiver = receiver;
        this.lastMassage = lastMassage;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public RoomUserDTO getReceiver() {
        return receiver;
    }

    public void setReceiver(RoomUserDTO receiver) {
        this.receiver = receiver;
    }

    public Message getLastMassage() {
        return lastMassage;
    }

    public void setLastMassage(Message lastMassage) {
        this.lastMassage = lastMassage;
    }
}
