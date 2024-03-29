package com.example.cinema.blImpl.sales;

import com.example.cinema.bl.sales.PurchaseService;
import com.example.cinema.blImpl.management.schedule.ScheduleServiceForBl;
import com.example.cinema.data.management.ScheduleMapper;
import com.example.cinema.data.sales.PurchaseMapper;
import com.example.cinema.data.sales.TicketMapper;
import com.example.cinema.po.*;
import com.example.cinema.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    @Autowired
    PurchaseMapper purchaseMapper;
    @Autowired
    ScheduleServiceForBl scheduleService;
    @Autowired
    PurchaseServiceImpl purchaseService;
    @Autowired
    private ScheduleMapper scheduleMapper;
    @Autowired
    TicketMapper ticketMapper;

    @Override
    @Transactional
    public ResponseVO addPurchase(PurchaseItemVO purchaseitem) {
        //增加一条消费记录
        try {
            int userID=purchaseitem.getUserId();
            Purchase purchaseToAdd=new Purchase();
            purchaseToAdd.setUserId(userID);
            purchaseToAdd.setAmount(purchaseitem.getAmount());
            purchaseToAdd.setPayMethod(purchaseitem.getPayMethod());
            purchaseToAdd.setTime(new Timestamp(System.currentTimeMillis()));
            purchaseMapper.insertPurchase(purchaseToAdd);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getPurchaseByUser(int userId) {
        //根据userid来获取该用户的所有消费记录
        try{
            List<Purchase> purchases=purchaseMapper.selectPurchasesByUser(userId);
            List<PurchaseVO>purchaseVOS=new ArrayList<>();
            for(int i=0;i<purchases.size();i++){
                purchaseVOS.add(purchases.get(i).getVO());
            }
            return ResponseVO.buildSuccess(purchaseVOS);

        }catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getPurchaseById(int id) {
        //根据消费记录Id获取消费记录
        try {
            Purchase purchase =purchaseMapper.selectPurchaseById(id);
            if(purchase != null){
                return ResponseVO.buildSuccess(purchase.getVO());
            }else{
                return ResponseVO.buildSuccess(null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO canclePurchase(int id){
        //将消费记录状态设置为失效
        try {
            Purchase purchaseToCancle=purchaseMapper.selectPurchaseById(id);
            purchaseMapper.updatePurchaseState(id,1);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getByTicket(int ticketId){
        //根据某笔消费的时间获取对应的电影票信息
        try{
            Purchase purchaseToSelect=purchaseMapper.selectPurchaseByTicket(ticketId);
            PurchaseVO purchaseVO=purchaseToSelect.getVO();
            return ResponseVO.buildSuccess(purchaseVO);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO issuePurchase(int purchaseId, List<Integer> ticketId) {
        //在purchase_ticket表中添加若干条对应的记录
        try {
            for(int i=0;i<ticketId.size();i++){
                purchaseMapper.insertPurchaseTicket(purchaseId,ticketId.get(i));
            }
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }

    }

    @Override
    @Transactional
    public ResponseVO getLastPurchase() {
        try {
            List<Purchase> purchases=purchaseMapper.selectAllPurchases();
            int lastId=purchases.get(purchases.size()-1).getId();
            return ResponseVO.buildSuccess(lastId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }
}
