package com.example.cinema.po;



import com.example.cinema.bl.promotion.VIPKindService;
import com.example.cinema.blImpl.promotion.VIPKindServiceImpl;
import com.example.cinema.data.promotion.VIPKindMapper;
import com.example.cinema.po.VIPKind;
import com.example.cinema.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;


import java.sql.Timestamp;

/**
 * Created by liying on 2019/4/14.
 */

@Component
public class VIPCard {
    @Autowired
    VIPKindMapper vipKindMapper;
    @Autowired
    VIPKindService vipKindService;

    public static VIPCard vipCard;
//    public static final double price = 25;
//
//    public static final String description="满200送30";

    public double price;

    public String description;

    public double targetAmount;

    public double discountAmount;

    public String kind;

    public int duration;


    /**
     * 用户id
     */
    private int userId;

    /**
     * 会员卡id
     */
    private int id;

    /**
     * 会员卡余额
     */
    private double balance;

    /**
     * 办卡日期
     */
    private Timestamp joinDate;

    /*
     * 折扣
     * */
    public double discountPercent;


    /*@PostConstruct
    public void init() {
        try {
            vipCard=this;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public VIPCard(){
        /*try {

            VIPKind vipKind = vipCard.vipKindMapper.selectVIPKindByName(kind);
            System.out.println(vipKind);
            this.targetAmount = vipKind.targetAmount;
            this.discountAmount = vipKind.discountAmount;
            this.price = vipKind.price;
            this.description = "满" + this.discountAmount + "送" + this.discountAmount;
            this.duration = vipKind.duration;
        }catch (Exception e) {
            e.printStackTrace();
        }*/

    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Timestamp getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Timestamp joinDate) {
        this.joinDate = joinDate;
    }





    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }


    public double calculate(double amount) {
        return (int)(amount/targetAmount)*discountAmount+amount;

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

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }

}
