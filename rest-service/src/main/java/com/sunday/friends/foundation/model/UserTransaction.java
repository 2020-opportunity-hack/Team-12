package com.sunday.friends.foundation.model;

import java.util.List;

public class UserTransaction {
    private Integer userId;
    private List<Transactions> transactions;

    public UserTransaction(Integer userId, List<Transactions> transactions) {
        this.userId = userId;
        this.transactions = transactions;
    }

    public UserTransaction() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Transactions> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transactions> transactions) {
        this.transactions = transactions;
    }
}
