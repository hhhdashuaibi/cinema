package com.example.cinema.data.user;

import com.example.cinema.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huwen
 * @date 2019/3/23
 */
@Mapper
public interface AccountMapper {

    /**
     * 创建一个新的账号
     * @param username
     * @param password
     * @return
     */
    public int createNewAccount(@Param("username") String username, @Param("password") String password, @Param("power") int power,@Param("totalPurchase") double totalPurchase);

    /**
     * 根据用户名查找账号
     *
     * @param username
     * @return
     */
    public User getAccountByName(@Param("username") String username);

    public void updatePowerAndName(@Param("power") int power, @Param("name") String name, @Param("username") String username);

    public List<User> getStaff(@Param("power") int power);

    /**
     * 根据最低消费选择符合条件的用户
     * @param targetPurchase
     * @return
     */
    public List<User> getUsersByPurchase(@Param("targetPurchase") double targetPurchase);

    /**
     * 更新用户的总消费
     * @param newtotalPurchase
     * @param userId
     */
    public void updateTotalPurchase(@Param("newTotalPurchase") double newtotalPurchase, @Param("userId") int userId);

    /**
     * 获取用户的总消费
     * @param userId
     * @return
     */
    public User getTotalPurchase(@Param("userId") int userId);

}
