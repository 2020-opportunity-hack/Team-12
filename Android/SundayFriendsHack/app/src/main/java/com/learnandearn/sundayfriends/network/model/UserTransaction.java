package com.learnandearn.sundayfriends.network.model;

public class UserTransaction {
    private int    transactionid;
    private int    userId;
    private int    type;
    private float  amount;
    private float  balanceAfterAction;
    private String time;

    public int getTransactionid() {
        return transactionid;
    }

    public int getUserId() {
        return userId;
    }

    public int getType() {
        return type;
    }

    public float getAmount() {
        return amount;
    }

    public float getBalanceAfterAction() {
        return balanceAfterAction;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
