package com.example.cinema.vo;

import java.sql.Timestamp;

/**
 * Created by liying on 2019/4/16.
 */
public class CouponForm {
    /**
     * 优惠券描述
     */
    private String description;
    /**
     * 优惠券名称
     */
    private String name;
    /**
     * 优惠券使用门槛
     */
    private double targetAmount;
    /**
     * 优惠券优惠金额
     */
    private double discountAmount;
    /**
<<<<<<< HEAD
=======
     * 用户最低消费
     */
    private double leastPurchase;
    /**
>>>>>>> db54bb6a78d5c3bfa52225d79fdf26c333d01596
     * 可用时间
     */
    private Timestamp startTime;
    /**
     * 失效时间
     */
    private Timestamp endTime;

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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


    public double getLeastPurchase() {
        return leastPurchase;
    }

    public void setLeastPurchase(double leastPurchase) {
        this.leastPurchase = leastPurchase;
    }


    public CouponForm() {
    }
}
