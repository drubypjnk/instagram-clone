package com.example.prm392_project.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ActivityContainer {
    private String title;
    @SerializedName("notificationItems")
    private List<ActivityItem> activityItemList;

    public ActivityContainer() {
    }

    public ActivityContainer(String title, List<ActivityItem> activityItemList) {
        this.title = title;
        this.activityItemList = activityItemList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ActivityItem> getActivityItemList() {
        return activityItemList;
    }

    public void setActivityItemList(List<ActivityItem> activityItemList) {
        this.activityItemList = activityItemList;
    }
}
