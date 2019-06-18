package com.example.cinema.po;

/**
 * @author ZPC
 * @date 2019/5/14 6:00 PM
 */

public class MoviePeriodBoxOffice {
    private Integer movieId;
    private Integer boxOffice;
    private String name;

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
