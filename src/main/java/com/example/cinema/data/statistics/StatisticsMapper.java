package com.example.cinema.data.statistics;

import com.example.cinema.po.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author fjj
 * @date 2019/4/16 1:43 PM
 */
@Mapper
public interface StatisticsMapper {
    /**
     * 查询date日期每部电影的排片次数
     * @param date
     * @return
     */
    List<MovieScheduleTime> selectMovieScheduleTimes(@Param("date") Date date, @Param("nextDate") Date nextDate);

    /**
     * 查询所有电影的总票房（包括已经下架的，降序排列）
     * @return
     */
    List<MovieTotalBoxOffice> selectMovieTotalBoxOffice();

    /**
     * 查询某天每个客户的购票金额
     * @param date
     * @param nextDate
     * @return
     */
    List<AudiencePrice> selectAudiencePrice(@Param("date") Date date, @Param("nextDate") Date nextDate);


    /**
     * 查询该期间内每部电影的总票房
     * @param date
     * @param nextDate
     * @return
     */
    List<MoviePeriodBoxOffice> selectMoviePeriodBoxOffice(@Param("date") Date date, @Param("nextDate") Date nextDate);

    /**
     * 查询获取所有电影某天的上座率
     * @param date
     * @param nextDate
     * @return
     */
    List<MoviePlacingRate> selectMoviePlacingRates(@Param("date") Date date,@Param("nextDate") Date nextDate);

    /**
     * 查询所有电影链区块的高度
     * @return
     */
    List<MovieBlockHeight> selectMovieBlockHeight();

}
