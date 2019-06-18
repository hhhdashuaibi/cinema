package com.example.cinema.bl.management;

import com.example.cinema.vo.ResponseVO;

/**
 * @author fjj
 * @date 2019/4/12 2:01 PM
 */
public interface HallService {
    /**
     * 搜索所有影厅
     * @return
     */
    ResponseVO searchAllHall();


    /**
     * 影厅信息录入，增加影厅
     * @param name
     * @param type
     * @return
     */
    ResponseVO addHall(String name,String type);

    /**
     * 根据id修改影厅信息
     * @param name
     * @param type
     * @param id
     * @return
     */
    ResponseVO modifyHall(String name,String type,int id);

    /**
     * 根据id删除该影厅
     * @param id
     * @return
     */
    ResponseVO deleteHall(int id);

}
