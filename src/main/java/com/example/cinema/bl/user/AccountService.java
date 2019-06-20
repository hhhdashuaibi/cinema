package com.example.cinema.bl.user;

import com.example.cinema.vo.UserForm;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.UserVO;

/**
 * @author huwen
 * @date 2019/3/23
 */
public interface AccountService {

    /**
     * 注册账号
     * @return
     */
    public ResponseVO registerAccount(UserForm userForm);

    /**
     * 用户登录，登录成功会将用户信息保存再session中
     * @return
     */
    public UserVO login(UserForm userForm);

    public ResponseVO updatePowerAndName(UserForm userForm);

    public ResponseVO getStaff();

    /**
     * 选取符合最低消费条件的用户
     *
     * @param targetPurchase
     * @return
     */
    public ResponseVO getQualifiedUsers(double targetPurchase);

    /**
     * 用户购票时增加用户总消费金额
     *
     * @param purchaseAmount
     * @param userId
     * @return
     */
    public ResponseVO updateTotalPurchase(double purchaseAmount,int userId);

}
