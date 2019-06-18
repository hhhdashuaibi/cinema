package com.example.cinema.vo;

import com.example.cinema.po.Movie;

import java.sql.Timestamp;
import java.util.List;

public class RefundVO {
    //
    private int id;
    //退款规则名称
    private String name;
    //退款规则开始日期
    private Timestamp startTime;
    //退款日期结束日期
    private Timestamp endTime;
    //退款金额底线
    private double targetAmount;
    //天数限制
    private int timeLimit;
    //电影列表
    private List<MovieVO> movieList;

    public RefundVO(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public List<MovieVO> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<MovieVO> movieList) {
        this.movieList = movieList;
    }
}
