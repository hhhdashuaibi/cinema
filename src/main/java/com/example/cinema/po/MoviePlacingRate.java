package com.example.cinema.po;

/**
 * @author ZPC
 * @date 2019/5/14 7:35 PM
 */

public class MoviePlacingRate {
    private Integer movieId;
    private String rate; //电影的上座率，以百分比显示
    private String name;

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public String getName() {
        return name;
    }

    public String getRate() {
        return rate;
    }
}
