package com.example.prm392_project.Entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "searchKeys")
public class SearchKey {
    @PrimaryKey
    @NonNull
    private String searchKey;
    private Date createdDate;

    public SearchKey() {
    }

    public SearchKey(String searchKey, Date createdDate) {
        this.searchKey = searchKey;
        this.createdDate = createdDate;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
