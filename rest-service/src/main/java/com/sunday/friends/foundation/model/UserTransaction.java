package com.sunday.friends.foundation.model;

import java.util.List;
/**
 * User Transaction Derived Model
 * @author  Mahapatra Manas
 * @version 1.0
 * @since   11-20-2020
 */

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
