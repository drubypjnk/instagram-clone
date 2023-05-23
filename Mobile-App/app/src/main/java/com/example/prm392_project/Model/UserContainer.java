package com.example.prm392_project.Model;

import java.util.List;

public class UserContainer {
    List<UserItem> userItems ;

    public UserContainer() {
    }

    public UserContainer(List<UserItem> userItems) {
        this.userItems = userItems;
    }

    public List<UserItem> getUserItems() {
        return userItems;
    }

    public void setUserItems(List<UserItem> userItems) {
        this.userItems = userItems;
    }
}
