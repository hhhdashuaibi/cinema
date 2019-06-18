package com.example.cinema.controller.promotion;


import com.example.cinema.bl.promotion.RefundService;
import com.example.cinema.vo.RefundForm;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/refund")
public class RefundController {
    @Autowired
    RefundService refundService;

    @PostMapping("/publish")
    public ResponseVO publishRefund(@RequestBody RefundForm refundForm){
        return refundService.publishRefund(refundForm);
    }

    @PostMapping("/change")
    public ResponseVO changeRefund(@RequestBody RefundForm refundForm){
        return  refundService.changeRefund(refundForm);
    }



    @GetMapping("/get")
    public ResponseVO getRefunds(){
        return refundService.getRefunds();
    }

}
