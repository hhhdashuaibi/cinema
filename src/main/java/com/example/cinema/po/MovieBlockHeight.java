package com.example.cinema.po;
/**
 * @author ZPC
 * @date 2019/5/14 6:00 PM
 */
public class MovieBlockHeight {
    private String blockType;
    private Integer blockHeight;

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
