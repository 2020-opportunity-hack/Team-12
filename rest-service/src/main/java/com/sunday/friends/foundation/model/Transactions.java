package com.sunday.friends.foundation.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Transactions")
public class Transactions {
    private Integer transactionid;
    private Integer userId;
    private Integer type;
    private Float amount;
    private Float balanceAfterAction;
    @Column(name = "time", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    public Transactions(Integer userId, Integer type, Float amount, Float balanceAfterAction, Date time) {
        this.transactionid = transactionid;
        this.userId = userId;
        this.type = type;
        this.amount = amount;
        this.balanceAfterAction = balanceAfterAction;
        this.time = time;
    }

    public Transactions() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(Integer transactionid) {
        this.transactionid = transactionid;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Float getBalanceAfterAction() {
        return balanceAfterAction;
    }

    public void setBalanceAfterAction(Float balanceAfterAction) {
        this.balanceAfterAction = balanceAfterAction;
    }

    @Column(name = "time", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
