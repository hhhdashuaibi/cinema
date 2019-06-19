package com.example.cinema.data.promotion;

import com.example.cinema.po.Refund;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RefundMapper {
    int insertRefund(Refund refund);
    int insertRefundAndMovie(@Param("refundId") int refundId, @Param("movieId") List<Integer> movieId);
    int changeRefund(Refund refund);
    int changeRefundAndMovie(@Param("refundId") int refundId, @Param("movieId")List<Integer> movieId);
    int deleteRefundAndMovie(@Param("refundId")int refundId);

    List<Refund> SelectRefunds();
    List<Refund> SelectRefundsByMovie(int movieId);

    Refund SelectByID(int refundId);

}
