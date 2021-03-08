package com.learnandearn.sundayfriends.network.model;

import java.util.ArrayList;

public class UserTransactionDto {

    private int userId;
    private ArrayList<UserTransaction> transactions;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ArrayList<UserTransaction> getTransactions() {
        return transactions;
    }

}
