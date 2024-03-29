package com.example.cinema.vo;

import com.example.cinema.po.Hall;

/**
 * @author fjj
 * @date 2019/4/11 3:46 PM
 */
public class HallVO {
    private Integer id;
    private String name;
    private Integer row;
    private Integer column;
    private String type;

    public HallVO(Hall hall){
        this.id = hall.getId();
        this.name = hall.getName();
        this.row = hall.getRow();
        this.column = hall.getColumn();
        this.type=hall.getType();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }
}
