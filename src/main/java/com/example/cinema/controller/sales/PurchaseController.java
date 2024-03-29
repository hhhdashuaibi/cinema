package com.example.cinema.controller.sales;

import com.example.cinema.vo.PurchaseItemVO;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.bl.sales.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    @Autowired
    PurchaseService purchaseService;

    /**
     * 增加消费记录
     *
     * @param purchaseItem
     * @return
     */
    @PostMapping("/create")
    public ResponseVO createPurchase(@RequestBody PurchaseItemVO purchaseItem) {
        return purchaseService.addPurchase(purchaseItem);
    }

    @PostMapping("/cancle")
    public ResponseVO canclePurchase(@RequestParam int purchaseId) {
        return purchaseService.canclePurchase(purchaseId);
    }

    /**
     * 获取用户所有的消费记录
     *
     * @param userId
     * @return
     */
    @GetMapping("/gety/{userId}")
    public ResponseVO getPurchaseByUserId(@PathVariable int userId) {
        return purchaseService.getPurchaseByUser(userId);
    }

    /**
     * 获取某一条消费记录
     *
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public ResponseVO getPurchaseById(@PathVariable int id) {
        return purchaseService.getPurchaseById(id);
    }

    /**
     * 获取与ticket对应的消费记录
     *
     * @param ticketId
     * @return
     */
    @GetMapping("/gett/ticket")
    public ResponseVO getPurchaseByTicket(@RequestParam int ticketId){
        return purchaseService.getByTicket(ticketId);
    }

    /**
     * 在purchase_ticket表中对应地添加
     *
     * @param purchaseId
     * @param ticketId
     * @return
     */
    @PostMapping("/addPurchaseAndTicket")
    public ResponseVO issuePurchase(@RequestParam int purchaseId,@RequestParam List<Integer> ticketId){
        return purchaseService.issuePurchase(purchaseId,ticketId);
    };

    /**
     * 获取当前添加的消费记录的id
     *
     * @return
     */
    @GetMapping("/getPurchaseId")
    public ResponseVO getPurchaseId(){return purchaseService.getLastPurchase();}
}
