package com.example.cinema.data.sales;

import com.example.cinema.po.Purchase;
import com.example.cinema.vo.PurchaseItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;
@Mapper
public interface PurchaseMapper {

    /**
     * 增加消费记录
     * @param purchase
     * @return
     */
    int insertPurchase(Purchase purchase);

    /**
     * 获取用户所有消费记录
     * @param userId
     * @return
     */
    List<Purchase> selectPurchasesByUser(int userId);

    /**
     * 获取某一条消费记录
     * @param id
     * @return
     */
    Purchase selectPurchaseById(@Param("id") int id);

    void updatePurchaseState(@Param("id") int id,@Param("purchaseState") int purchaseState);

    /**
     * 在purchase_ticket表中对应地添加
     * @param purchaseId
     * @param ticketId
     */
    void insertPurchaseTicket(@Param("purchaseId") int purchaseId,@Param("ticketId")int ticketId);

    /**
     * 根据票的id获取对应的消费记录
     * @param ticketId
     * @return
     */
    Purchase selectPurchaseByTicket(int ticketId);

    /**
     * 获取所有的消费记录
     * @return
     */
    List<Purchase> selectAllPurchases();
}
