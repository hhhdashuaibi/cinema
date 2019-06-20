package com.example.cinema.bl.sales;


import com.example.cinema.vo.PurchaseItemVO;
import com.example.cinema.vo.ResponseVO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface PurchaseService {
    /**
     * 根据前端传回的消费信息增加消费记录
     *
     * @param purchaseItemVO
     * @return
     */
    ResponseVO addPurchase(PurchaseItemVO purchaseItemVO);
    /**
     * 获得用户的所有消费记录
     *
     * @param userId
     * @return
     */
    ResponseVO getPurchaseByUser(int userId);
    /**
     * 根据id选择消费记录
     * @param id
     * @return
     */
    ResponseVO getPurchaseById(int id);
    /**
     * 根据id将消费记录状态设置为失效
     * @param id
     * @return
     */
    ResponseVO canclePurchase(int id);
    /**
     * 根据票获取相应的消费记录
     * @param ticketId
     * @return
     */
    ResponseVO getByTicket(int ticketId);

    /**
     * 将消费记录与票对应地加入purchase_ticket表中
     *
     * @param purchaseId
     * @param ticketId
     * @return
     */
    ResponseVO issuePurchase(int purchaseId,List<Integer> ticketId);

    /**
     * 获取当前最后一条消费记录
     *
     * @return
     */
    ResponseVO getLastPurchase();
}
