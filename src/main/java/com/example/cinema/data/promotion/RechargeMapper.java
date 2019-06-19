package com.example.cinema.data.promotion;

import com.example.cinema.po.Recharge;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface RechargeMapper {
    int insertRecharge(Recharge recharge);

    List<Recharge> selectRechargesByUser(int userId);

    Recharge selectRechargeById(@Param("id") int id);
}
