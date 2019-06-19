package com.example.cinema.data.sales;

import com.example.cinema.po.Purchase;
import com.example.cinema.vo.PurchaseItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;
@Mapper
public interface PurchaseMapper {

    int insertPurchase(Purchase purchase);

    List<Purchase> selectPurchasesByUser(int userId);

    Purchase selectPurchaseById(@Param("id") int id);

    void updatePurchaseState(@Param("id") int id,@Param("purchaseState") int purchaseState);

    void insertPurchaseTicket(@Param("purchaseId") int purchaseId,@Param("ticketId")int ticketId);

    Purchase selectPurchaseByTicket(Timestamp ticketTime);

    List<Purchase> selectAllPurchases();
}
