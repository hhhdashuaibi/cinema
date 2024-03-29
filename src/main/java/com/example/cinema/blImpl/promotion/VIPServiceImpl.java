package com.example.cinema.blImpl.promotion;

import com.example.cinema.bl.promotion.VIPService;
import com.example.cinema.data.promotion.VIPCardMapper;
import com.example.cinema.data.management.ScheduleMapper;
import com.example.cinema.data.promotion.VIPCardMapper;
import com.example.cinema.data.sales.TicketMapper;
import com.example.cinema.po.Ticket;
import com.example.cinema.vo.VIPCardForm;
import com.example.cinema.po.VIPCard;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.VIPInfoVO;
import com.example.cinema.vo.VIPRefundForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by liying on 2019/4/14.
 */
@Service
public class VIPServiceImpl implements VIPService {
    @Autowired
    VIPCardMapper vipCardMapper;

    @Autowired
    TicketMapper ticketMapper;

    @Autowired
    ScheduleMapper scheduleMapper;

    @Override
    public ResponseVO addVIPCard(VIPCardForm vipCardForm) {
        VIPCard vipCard = new VIPCard();
        vipCard.setUserId(vipCardForm.getUserId());
        vipCard.setBalance(0);
        vipCard.setKind(vipCardForm.getKind());
        vipCard.setTargetAmount(vipCardForm.getTargetAmount());
        vipCard.setDiscountAmount(vipCardForm.getDiscountAmount());
        try {
            int id = vipCardMapper.insertOneCard(vipCard);
            return ResponseVO.buildSuccess(vipCardMapper.selectCardById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getCardById(int id) {
        try {
            return ResponseVO.buildSuccess(vipCardMapper.selectCardById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getVIPInfo() {
        VIPInfoVO vipInfoVO = new VIPInfoVO();
//        vipInfoVO.setDescription(VIPCard.description);
//        vipInfoVO.setPrice(VIPCard.price);
//        vipInfoVO.setDuration(VIPCard.duration);
//        vipInfoVO.setName(VIPCard.kind);
        return ResponseVO.buildSuccess(vipInfoVO);
    }

    @Override
    public ResponseVO charge(VIPCardForm vipCardForm) {

        VIPCard vipCard = vipCardMapper.selectCardById(vipCardForm.getVipId());
        if (vipCard == null) {
            return ResponseVO.buildFailure("会员卡不存在");
        }
        double balance = vipCard.calculate(vipCardForm.getAmount());
        vipCard.setBalance(vipCard.getBalance() + balance);
        try {
            vipCardMapper.updateCardBalance(vipCardForm.getVipId(), vipCard.getBalance());
            return ResponseVO.buildSuccess(vipCard);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getCardByUserId(int userId) {
        try {
            VIPCard vipCard = vipCardMapper.selectCardByUserId(userId);
            if(vipCard==null){
                return ResponseVO.buildFailure("用户卡不存在");
            }
            return ResponseVO.buildSuccess(vipCard);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }
    @Override
    public ResponseVO updateVIPCard(String kind,double targetAmount,double discountAmount,String newkind) {
        try {
            vipCardMapper.updateVIPCard(kind, targetAmount, discountAmount, newkind);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO updateVIPCardByUserId(String kind,double targetAmount,double discountAmount,int userId){
        try {
            vipCardMapper.updateVIPCardByUserId(kind,targetAmount,discountAmount,userId);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO refundCard(VIPRefundForm vipRefundForm) {
        try {
            VIPCard vipCard = vipCardMapper.selectCardById(vipRefundForm.getVIPcardID());
            if(vipCard==null){
                return ResponseVO.buildFailure("失败");
            }else {
                vipCard.setBalance(vipCard.getBalance() + vipRefundForm.getAmount());
                vipCardMapper.updateCardBalance(vipCard.getId(),vipCard.getBalance());
                return ResponseVO.buildSuccess();
            }

        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }


}
