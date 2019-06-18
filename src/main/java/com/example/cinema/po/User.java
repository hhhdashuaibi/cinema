package com.example.cinema.po;


import com.example.cinema.vo.UserVO;


/**
 * @author huwen
 * @date 2019/3/23
 */
public class User {
    private Integer id;
    private String username;
    private String password;

    /*
    * 权限，0,1,2依次递减
    * */
    private int power;

    private String name;
    public UserVO getVO() {
        UserVO vo = new UserVO(this);
        return vo;

    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
