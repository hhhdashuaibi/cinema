package com.example.cinema.blImpl.promotion;

import com.example.cinema.bl.promotion.RefundService;
import com.example.cinema.data.promotion.RefundMapper;
import com.example.cinema.po.Refund;
import com.example.cinema.vo.RefundForm;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RefundServicelmpl implements RefundService {

    @Autowired
    RefundMapper refundMapper;


    @Override
    @Transactional
    public ResponseVO publishRefund(RefundForm refundForm) {
        try{
            Refund refund=new Refund();
            refund.setId(refundForm.getId());
            refund.setName(refundForm.getName());
            refund.setStartTime(refundForm.getStartTime());
            refund.setEndTime(refundForm.getEndTime());
            refund.setTargetAmount(refundForm.getTargetAmount());
            refund.setTimeLimit(refundForm.getTimeLimit());
            refundMapper.insertRefund(refund);
            if(refundForm.getMovieList().size()!=0&&refundForm.getMovieList()!=null){
                System.out.println(refundForm.getMovieList());
                refundMapper.insertRefundAndMovie(refund.getId(),refundForm.getMovieList());
            }
            return ResponseVO.buildSuccess(refundMapper.SelectByID(refund.getId()));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getRefunds() {
        try {
            return ResponseVO.buildSuccess(refundMapper.SelectRefunds().stream().map(Refund::getVO));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO changeRefund(RefundForm refundForm) {
        try {
            Refund refund=refundMapper.SelectByID(Integer.parseInt(refundForm.getName()));
            refund.setStartTime(refundForm.getStartTime());
            refund.setEndTime(refundForm.getEndTime());
            refund.setTargetAmount(refundForm.getTargetAmount());
            refund.setTimeLimit(refundForm.getTimeLimit());
            refundMapper.changeRefund(refund);
            if(refundForm.getMovieList().size()!=0&&refundForm.getMovieList()!=null){
                refundMapper.changeRefundAndMovie(refund.getId(),refundForm.getMovieList());
            }
            else {
                refundMapper.deleteRefundAndMovie(refund.getId());
            }
            return ResponseVO.buildSuccess(refundMapper.SelectByID(refund.getId()));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }

    }
}
