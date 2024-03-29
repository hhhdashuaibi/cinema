package com.example.cinema.controller.promotion;

import com.example.cinema.bl.promotion.VIPKindService;
import com.example.cinema.bl.promotion.VIPService;
import com.example.cinema.vo.VIPCardForm;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.VIPKindForm;
import com.example.cinema.po.VIPCard;
import com.example.cinema.vo.VIPCardForm;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.VIPKindForm;
import com.example.cinema.vo.VIPRefundForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by liying on 2019/4/14.
 */
@RestController()
@RequestMapping("/vip")
public class VIPCardController {
    @Autowired
    VIPService vipService;
    @Autowired
    VIPKindService vipKindService;
    @PostMapping("/add")
    public ResponseVO addVIP(@RequestBody VIPCardForm vipCardForm){
        return vipService.addVIPCard(vipCardForm);
    }
    @GetMapping("{userId}/get")
    public ResponseVO getVIP(@PathVariable int userId){
        return vipService.getCardByUserId(userId);
    }
    @GetMapping("/getVIPInfo")
    public ResponseVO getVIPInfo(){
        return vipService.getVIPInfo();
    }
    @PostMapping("/charge")
    public ResponseVO charge(@RequestBody VIPCardForm vipCardForm){
        return vipService.charge(vipCardForm);
    }
    @PostMapping("/updateVIPCard")
    public ResponseVO updateVIPCard(@RequestParam String kind,@RequestParam double targetAmount,@RequestParam double discountAmount,@RequestParam String newkind){return vipService.updateVIPCard(kind,targetAmount,discountAmount,newkind);}
    @PostMapping("/updateVIPCardByUserId")
    public ResponseVO updateVIPCardByUserId(@RequestParam String kind,@RequestParam double targetAmount,@RequestParam double discountAmount,@RequestParam int userId){return vipService.updateVIPCardByUserId(kind,targetAmount,discountAmount,userId);}
    @PostMapping("/issue")
    public ResponseVO addVIPKind(@RequestBody VIPKindForm vipKindForm){return vipKindService.addVIPKind(vipKindForm);}
    @PostMapping("/refund")
    public ResponseVO refundVIPCard(@RequestBody VIPRefundForm vipRefundForm){
        return vipService.refundCard(vipRefundForm);
    }
    @DeleteMapping("/deleteVIPKind")
    public ResponseVO deleteVIPKind(@RequestParam String name){
        return vipKindService.deleteVIPKind(name);
    }
    @GetMapping("/getVIPKind")
    public ResponseVO getVIPKindById(@RequestParam String name){return vipKindService.getVIPKindByName(name);}
    @GetMapping("/getVIPKinds")
    public ResponseVO getVIPKinds(){return vipKindService.getVIPKinds();}
    @PostMapping("/updateVIPKind")
    public ResponseVO updateVIPKind(@RequestBody VIPKindForm vipKindForm){return vipKindService.updateVIPKind(vipKindForm);}

}
