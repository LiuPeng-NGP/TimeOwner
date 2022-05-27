package com.example.timeowner.object;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class User {
    private String userID;
    private String userPassword;
    private String userName;
    private String userEmail;
    private Bitmap userPicture;
    private String userRecentChannel;

    public User() {
    }

    public User(String userID, String userPassword, String userName, String userEmail, Bitmap userPicture, String userRecentChannel) {
        this.userID = userID;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPicture = userPicture;
        this.userRecentChannel = userRecentChannel;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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

    public byte[] bitmapToBLOB() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userPicture.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

}
