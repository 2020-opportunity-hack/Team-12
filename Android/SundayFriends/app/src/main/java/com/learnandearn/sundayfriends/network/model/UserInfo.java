package com.learnandearn.sundayfriends.network.model;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private int userId;
    private String name;
    private String email;
    private Integer familyId;
    private boolean isAdmin;
    private String imageUrl;
    private String address;
    private float balance;
    private boolean isActive;

    //Progressbar item
    private boolean progressBar;

    private UserInfo() {
    }

    //Only used once for getting user transactions on user dashboard btn click
    public UserInfo(int userId, String name){
        this.userId = userId;
        this.name = name;
    }

    public static UserInfo addProgressBar(){
        return new UserInfo(true);
    }

    public UserInfo(boolean activateProgressBar){
        this.progressBar = activateProgressBar;
    }

    public boolean isProgressBar() {
        return progressBar;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Integer getFamilyId() {
        return familyId;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getAddress() {
        return address;
    }

    public float getBalance() {
        return balance;
    }

    public boolean isActive() {
        return isActive;
    }
}
