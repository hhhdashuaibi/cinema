package com.example.cinema.bl.promotion;

import com.example.cinema.vo.VIPCardForm;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.VIPRefundForm;


/**
 * Created by liying on 2019/4/14.
 */

public interface VIPService {

    ResponseVO addVIPCard(VIPCardForm vipCardForm);

    ResponseVO getCardById(int id);

    ResponseVO getVIPInfo();

    ResponseVO charge(VIPCardForm vipCardForm);

    ResponseVO getCardByUserId(int userId);

    ResponseVO updateVIPCard(String kind,double targetAmount,double discountAmount,String newkind);

    ResponseVO updateVIPCardByUserId(String kind,double targetAmount,double discountAmount,int userId);

    ResponseVO refundCard(VIPRefundForm vipRefundForm);

}
