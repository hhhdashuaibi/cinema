package com.example.cinema.blImpl.sales;

import com.example.cinema.bl.sales.TicketService;
import com.example.cinema.blImpl.management.hall.HallServiceForBl;
import com.example.cinema.blImpl.management.schedule.ScheduleServiceForBl;
import com.example.cinema.data.management.MovieMapper;
import com.example.cinema.data.promotion.ActivityMapper;
import com.example.cinema.data.promotion.CouponMapper;
import com.example.cinema.data.promotion.VIPCardMapper;
import com.example.cinema.data.promotion.RefundMapper;
import com.example.cinema.data.sales.PurchaseMapper;
import com.example.cinema.data.promotion.RefundMapper;
import com.example.cinema.data.promotion.VIPCardMapper;
import com.example.cinema.data.sales.PurchaseMapper;
import com.example.cinema.data.sales.TicketMapper;
import com.example.cinema.po.*;
import com.example.cinema.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liying on 2019/4/16.
 */
@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    TicketMapper ticketMapper;
    @Autowired
    ScheduleServiceForBl scheduleService;
    @Autowired
    HallServiceForBl hallService;

    @Autowired
    CouponMapper couponMapper;
    @Autowired
    ActivityMapper activityMapper;
    @Autowired
    VIPCardMapper vipCardMapper;

    @Autowired
    PurchaseMapper purchaseMapper;
    @Autowired
    RefundMapper refundMapper;

    @Override
    @Transactional
    public ResponseVO addTicket(TicketForm ticketForm) {
        //TODO
        //功能：增加一条票的记录，票的状态为未付款，即未完成：“0”
        try {
            int userID=ticketForm.getUserId();
            int scheduleID=ticketForm.getScheduleId();
            List<SeatForm> seats=ticketForm.getSeats();
            int amount=seats.size();
            List<Ticket> res=new ArrayList<>();
            for(int i=0;i<amount;i++){
                Ticket ticketToAdd=new Ticket();
                ticketToAdd.setUserId(userID);
                ticketToAdd.setScheduleId(scheduleID);
                ticketToAdd.setState(0);
                ticketToAdd.setTime(new Timestamp(System.currentTimeMillis()));
                ticketToAdd.setColumnIndex(seats.get(i).getColumnIndex());
                ticketToAdd.setRowIndex(seats.get(i).getRowIndex());
                res.add(ticketToAdd);
            }
            ticketMapper.insertTickets(res);

            return ResponseVO.buildSuccess(res);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    @Transactional
    public ResponseVO completeTicket(List<Integer> id, int couponId) {
        //TODO
        //完成购票【不使用会员卡】流程包括校验优惠券和根据优惠活动赠送优惠券
        try {
            Timestamp nowTime = new Timestamp(System.currentTimeMillis());//当前时间

            //通过电影票id得到其中一张电影票，并的到排片信息和用户id，得到单价、总价
            Ticket ticket=ticketMapper.selectTicketById(id.get(0));
            int scheduleId=ticket.getScheduleId();
            int userId=ticket.getUserId();
            ScheduleItem scheduleItem=scheduleService.getScheduleItemById(scheduleId);
            double fare=scheduleItem.getFare();
            double total=fare*id.size();

            //校验优惠券，得到使用优惠券之后的总金额，删除已使用优惠券
            if(couponId!=0) {
                Coupon coupon = couponMapper.selectById(couponId);//通过优惠券id获得优惠券对象
                double targetAmount = coupon.getTargetAmount();
                double discountAmount = coupon.getDiscountAmount();
                Timestamp startTime = coupon.getStartTime();
                Timestamp endTime = coupon.getEndTime();

                if (total >= targetAmount && startTime.before(nowTime) && endTime.after(nowTime)) {
                    total = total - discountAmount;
                }
                couponMapper.deleteCouponUser(couponId, userId);
            }
            int ticketPrice=(int)total/id.size();

            //得到当前电影的活动列表以及与电影无关的活动，验证活动时间并赠送优惠券
            int movieId=scheduleItem.getMovieId();//通过电影票排片信息得到电影票的电影id
            List<Activity> activities=activityMapper.selectActivitiesByMovie(movieId);//通过电影id得到该电影的所有活动
            List<Activity> activities2=activityMapper.selectActivitiesWithoutMovie();
            activities.addAll(activities2);
            activities.stream().forEach(activity -> {
                if(activity.getStartTime().before(nowTime)&&activity.getEndTime().after(nowTime)){
                    couponMapper.insertCouponUser(activity.getCoupon().getId(),userId);
                }
            });

            //购买后更改ticket状态
            id.stream().forEach(ticketId->{
                ticketMapper.updateTicketState(ticketId,1);
                ticketMapper.updateTicketPrice(ticketId,ticketPrice);
            });

            return ResponseVO.buildSuccess();//返回ResponseVO对象，调用成功！
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");  //返回ResponseVO对象，调用此completeTicket(List<Integer> id, int couponId)方法失败
        }
    }

    @Override
    public ResponseVO getBySchedule(int scheduleId) {  //根据排片的id来获取该排片场次的被锁座位和场次信息,即获得该场次已被占、锁座的座位

        try {
            List<Ticket> tickets = ticketMapper.selectTicketsBySchedule(scheduleId);//根据排片id获得该场次的所有已经被用户购买的tickets,存入tickets列表
            ScheduleItem schedule=scheduleService.getScheduleItemById(scheduleId);//根据排片id获取该场次的排片信息，即获得一个ScheduleItem对象
            Hall hall=hallService.getHallById(schedule.getHallId());//根据场次的排片信息ScheduleItem对象的HallId属性来获取一个影厅Hall对象
            int[][] seats=new int[hall.getRow()][hall.getColumn()];//根据该影厅Hall对象的row、column属性来声明初始化所有的座位seats
            tickets.stream().forEach(ticket -> {   //此处是遍历tickets列表中的每一个元素并赋值给临时的ticket变量
                seats[ticket.getRowIndex()][ticket.getColumnIndex()]=1;//通过Ticket对象ticket的rowIndex、columnIndex属性，将被用户购买的座位seat[row][column]设置为1,表示该座位已被锁座（ticket状态可能是未完成0，即可能还未付款）
            });
            ScheduleWithSeatVO scheduleWithSeatVO=new ScheduleWithSeatVO(); //创建一个含有“座位是否被锁座信息”的排片信息ScheduleWithSeatVO对象
            scheduleWithSeatVO.setScheduleItem(schedule);//将schedule对象赋值初始化给scheduleWithSeatVO对象的scheduleItem属性
            scheduleWithSeatVO.setSeats(seats);//将含有是否被锁座信息的座位对象seats赋值给scheduleWithSeatVO对象的seats属性
            return ResponseVO.buildSuccess(scheduleWithSeatVO);//返回ResponseVO对象，调用成功！
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");  //返回ResponseVO对象，调用此getBySchedule(int scheduleId)方法失败
        }
    }

    @Override
    public ResponseVO getTicketByUser(int userId) {
        //根据userid来获取该用户买过的tickets
        try{
            List<Ticket> tickets=ticketMapper.selectTicketByUser(userId);
            List<TicketVO>ticketVOS=new ArrayList<>();
            for(int i=0;i<tickets.size();i++){
                ticketVOS.add(tickets.get(i).getVO());
            }
            return ResponseVO.buildSuccess(ticketVOS);

        }catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }

    }

    @Override
    public ResponseVO getRefundTicketByUser(int userId){
        try {
            List<Refund> refunds=refundMapper.SelectRefunds();
            List<Ticket> tickets=ticketMapper.selectTicketByUser(userId);
            List<TicketWithRefundVO> ticketRefundVO_list=new ArrayList<>();
            for(int i=0;i<tickets.size();i++){
                TicketWithRefundVO ticketWithRefundVO=new TicketWithRefundVO();
                ticketWithRefundVO.setId(tickets.get(i).getId());
                ticketWithRefundVO.setUserId(userId);
                ticketWithRefundVO.setScheduleId(tickets.get(i).getScheduleId());
                ticketWithRefundVO.setColumnIndex(tickets.get(i).getColumnIndex());
                ticketWithRefundVO.setRowIndex(tickets.get(i).getRowIndex());
                ticketWithRefundVO.setPrice(tickets.get(i).getPrice());
                String stateString;
                switch (tickets.get(i).getState()) {
                    case 0:
                        stateString = "未完成";
                        break;
                    case 1:
                        stateString = "已完成";
                        break;
                    case 2:
                        stateString = "已失效";
                        break;
                    default:
                        stateString = "未完成";
                }
                ticketWithRefundVO.setState(stateString);
                ticketWithRefundVO.setTime(tickets.get(i).getTime());
                int refundState=0;
                if(tickets.get(i).getState()==1){
                    for(int j=0;j<refunds.size();j++){
                        System.out.println(ticketWithRefundVO.getPrice());
                        if(ticketWithRefundVO.getTime().before(refunds.get(j).getEndTime())&&ticketWithRefundVO.getTime().after(refunds.get(j).getStartTime())){
                            refundState=1;
                        }
                    }
                }
                ticketWithRefundVO.setRefundState(refundState);
                ticketRefundVO_list.add(ticketWithRefundVO);
            }
            return ResponseVO.buildSuccess(ticketRefundVO_list);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }

    }

    @Override
    @Transactional
    public ResponseVO completeByVIPCard(List<Integer> id, int couponId) {
        try {

            Timestamp nowTime = new Timestamp(System.currentTimeMillis());//当前时间

            //通过电影票id得到其中一张电影票，并的到排片信息和用户id，单价总价
            Ticket ticket=ticketMapper.selectTicketById(id.get(0));
            int scheduleId=ticket.getScheduleId();
            int userId=ticket.getUserId();
            ScheduleItem scheduleItem=scheduleService.getScheduleItemById(scheduleId);
            double fare=scheduleItem.getFare();
            double total=fare*id.size();


            //校验优惠券，得到使用优惠券之后的总金额，删除已使用优惠券
            if(couponId!=0) {
                Coupon coupon = couponMapper.selectById(couponId);//通过优惠券id获得优惠券对象
                double targetAmount = coupon.getTargetAmount();
                double discountAmount = coupon.getDiscountAmount();
                Timestamp startTime = coupon.getStartTime();
                Timestamp endTime = coupon.getEndTime();
                if (total >= targetAmount && startTime.before(nowTime) && endTime.after(nowTime)) {
                    total = total - discountAmount;
                }
                couponMapper.deleteCouponUser(couponId, userId);
            }





            //得到当前电影的活动列表以及与电影无关的活动，验证活动时间并赠送优惠券
            int movieId=scheduleItem.getMovieId();//通过电影票排片信息得到电影票的电影id
            List<Activity> activities=activityMapper.selectActivitiesByMovie(movieId);//通过电影id得到该电影的所有活动
            List<Activity> activities2=activityMapper.selectActivitiesWithoutMovie();
            activities.addAll(activities2);
            activities.stream().forEach(activity -> {
                if(activity.getStartTime().before(nowTime)&&activity.getEndTime().after(nowTime)){
                    couponMapper.insertCouponUser(activity.getCoupon().getId(),userId);
                }
            });

            //对vipcard操作
            int userID=ticketMapper.selectTicketById(id.get(0)).getUserId();
            VIPCard vipCard=vipCardMapper.selectCardByUserId(userID);
            int vipCardId=vipCard.getId();
            int ticketPrice=(int)vipCard.calculate(total)/id.size();
            if(vipCard.getBalance()>total&&vipCard.getJoinDate().before(nowTime)) {
                double balance = vipCard.getBalance() - vipCard.calculate( total);
                vipCardMapper.updateCardBalance(vipCardId,balance);
            }


            //购买后更改ticket状态
            id.stream().forEach(ticketId->{
                ticketMapper.updateTicketState(ticketId,1);
                ticketMapper.updateTicketPrice(ticketId,ticketPrice);
            });
            //反值
            return ResponseVO.buildSuccess("成功vip购票");

        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");  //返回ResponseVO对象，调用此completeTicket(List<Integer> id, int couponId)方法失败
        }

    }

    @Override
    public ResponseVO cancelTicket(List<Integer> id) {  //取消锁座
        try {
            ScheduleWithSeatVO scheduleWithSeatVO=new ScheduleWithSeatVO();
            id.stream().forEach(ticketId ->{
                Ticket ticket=ticketMapper.selectTicketById(ticketId);  //根据ticketiId来获取Ticket对象。
                int scheduleId=ticket.getScheduleId();
                ScheduleItem scheduleItem=scheduleService.getScheduleItemById(scheduleId);
                //ResponseVO vo=new TicketServiceImpl().getBySchedule(scheduleId); //使用该类中的getBySchedule方法，根据排片id获取排片场次的座位信息，查看哪些座位已被锁座
                //ScheduleWithSeatVO scheduleWithSeatVO=ScheduleWithSeatVO(vo.getContent());  //获取到ScheduleWithSeatVO对象的seats属性，然后将待取消锁座的座位seats[][]设为0即可
                //int[][] seats=vo.getContent().seats;
                //Object obj=vo.getContent();
                //ScheduleWithSeatVO(obj).


                //获取该ticket所在影厅的座位情况（seats[][]表明了对应座位是否被锁座，1为被锁座，0为未被锁座）
                int[][] seats=new int[hallService.getHallById(scheduleItem.getHallId()).getRow()][hallService.getHallById(scheduleItem.getHallId()).getColumn()];
                List<Ticket> tickets = ticketMapper.selectTicketsBySchedule(scheduleId);
                tickets.stream().forEach(ticket2 -> {   //此处是遍历tickets列表中的每一个元素并赋值给临时的ticket变量
                    seats[ticket2.getRowIndex()][ticket2.getColumnIndex()]=1;//通过Ticket对象ticket的rowIndex、columnIndex属性，将被用户购买的座位seat[row][column]设置为1,表示该座位已被锁座（ticket状态可能是未完成0，即可能还未付款）
                });
                seats[ticket.getRowIndex()][ticket.getColumnIndex()]=0;//把要取消锁座的座位seat[][]设为0

                scheduleWithSeatVO.setScheduleItem(scheduleItem);
                scheduleWithSeatVO.setSeats(seats);
            });
            return ResponseVO.buildSuccess(scheduleWithSeatVO);//返回ResponseVO对象，调用成功！
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }

    }

    @Override
    public ResponseVO getByPurchase(int purchaseId){
        //根据某笔消费的时间获取对应的电影票信息
        try{
            Purchase purchase=purchaseMapper.selectPurchaseById(purchaseId);
            Timestamp purchaseTime=purchase.getTime();
            List<Ticket> tickets=ticketMapper.selectTicketsByPurchase(purchaseTime);
            List<TicketVO>ticketVOS=new ArrayList<>();
            for(int i=0;i<tickets.size();i++){
                ticketVOS.add(tickets.get(i).getVO());
            }
            return ResponseVO.buildSuccess(ticketVOS);


        }catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO deleteTicket(int id){
        //删除票信息
        try {
            System.out.println(id);
            ticketMapper.deleteTicket(id);
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }

    }

}
