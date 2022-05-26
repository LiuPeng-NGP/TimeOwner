package com.example.timeowner.object;

import android.graphics.Bitmap;

public class User {
    private String userID;
    private String userPassword;
    private String userName;
    private Bitmap userPicture;
    private String userRecentChannel;

    public User(String userID, String userPassword, String userName, Bitmap userPicture, String userRecentChannel) {
        this.userID = userID;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userPicture = userPicture;
        this.userRecentChannel = userRecentChannel;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Bitmap getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(Bitmap userPicture) {
        this.userPicture = userPicture;
    }

    public String getUserRecentChannel() {
        return userRecentChannel;
    }

    public void setUserRecentChannel(String userRecentChannel) {
        this.userRecentChannel = userRecentChannel;
    }
}
