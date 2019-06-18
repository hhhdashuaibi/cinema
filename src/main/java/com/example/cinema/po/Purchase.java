package com.example.cinema.po;

import com.example.cinema.po.Ticket;
import com.example.cinema.vo.PurchaseVO;

import java.sql.Timestamp;
import java.util.List;

public class Purchase {
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
     * 0:银行卡；1:会员卡
     */
    private int payMethod;
    /**
     * 订单状态：
     * 0：有效 1：失效
     */
    private int purchaseState;
    /**
     * 支付时间
     */
    private Timestamp purchaseTime;

    public Purchase() {
    }

    public Timestamp getTime() {
        return purchaseTime;
    }

    public void setTime(Timestamp time) {
        this.purchaseTime = time;
    }

    public double getAmount() {
        return amount;
    }

    public int getPayMethod() {
        return payMethod;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPayMethod(int payMethod) {
        this.payMethod = payMethod;
    }


    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public int getPurchaseState() {
        return purchaseState;
    }

    public void setPurchaseState(int purchaseState) {
        this.purchaseState = purchaseState;
    }

    public PurchaseVO getVO() {
        PurchaseVO vo = new PurchaseVO();
        vo.setId(this.getId());
        vo.setUserId(this.getUserId());
        vo.setAmount(this.getAmount());
        String payMethodString;
        switch (payMethod) {
            case 0:
                payMethodString = "银行卡支付";
                break;
            case 1:
                payMethodString = "会员卡支付";
                break;
            default:
                payMethodString = "银行卡支付";
                break;
        }
        String purchaseStateString;
        switch (purchaseState) {
            case 0:
                purchaseStateString = "有效";
                break;
            case 1:
                purchaseStateString = "失效";
                break;
            default:
                purchaseStateString = "有效";
                break;
        }
        vo.setPurchaseState(purchaseStateString);
        vo.setPayMethod(payMethodString);
        vo.setTime(this.purchaseTime);
        return vo;
    }

}
