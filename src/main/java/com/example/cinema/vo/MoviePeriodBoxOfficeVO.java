package com.example.cinema.vo;

import com.example.cinema.po.MoviePeriodBoxOffice;
/**
 * @author ZPC
 * @date 2019/5/14 6:00 PM
 */

public class MoviePeriodBoxOfficeVO {
    private Integer movieId;
    private Integer boxOffice;
    private String name;

    public MoviePeriodBoxOfficeVO(MoviePeriodBoxOffice moviePeriodBoxOffice){
        this.movieId=moviePeriodBoxOffice.getMovieId();
        this.boxOffice=moviePeriodBoxOffice.getBoxOffice();
        this.name=moviePeriodBoxOffice.getName();
    }

    public Integer getBoxOffice() {
        return boxOffice;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public String getName() {
        return name;
    }

    public void setBoxOffice(Integer boxOffice) {
        this.boxOffice = boxOffice;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
