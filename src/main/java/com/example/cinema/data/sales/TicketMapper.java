package com.example.cinema.data.sales;

import com.example.cinema.po.Ticket;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.scheduling.annotation.Scheduled;


import java.sql.Timestamp;

import java.util.List;

/**
 * Created by liying on 2019/4/16.
 */
@Mapper

public interface TicketMapper {

    int insertTicket(Ticket ticket);

    int insertTickets(List<Ticket> tickets);

    void deleteTicket(int ticketId);

    void updateTicketState(@Param("ticketId") int ticketId, @Param("state") int state);



    void updateTicketPrice(@Param("ticketId") int ticketId,@Param("price") int price);




    List<Ticket> selectTicketsBySchedule(int scheduleId);

    Ticket selectTicketByScheduleIdAndSeat(@Param("scheduleId") int scheduleId, @Param("column") int columnIndex, @Param("row") int rowIndex);

    Ticket selectTicketById(int id);

    List<Ticket> selectTicketByUser(int userId);


    List<Ticket> selectTicketsByPurchase(Timestamp purchaseTime);


    @Scheduled(cron = "0/1 * * * * ?")
    void cleanExpiredTicket();
}

