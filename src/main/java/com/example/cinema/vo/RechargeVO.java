package com.example.cinema.vo;

import java.sql.Timestamp;

public class RechargeVO {
    /**
     * 充值记录id
     */
    private int id;
    /**
     * 用户id
     */
    private int userId;
    /**
     * 支付金额
     */
    private double payAmount;
    /**
     * 到账金额
     */
    private double actualAmount;
    /**
     * 充值后卡余额
     */
    private double afterCardBalance;
    /**
     * 充值时间
     */
    private Timestamp rechargeTime;

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

    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }

    public double getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(double actualAmount) {
        this.actualAmount = actualAmount;
    }

    public double getAfterCardBalance() {
        return afterCardBalance;
    }

    public void setAfterCardBalance(double afterCardBalance) {
        this.afterCardBalance = afterCardBalance;
    }

    public Timestamp getRechargeTime() {
        return rechargeTime;
    }

    public void setRechargeTime(Timestamp rechargeTime) {
        this.rechargeTime = rechargeTime;
    }

    public RechargeVO() {
    }

}
