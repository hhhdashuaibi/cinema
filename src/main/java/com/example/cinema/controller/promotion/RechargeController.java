package com.example.cinema.controller.promotion;

import com.example.cinema.vo.RechargeItemVO;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.bl.promotion.RechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recharge")
public class RechargeController {
    @Autowired
    RechargeService rechargeService;

    @PostMapping("/create")
    public ResponseVO createRecharge(@RequestBody RechargeItemVO rechargeItemVO) {
        return rechargeService.addRecharge(rechargeItemVO);
    }


    @GetMapping("/gety/{userId}")
    public ResponseVO getRechargeByUserId(@PathVariable int userId) {
        return rechargeService.getRechargeByUser(userId);
    }

    @GetMapping("/get/{id}")
    public ResponseVO getRechargeById(@PathVariable int id) {
        return rechargeService.getRechargeById(id);
    }

}
