package com.example.cinema.blImpl.promotion;

import com.example.cinema.bl.promotion.VIPKindService;
import com.example.cinema.data.promotion.VIPKindMapper;
import com.example.cinema.po.VIPKind;
import com.example.cinema.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;


@Service
public class VIPKindServiceImpl implements VIPKindService {
    @Autowired
    VIPKindMapper vipKindMapper;
    @Override
    public ResponseVO addVIPKind(VIPKindForm vipKindForm){
        try {
            String name=vipKindForm.getName();
            double targetAmount=vipKindForm.getTargetAmount();
            double discountAmount=vipKindForm.getDiscountAmount();
            double price=vipKindForm.getPrice();
            int duration=vipKindForm.getDuration();
            Timestamp issueTime=vipKindForm.getIssueTime();
            VIPKind vipKind=new VIPKind();
            vipKind.setName(name);
            vipKind.setTargetAmount(targetAmount);
            vipKind.setDiscountAmount(discountAmount);
            vipKind.setDuration(duration);
            vipKind.setIssueTime(issueTime);
            vipKind.setPrice(price);
            vipKindMapper.insertOneVIPKind(vipKind);
            return ResponseVO.buildSuccess(vipKind);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }
    @Override
    public ResponseVO getVIPKindByName(String name){
        try {
            VIPKind vipKind=vipKindMapper.selectVIPKindByName(name);
            return ResponseVO.buildSuccess(vipKind);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }
    @Override
    public ResponseVO deleteVIPKind(String name){
        try {
            vipKindMapper.deleteVIPKind(name);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }
    @Override
    public ResponseVO getVIPKinds(){
        try {
            return ResponseVO.buildSuccess(vipKindMapper.selectVIPKinds().stream().map(VIPKind::getVO));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO updateVIPKind(VIPKindForm vipKindForm){
        try {
            vipKindMapper.updateVIPKind(vipKindForm);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }
}
