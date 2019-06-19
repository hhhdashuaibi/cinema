package com.example.cinema.blImpl.user;

import com.example.cinema.bl.user.AccountService;
import com.example.cinema.data.user.AccountMapper;
import com.example.cinema.po.User;
import com.example.cinema.vo.UserForm;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huwen
 * @date 2019/3/23
 */
@Service
public class AccountServiceImpl implements AccountService {
    private final static String ACCOUNT_EXIST = "账号已存在";
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public ResponseVO registerAccount(UserForm userForm) {
        try {

            accountMapper.createNewAccount(userForm.getUsername(), userForm.getPassword(),2);

        } catch (Exception e) {
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public UserVO login(UserForm userForm) {
        User user = accountMapper.getAccountByName(userForm.getUsername());
        if (null == user || !user.getPassword().equals(userForm.getPassword())) {
            return null;
        }
        return new UserVO(user);
    }

    @Override
    public ResponseVO updatePowerAndName(UserForm userForm){
        try {
            accountMapper.updatePowerAndName(userForm.getPower(),userForm.getName(),userForm.getUsername());
        } catch (Exception e) {
            return ResponseVO.buildFailure("失败");
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public ResponseVO getStaff() {
        return ResponseVO.buildSuccess(accountMapper.getStaff(1).stream().map(User::getVO));
    }

    @Override
    public ResponseVO getQualifiedUsers(double targetPurchase){
        try {
            List<User> users =accountMapper.getUsersByPurchase(targetPurchase);
            List<UserVO> userVOS=new ArrayList<>();
            for(int i=0;i<users.size();i++){
                userVOS.add(users.get(i).getVO());
            }
            if(userVOS != null){
                return ResponseVO.buildSuccess(userVOS);
            }else{
                return ResponseVO.buildSuccess(null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO updateTotalPurchase(double purchaseAmount,int userId){
        double rawTotal=accountMapper.getTotalPurchase(userId).getTotalPurchase();
        double res=rawTotal+purchaseAmount;
        accountMapper.updateTotalPurchase(res,userId);
        return ResponseVO.buildSuccess();
    }
}
