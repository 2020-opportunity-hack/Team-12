package com.learnandearn.sundayfriends.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OnBoard {

    @SerializedName("userId")
    @Expose
    private int userId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("familyId")
    @Expose
    private int familyId;

    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;

    @SerializedName("balance")
    @Expose
    private float balance;

    @SerializedName("admin")
    @Expose
    private boolean admin;

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getFamilyId() {
        return familyId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public float getBalance() {
        return balance;
    }

    public boolean isAdmin() {
        return admin;
    }

    @Override
    public String toString() {
        return "OnBoard{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", familyId=" + familyId +
                ", imageUrl='" + imageUrl + '\'' +
                ", balance=" + balance +
                ", admin=" + admin +
                '}';
    }
}
