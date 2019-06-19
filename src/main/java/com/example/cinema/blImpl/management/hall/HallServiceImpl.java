package com.example.cinema.blImpl.management.hall;

import com.example.cinema.bl.management.HallService;
import com.example.cinema.data.management.HallMapper;
import com.example.cinema.data.management.ScheduleMapper;
import com.example.cinema.po.Hall;
import com.example.cinema.po.ScheduleItem;
import com.example.cinema.vo.HallVO;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author fjj
 * @date 2019/4/12 2:01 PM
 */
@Service
public class HallServiceImpl implements HallService, HallServiceForBl {
    @Autowired
    private HallMapper hallMapper;

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Override
    public ResponseVO searchAllHall() {
        try {
            return ResponseVO.buildSuccess(hallList2HallVOList(hallMapper.selectAllHall()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public Hall getHallById(int id) {
        try {
            return hallMapper.selectHallById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public ResponseVO addHall(String name,String type){
        try {
            //判断要增加的影厅名称是否重名，是则不能修改影厅
            List<Hall> halls=hallMapper.selectAllHall();
            AtomicInteger isNameExisted= new AtomicInteger();
            halls.stream().forEach(hall -> {   //此处是遍历halls列表中的每一个元素并赋值给临时的hall变量
                if(hall.getName().equals(name)){
                    isNameExisted.set(1);
                }
            });
            if(isNameExisted.get() ==1){
                return ResponseVO.buildFailure("已存在此影厅名称，请重新输入！");
            }
            //可以增加
            int row,column=0;
            if(type.equals("小")){
                row=5;
                column=10;
            }
            else if(type.equals("中")){
                row=8;
                column=12;
            }
            else{
                row=10;
                column=14;
            }
            hallMapper.addHall(name,type,row,column);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("增加影厅失败");
        }
    }

    @Override
    public ResponseVO modifyHall(String name,String type,int id){
        try {

            List<ScheduleItem> scheduleItems=isHallBusy(id);

            if(!scheduleItems.isEmpty()){
                return ResponseVO.buildFailure("该影厅还有未结束的排片，暂且无法修改影厅信息！");
            }

            //判断要修改的影厅名称是否重名，是则不能修改影厅，但是允许修改它自己
            List<Hall> halls=hallMapper.selectAllHall();
            AtomicInteger isNameExisted= new AtomicInteger();
            halls.stream().forEach(hall -> {   //此处是遍历halls列表中的每一个元素并赋值给临时的hall变量
                if(hall.getName().equals(name)&&hall.getId()!=id){ //通过id判断是不是修改该影厅自己，若是，则允许名称不变只改大小
                    isNameExisted.set(1);
                }
            });
            if(isNameExisted.get() ==1){
                return ResponseVO.buildFailure("已存在此影厅名称，请重新输入！");
            }

            //可以修改影厅
            System.out.println("开始修改影厅");
            int row,column=0;
            if(type.equals("小")){
                row=5;
                column=10;
            }
            else if(type.equals("中")){
                row=8;
                column=12;
            }
            else{
                row=10;
                column=14;
            }
            hallMapper.modifyHall(id,name,type,row,column);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("修改影厅信息失败");
        }

    }
    @Override
    public ResponseVO deleteHall(int id){
        try {
            List<ScheduleItem> scheduleItems=isHallBusy(id);
            if(!scheduleItems.isEmpty()){
                return ResponseVO.buildFailure("该影厅还有未结束的排片，暂且无法删除该影厅！");
            }

            hallMapper.deleteHall(id);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }
    private List<ScheduleItem> isHallBusy(int id){
        //判断该影厅是否还有未结束的排片记录，若是则不能修改该影厅
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //Date today = simpleDateFormat.parse(simpleDateFormat.format(new Date())); //获取今天日期，并格式化
        Date today=new Date();
        Date endDate=getNumDayAfterDate(today,720);
        System.out.println(today);
        System.out.println(endDate);
        List<ScheduleItem> scheduleItems=scheduleMapper.selectSchedule(id,today,endDate);
        System.out.println(scheduleItems);
        return scheduleItems;
    }

    private List<HallVO> hallList2HallVOList(List<Hall> hallList){
        List<HallVO> hallVOList = new ArrayList<>();
        for(Hall hall : hallList){
            hallVOList.add(new HallVO(hall));
        }
        return hallVOList;
    }

    /**
     * 获得num天后的日期
     * @param oldDate
     * @param num
     * @return
     */
    Date getNumDayAfterDate(Date oldDate, int num){
        Calendar calendarTime = Calendar.getInstance();
        calendarTime.setTime(oldDate);
        calendarTime.add(Calendar.DAY_OF_YEAR, num);
        return calendarTime.getTime();
    }

}
