package com.example.prm392_project.Model;

public class Notification {
    private int NotifyType;
    private String NotifyTitle;
    private int NotifyId;
    private String NotifyContent;
    private boolean DeletedFlag;
    private String CreateDate;
    private int Status;
    private String UserId;
    private User User;

    public int getNotifyType() {
        return NotifyType;
    }

    public void setNotifyType(int notifyType) {
        NotifyType = notifyType;
    }

    public String getNotifyTitle() {
        return NotifyTitle;
    }

    public void setNotifyTitle(String notifyTitle) {
        NotifyTitle = notifyTitle;
    }

    public int getNotifyId() {
        return NotifyId;
    }

    public void setNotifyId(int notifyId) {
        NotifyId = notifyId;
    }

    public String getNotifyContent() {
        return NotifyContent;
    }

    public void setNotifyContent(String notifyContent) {
        NotifyContent = notifyContent;
    }

    public boolean isDeletedFlag() {
        return DeletedFlag;
    }

    public void setDeletedFlag(boolean deletedFlag) {
        DeletedFlag = deletedFlag;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public com.example.prm392_project.Model.User getUser() {
        return User;
    }

    public void setUser(com.example.prm392_project.Model.User user) {
        User = user;
    }
}
