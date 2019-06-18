package com.example.cinema.controller.sales;

import com.example.cinema.vo.PurchaseItemVO;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.bl.sales.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    @Autowired
    PurchaseService purchaseService;

    @PostMapping("/create")
    public ResponseVO createPurchase(@RequestBody PurchaseItemVO purchaseItem) {
        return purchaseService.addPurchase(purchaseItem);
    }

    @PostMapping("/cancle")
    public ResponseVO canclePurchase(@RequestParam int purchaseId) {
        return purchaseService.canclePurchase(purchaseId);
    }

    @GetMapping("/gety/{userId}")
    public ResponseVO getPurchaseByUserId(@PathVariable int userId) {
        return purchaseService.getPurchaseByUser(userId);
    }

    @GetMapping("/get/{id}")
    public ResponseVO getPurchaseById(@PathVariable int id) {
        return purchaseService.getPurchaseById(id);
    }

    @GetMapping("/gett/ticket")
    public ResponseVO getPurchaseByTicket(@RequestParam int ticketId){
        return purchaseService.getByTicket(ticketId);
    }
}
