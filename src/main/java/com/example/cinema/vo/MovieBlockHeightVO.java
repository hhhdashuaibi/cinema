package com.example.cinema.vo;

import com.example.cinema.po.MovieBlockHeight;

/**
 * @author ZPC
 * @date 2019/5/14 7:38 PM
 */
public class MovieBlockHeightVO {
    private String blockType;
    private Integer blockHeight;

    public MovieBlockHeightVO(MovieBlockHeight movieBlockHeight){
        this.blockType=movieBlockHeight.getBlockType();
        this.blockHeight=movieBlockHeight.getBlockHeight();
    }

    public Integer getBlockHeight() {
        return blockHeight;
    }

    public void setBlockHeight(Integer blockHeight) {
        this.blockHeight = blockHeight;
    }

    public void setBlockType(String blockType) {
        this.blockType = blockType;
    }

    public String getBlockType() {
        return blockType;
    }

}
