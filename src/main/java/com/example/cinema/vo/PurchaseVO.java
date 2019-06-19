package com.example.cinema.vo;

import com.example.cinema.po.Purchase;

import java.sql.Timestamp;
import java.util.List;


public class PurchaseVO {

    /**
     * 消费记录id
     */
    private int id;
    /**
     * 用户id
     */
    private int userId;
    /**
     * 消费金额
     */
    private double amount;
    /**
     * 支付方式
     */
    private String payMethod;
    /**
     * 订单状态
     */
    private String purchaseState;

    private Timestamp time;

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public PurchaseVO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPayMethod(){
        return payMethod;
    }

    public void setPayMethod(String payMethod){
        this.payMethod=payMethod;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPurchaseState() {
        return purchaseState;
    }

    public void setPurchaseState(String purchaseState) {
        this.purchaseState = purchaseState;
    }
}

