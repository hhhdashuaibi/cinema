package com.example.cinema.vo;

import java.util.List;

public class PurchaseItemVO {
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
     * 0:银行卡；1:会员卡
     */
    private int payMethod;
    /**
     * 消费状态
     * 0:有效；1:失效
     */
    private int purchaseState;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(int payMethod) {
        this.payMethod = payMethod;
    }

    public int getPurchaseState() {
        return purchaseState;
    }

    public void setPurchaseState(int purchaseState) {
        this.purchaseState = purchaseState;
    }

    public PurchaseItemVO(){

    }
}
