package com.example.cinema.bl.promotion;

import com.example.cinema.vo.VIPCardForm;
import com.example.cinema.vo.ResponseVO;



/**
 * Created by liying on 2019/4/14.
 */

public interface VIPService {


    ResponseVO addVIPCard(VIPCardForm vipCardForm);

    ResponseVO getCardById(int id);

    ResponseVO getVIPInfo();

    ResponseVO charge(VIPCardForm vipCardForm);

    ResponseVO getCardByUserId(int userId);


    ResponseVO updateVIPCard(String kind,double targetAmount,double discountAmount,double discountPercent);



}
