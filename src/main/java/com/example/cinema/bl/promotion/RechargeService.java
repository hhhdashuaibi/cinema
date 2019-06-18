package com.example.cinema.bl.promotion;

import com.example.cinema.vo.RechargeItemVO;
import com.example.cinema.vo.ResponseVO;

public interface RechargeService {
    /**
     * 增加充值记录
     *
     * @param rechargeItemVO
     * @return
     */
    ResponseVO addRecharge(RechargeItemVO rechargeItemVO);
    /**
     * 获得用户的所有充值记录
     *
     * @param userId
     * @return
     */
    ResponseVO getRechargeByUser(int userId);
    /**
     * 根据id选择充值记录
     * @param id
     * @return
     */
    ResponseVO getRechargeById(int id);
}
