package com.example.cinema.vo;

import java.util.List;

public class RechargeItemVO {
    /**
     * 用户id
     */
    private int userId;
    /**
     * 支付金额
     */
    private double payAmount;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }

    public RechargeItemVO(){}
}
