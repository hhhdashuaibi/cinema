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

    /**
     * 增加一条充值记录
     *
     * @param rechargeItemVO
     * @return
     */
    @PostMapping("/create")
    public ResponseVO createRecharge(@RequestBody RechargeItemVO rechargeItemVO) {
        return rechargeService.addRecharge(rechargeItemVO);
    }

    /**
     * 获取用户所有的消费记录
     *
     * @param userId
     * @return
     */
    @GetMapping("/gety/{userId}")
    public ResponseVO getRechargeByUserId(@PathVariable int userId) {
        return rechargeService.getRechargeByUser(userId);
    }

    /**
     * 获取某一条消费记录
     *
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public ResponseVO getRechargeById(@PathVariable int id) {
        return rechargeService.getRechargeById(id);
    }

}
