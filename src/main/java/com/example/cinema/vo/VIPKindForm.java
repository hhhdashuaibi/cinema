package com.example.cinema.vo;

import java.sql.Timestamp;

public class VIPKindForm {
    /*
     * 会员卡名称
     */
    public String name;
    /**
     * 会员卡种类id
     */
    private int id;
    /**
     * 开卡日期
     */
    private Timestamp issueTime;
    /*
     * 目标金额
     */
    public double targetAmount;
    /*
     * 满减金额
     */
    public double discountAmount;
    /*
     * 持续时间
     * */
    public int duration;
    /*
    * 会员卡价格
    * */
    public double price;
    /*
     * 折扣
     * */
    public double discountPercent;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(Timestamp issueTime) {
        this.issueTime= issueTime;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }
}
