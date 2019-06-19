package com.example.cinema.po;

import com.example.cinema.vo.MovieVO;
import com.example.cinema.vo.RefundVO;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

public class Refund {

    //
    private int id;
    //退款规则名称
    private String sname;
    //退款规则开始日期
    private Timestamp startTime;
    //退款日期结束日期
    private Timestamp endTime;
    //退款金额底线
    private double targetAmount;
    //天数限制
    private int timeLimit;
    //电影列表
    private List<Movie> movieList;

    public Refund(){

    }

    public RefundVO getVO(){
        RefundVO vo=new RefundVO();
        vo.setId(id);
        vo.setName(sname);
        vo.setStartTime(startTime);
        vo.setEndTime(endTime);
        vo.setTargetAmount(targetAmount);
        vo.setTimeLimit(timeLimit);


        vo.setMovieList(movieList.stream().map(movie -> {

            MovieVO mvo = new MovieVO();
            mvo.setId(movie.getId());
            mvo.setName(movie.getName());

            mvo.setPosterUrl(movie.getPosterUrl());
            mvo.setDirector(movie.getDirector());
            mvo.setScreenWriter(movie.getScreenWriter());
            mvo.setStarring(movie.getStarring());
            mvo.setType(movie.getType());
            mvo.setCountry(movie.getCountry());
            mvo.setLanguage(movie.getLanguage());
            mvo.setStartDate(movie.getStartDate());
            mvo.setLength(movie.getLength());
            mvo.setDescription(movie.getDescription());
            mvo.setStatus(movie.getStatus());
            mvo.setIslike(movie.getIslike());
            mvo.setLikeCount(movie.getLikeCount());
            return mvo;} ).collect(Collectors.toList()));
        return vo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return sname;
    }

    public void setName(String name) {
        this.sname = name;
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

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }
}
