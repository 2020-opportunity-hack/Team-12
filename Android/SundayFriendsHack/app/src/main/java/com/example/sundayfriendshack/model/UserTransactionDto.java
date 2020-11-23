package com.example.sundayfriendshack.model;

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

    public void setTransactions(ArrayList<UserTransaction> transactions) {
        this.transactions = transactions;
    }

    public class UserTransaction{
        private boolean type;
        private int amount;
        private int balanceAfterAction;
        private String time;

        public boolean isType() {
            return type;
        }

        public void setType(boolean type) {
            this.type = type;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getBalanceAfterAction() {
            return balanceAfterAction;
        }

        public void setBalanceAfterAction(int balanceAfterAction) {
            this.balanceAfterAction = balanceAfterAction;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
