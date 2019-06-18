package com.example.cinema.po;

import com.example.cinema.vo.RechargeVO;

import java.sql.Timestamp;

public class Recharge {
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
     * 充值后会员卡余额
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

    public Timestamp getRechargeTime() {
        return rechargeTime;
    }

    public void setRechargeTime(Timestamp rechargeTime) {
        this.rechargeTime = rechargeTime;
    }

    public double getAfterCardBalance() {
        return afterCardBalance;
    }

    public void setAfterCardBalance(double afterCardBalance) {
        this.afterCardBalance = afterCardBalance;
    }

    public Recharge(){}

    public RechargeVO getVO() {
        RechargeVO vo = new RechargeVO();
        vo.setId(this.getId());
        vo.setUserId(this.getUserId());
        vo.setPayAmount(this.getPayAmount());
        vo.setActualAmount(this.getActualAmount());
        vo.setAfterCardBalance(this.getAfterCardBalance());
        vo.setRechargeTime(this.getRechargeTime());
        return vo;
    }
}
