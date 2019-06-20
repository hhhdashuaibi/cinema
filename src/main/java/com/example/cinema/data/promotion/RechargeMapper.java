package com.example.cinema.data.promotion;

import com.example.cinema.po.Recharge;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface RechargeMapper {
    /**
     * 增加充值记录
     * @param recharge
     * @return
     */
    int insertRecharge(Recharge recharge);

    /**
     * 获取用户所有的充值记录
     * @param userId
     * @return
     */
    List<Recharge> selectRechargesByUser(int userId);

    /**
     * 获取某一条消费记录
     * @param id
     * @return
     */
    Recharge selectRechargeById(@Param("id") int id);
}
