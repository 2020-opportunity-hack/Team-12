package com.sunday.friends.foundation.model;

import javax.persistence.*;

@Entity
@Table(name="Users")
public class Users {
    private Integer userId;
    private String name;
    private String email;
    private Integer familyId;
    private boolean isAdmin;
    private String imageUrl;
    private Integer balance;

    public Users() {
    }

    public Users(String name, String email, Integer familyId, String imageUrl) {
        this.name = name;
        this.email = email;
        this.familyId = familyId;
        this.imageUrl = imageUrl;
        this.isAdmin = false;
        this.balance = 0;
    }

    public Users(Integer userId, String name, String email, Integer familyId, boolean isAdmin, String imageUrl, Integer balance) {
        this.userId = userId;
        this.email = email;
        this.familyId = familyId;
        this.name = name;
        this.isAdmin = isAdmin;
        this.imageUrl = imageUrl;
        this.balance = balance;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Integer familyId) {
        this.familyId = familyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "isAdmin")
    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }
}
