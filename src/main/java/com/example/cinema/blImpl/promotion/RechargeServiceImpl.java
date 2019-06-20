package com.example.cinema.blImpl.promotion;

import com.example.cinema.bl.promotion.RechargeService;
import com.example.cinema.data.promotion.RechargeMapper;
import com.example.cinema.data.promotion.VIPCardMapper;
import com.example.cinema.po.*;
import com.example.cinema.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class RechargeServiceImpl implements RechargeService {
    @Autowired
    RechargeMapper rechargeMapper;
    @Autowired
    VIPCardMapper vipCardMapper;
    @Autowired
    RechargeServiceImpl rechargeService;

    @Override
    @Transactional
    public ResponseVO addRecharge(RechargeItemVO rechargeItemVO) {
        //增加一条消费记录
        try {
            int userID=rechargeItemVO.getUserId();
            VIPCard card=vipCardMapper.selectCardByUserId(userID);
            double actualAmount=card.calculate(rechargeItemVO.getPayAmount());
            double newBalance=card.getBalance();
            Recharge rechargeToAdd=new Recharge();
            rechargeToAdd.setUserId(userID);
            rechargeToAdd.setPayAmount(rechargeItemVO.getPayAmount());
            rechargeToAdd.setActualAmount(actualAmount);
            rechargeToAdd.setAfterCardBalance(newBalance);
            rechargeToAdd.setRechargeTime(new Timestamp(System.currentTimeMillis()));
            rechargeMapper.insertRecharge(rechargeToAdd);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getRechargeByUser(int userId) {
        //根据userid来获取该用户的所有消费记录
        try{
            List<Recharge> recharges=rechargeMapper.selectRechargesByUser(userId);
            List<RechargeVO> rechargeVOS=new ArrayList<>();
            for(int i=0;i<recharges.size();i++){
                rechargeVOS.add(recharges.get(i).getVO());
            }
            return ResponseVO.buildSuccess(rechargeVOS);

        }catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getRechargeById(int id) {
        //根据充值记录Id获取充值记录
        try {
            Recharge recharge =rechargeMapper.selectRechargeById(id);
            if(recharge != null){
                return ResponseVO.buildSuccess(recharge.getVO());
            }else{
                return ResponseVO.buildSuccess(null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }
}
