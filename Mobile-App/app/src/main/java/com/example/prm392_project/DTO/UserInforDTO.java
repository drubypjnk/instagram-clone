package com.example.prm392_project.DTO;

import java.io.Serializable;
import java.util.List;

public class UserInforDTO implements Serializable {
    public String userId ;
    public String username ;
    public String fullName ;
    public String desription ;
    public int age ;
    public int gender ;
    public String location ;
    public String email ;
    public boolean isPrivate ;
    public PhotoInforDTO  avartarImage ;
    public List<PostInforDTO> listPost;
    public List<FollowerInforDTO>listFollower;
    public List<FollowerInforDTO>listFollowing;
    public UserInforDTO() {
    }

    public UserInforDTO(String userId, String username, String fullName, String desription, int age, int gender, String location, String email, boolean isPrivate, PhotoInforDTO avartarImage, List<PostInforDTO> listPost, List<FollowerInforDTO> listFollower, List<FollowerInforDTO> listFollowing) {
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
        this.desription = desription;
        this.age = age;
        this.gender = gender;
        this.location = location;
        this.email = email;
        this.isPrivate = isPrivate;
        this.avartarImage = avartarImage;
        this.listPost = listPost;
        this.listFollower = listFollower;
        this.listFollowing = listFollowing;
    }

    public List<FollowerInforDTO> getListFollower() {
        return listFollower;
    }

    public void setListFollower(List<FollowerInforDTO> listFollower) {
        this.listFollower = listFollower;
    }

    public List<FollowerInforDTO> getListFollowing() {
        return listFollowing;
    }

    public void setListFollowing(List<FollowerInforDTO> listFollowing) {
        this.listFollowing = listFollowing;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDesription() {
        return desription;
    }

    public void setDesription(String desription) {
        this.desription = desription;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public PhotoInforDTO getAvartarImage() {
        return avartarImage;
    }

    public void setAvartarImage(PhotoInforDTO avartarImage) {
        this.avartarImage = avartarImage;
    }

    public List<PostInforDTO> getListPost() {
        return listPost;
    }

    public void setListPost(List<PostInforDTO> listPost) {
        this.listPost = listPost;
    }
}
