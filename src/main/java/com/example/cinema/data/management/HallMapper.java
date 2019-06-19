package com.example.cinema.data.management;

import com.example.cinema.po.Hall;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author fjj
 * @date 2019/4/11 3:46 PM
 */
@Mapper
public interface HallMapper {
    /**
     * 查询所有影厅信息
     * @return
     */
    List<Hall> selectAllHall();

    /**
     * 根据id查询影厅
     * @return
     */
    Hall selectHallById(@Param("hallId") int hallId);


    /**
     * 影厅信息录入，增加影厅
     * @param name
     * @param type
     * @param row
     * @param column
     * @return
     */
    void addHall(@Param("name") String name,@Param("type") String type,@Param("row") int row,@Param("column") int column);

    /**
     * 根据id修改影厅信息
     * @param id
     * @param name
     * @param type
     * @param row
     * @param column
     * @return
     */
    void modifyHall(@Param("id") int id,@Param("name") String name,@Param("type") String type,@Param("row") int row,@Param("column") int column);

    /**
     * 根据id删除该影厅
     * @param id
     * @return
     */
    void deleteHall(int id);

}
