package com.example.cinema.controller.user;

import com.example.cinema.blImpl.user.AccountServiceImpl;
import com.example.cinema.config.InterceptorConfiguration;
import com.example.cinema.vo.UserForm;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

/**
 * @author huwen
 * @date 2019/3/23
 */
@RestController()
public class AccountController {
    private final static String ACCOUNT_INFO_ERROR="用户名或密码错误";
    @Autowired
    private AccountServiceImpl accountService;
    @PostMapping("/login")
    public ResponseVO login(@RequestBody UserForm userForm, HttpSession session){
        UserVO user = accountService.login(userForm);
        if(user==null){
           return ResponseVO.buildFailure(ACCOUNT_INFO_ERROR);
        }
        //注册session
        session.setAttribute(InterceptorConfiguration.SESSION_KEY,userForm);
        return ResponseVO.buildSuccess(user);
    }
    @PostMapping("/register")
    public ResponseVO registerAccount(@RequestBody UserForm userForm){
        return accountService.registerAccount(userForm);
    }

    @PostMapping("/logout")
    public String logOut(HttpSession session){
        session.removeAttribute(InterceptorConfiguration.SESSION_KEY);
        return "index";
    }

    @GetMapping("/getStaff")
    public ResponseVO getStaff(){
        return  accountService.getStaff();

    }

    @PostMapping("/updateStaff")
    public ResponseVO updatePowerAndName(@RequestBody UserForm userForm){
        return accountService.updatePowerAndName(userForm);
    }

    /**
     * 更新用户总消费
     * @param purchaseAmount
     * @param userId
     * @return
     */
    @PostMapping("/updateTotalPurchase")
    public ResponseVO updateTotalPurchase(@RequestParam double purchaseAmount, @RequestParam int userId){
        return accountService.updateTotalPurchase(purchaseAmount,userId);
    }

    /**
     * 获取符合最低消费条件的用户
     * @param targetPurchase
     * @return
     */
    @GetMapping("/getQualifiedUser")
    public ResponseVO getQualifiedUser(@RequestParam double targetPurchase){
        return accountService.getQualifiedUsers(targetPurchase);
    }

}
