package com.example.cinema.blImpl.statistics;

import com.example.cinema.bl.statistics.StatisticsService;
import com.example.cinema.data.statistics.StatisticsMapper;
import com.example.cinema.po.*;
import com.example.cinema.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



/**
 * @author fjj
 * @date 2019/4/16 1:34 PM
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private StatisticsMapper statisticsMapper;
    @Override
    public ResponseVO getScheduleRateByDate(Date date) {  //获取某日date每部影片排片次数，存入List<MovieScheduleTimeVO>对象中，包办在ResponseVO对象里传给前端
        try{
            Date requireDate = date;
            if(requireDate == null){
                requireDate = new Date();  //new Date()默认是今天
            }

            // System.out.println("In getScheduleRateByDate: requireDate1 is "+requireDate);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            requireDate = simpleDateFormat.parse(simpleDateFormat.format(requireDate));  //格式化日期date对象后将其赋值给requireDate对象
            //System.out.println("In getScheduleRateByDate: requireDate2 is "+requireDate);
            Date nextDate = getNumDayAfterDate(requireDate, 1);  //nextDate定义为date的隔一天
            return ResponseVO.buildSuccess(movieScheduleTimeList2MovieScheduleTimeVOList(statisticsMapper.selectMovieScheduleTimes(requireDate, nextDate)));

        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getTotalBoxOffice() {  // 获取所有每部电影的累计总票房(降序排序，且包含已下架的电影)，结果存入List<MovieTotalBoxOfficeVO>对象，包含在ResponseVO对象中，传给前端
        try {
            return ResponseVO.buildSuccess(movieTotalBoxOfficeList2MovieTotalBoxOfficeVOList(statisticsMapper.selectMovieTotalBoxOffice()));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getAudiencePriceSevenDays() {//获取过去7天内每天的客单价，客单价即某天每个用户的平均购票金额（某天的客单价=当天观众购票所花金额/购票人次数）
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date today = simpleDateFormat.parse(simpleDateFormat.format(new Date())); //获取今天日期，并格式化
            Date startDate = getNumDayAfterDate(today, -6); //获取开始日期，即前七天的第一天
            List<AudiencePriceVO> audiencePriceVOList = new ArrayList<>();  //创建List<AudiencePriceVO>对象，用来存储七天内每天的客单价
            for(int i = 0; i < 7; i++){
                AudiencePriceVO audiencePriceVO = new AudiencePriceVO();   //创建新的AudiencePriceVO类对象
                Date date = getNumDayAfterDate(startDate, i);
                audiencePriceVO.setDate(date);  //设置AudiencePriceVO类对象的date日期属性
                List<AudiencePrice> audiencePriceList = statisticsMapper.selectAudiencePrice(date, getNumDayAfterDate(date, 1));  //获取date那天每个用户的购票金额
                double totalPrice = audiencePriceList.stream().mapToDouble(item -> item.getTotalPrice()).sum();  //totalprice为date那天所有用户购票的总金额
                audiencePriceVO.setPrice(Double.parseDouble(String.format("%.2f", audiencePriceList.size() == 0 ? 0 : totalPrice / audiencePriceList.size()))); //设置AudiencePriceVO类对象的price属性，即date当天平均每个用户的购票金额
                audiencePriceVOList.add(audiencePriceVO);  //将该audiencePriceVO对象（包含了date那天平均每个客户的购票金额）添加到audiencePriceVOList对象中
            }
            return ResponseVO.buildSuccess(audiencePriceVOList);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getMoviePlacingRateByDate(Date date) { //获取某天所有电影的上座率，存入ResponseVO对象里的content对象属性，content实质为List<MoviePlacingRateVO>类
        //要求见接口说明
        try {
            //String string = "2019-04-20 19:00:00";

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date requireDate = date;
            if(requireDate == null){
                requireDate = new Date();
                //requireDate =simpleDateFormat.parse(string);
            }
            requireDate = simpleDateFormat.parse(simpleDateFormat.format(requireDate));  //格式化日期date对象后将其赋值给requireDate对象

            Date nextDate = getNumDayAfterDate(requireDate, 1);  //nextDate定义为date的隔一天
            return ResponseVO.buildSuccess(moviePlacingRateList2MoviePlacingRateVOList(statisticsMapper.selectMoviePlacingRates(requireDate, nextDate)));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }

    }

    @Override
    public ResponseVO getPopularMovies(int days, int movieNum) {//获取最近days天内，最受欢迎的movieNum个电影(可以简单理解为最近days内票房越高的电影越受欢迎)
        //要求见接口说明
        try {
            /*int requireDays = days;
            int requireMovieNum=movieNum;
            if(requireDays == 0){
                requireDays = 7;
            }
            if(requireMovieNum == 0){
                requireMovieNum = 5;
            }*/

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date today = simpleDateFormat.parse(simpleDateFormat.format(new Date())); //获取今天日期，并格式化

            Date endDate=getNumDayAfterDate(today,1);
            Date startDate = getNumDayAfterDate(today, -days+1); //获取开始日期，即前days天的第一天
            List<MoviePeriodBoxOffice> moviePeriodBoxOfficeList=statisticsMapper.selectMoviePeriodBoxOffice(startDate,endDate); //通过statisticsMapper对象获取在此期间内所有电影的总票房

            List<MoviePeriodBoxOffice> mostPopularMoviesList=new ArrayList<>(); //创建新的 List<MoviePeriodBoxOffice>对象mostPopularMoviesList
            for(int i=0;i<movieNum;i++){//将票房前五名的电影放入mostPopularMoviesList对象List中
                mostPopularMoviesList.add(moviePeriodBoxOfficeList.get(i));
            }
            List<MoviePeriodBoxOfficeVO> movieTotalBoxOfficeVOList=moviePeriodBoxOfficeList2MoviePeriodBoxOfficeVOList(mostPopularMoviesList);//将mostPopularMoviesList对象转为前端的List<MoviePeriodBoxOfficeVO>类型
            return ResponseVO.buildSuccess(movieTotalBoxOfficeVOList); //返回包含前端movieTotalBoxOfficeVOList对象的ResponseVO对象
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }

    }

    @Override
    public ResponseVO getMovieBlockHeight(){
        try {
            return ResponseVO.buildSuccess(movieBlockHeightList2MovieBlockHeightVOList(statisticsMapper.selectMovieBlockHeight()));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
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


    //将后端的List<MovieScheduleTime>对象转换为前端的List<MovieScheduleTimeVO>对象，数据内容都不变
    private List<MovieScheduleTimeVO> movieScheduleTimeList2MovieScheduleTimeVOList(List<MovieScheduleTime> movieScheduleTimeList){
        List<MovieScheduleTimeVO> movieScheduleTimeVOList = new ArrayList<>();
        for(MovieScheduleTime movieScheduleTime : movieScheduleTimeList){
            movieScheduleTimeVOList.add(new MovieScheduleTimeVO(movieScheduleTime));
        }
        return movieScheduleTimeVOList;
    }

    //将后端的List<MovieTotalBoxOffice>对象转换为前端的List<MovieTotalBoxOfficeVO>对象，数据内容都不变
    private List<MovieTotalBoxOfficeVO> movieTotalBoxOfficeList2MovieTotalBoxOfficeVOList(List<MovieTotalBoxOffice> movieTotalBoxOfficeList){
        List<MovieTotalBoxOfficeVO> movieTotalBoxOfficeVOList = new ArrayList<>();
        for(MovieTotalBoxOffice movieTotalBoxOffice : movieTotalBoxOfficeList){
            movieTotalBoxOfficeVOList.add(new MovieTotalBoxOfficeVO(movieTotalBoxOffice));
        }
        return movieTotalBoxOfficeVOList;
    }

    //将后端的List<MoviePeriodBoxOffice>对象转换为前端的List<MoviePeriodBoxOfficeVO>对象，数据内容都不变
    private List<MoviePeriodBoxOfficeVO> moviePeriodBoxOfficeList2MoviePeriodBoxOfficeVOList(List<MoviePeriodBoxOffice> moviePeriodBoxOfficeList){
        List<MoviePeriodBoxOfficeVO> moviePeriodBoxOfficeVOList = new ArrayList<>();
        for(MoviePeriodBoxOffice moviePeriodBoxOffice : moviePeriodBoxOfficeList){
            moviePeriodBoxOfficeVOList.add(new MoviePeriodBoxOfficeVO(moviePeriodBoxOffice));
        }
        return moviePeriodBoxOfficeVOList;
    }

    //将后端的List<MoviePlacingRate>对象转换为前端的List<MoviePlacingRateVO>对象，数据内容都不变
    private List<MoviePlacingRateVO> moviePlacingRateList2MoviePlacingRateVOList(List<MoviePlacingRate> moviePlacingRateList){
        List<MoviePlacingRateVO> moviePlacingRateVOList = new ArrayList<>();
        for(MoviePlacingRate moviePlacingRate : moviePlacingRateList){
            moviePlacingRateVOList.add(new MoviePlacingRateVO(moviePlacingRate));
        }
        return moviePlacingRateVOList;
    }

    private List<MovieBlockHeightVO> movieBlockHeightList2MovieBlockHeightVOList(List<MovieBlockHeight> movieBlockHeightList){
        List<MovieBlockHeightVO> movieBlockHeightVOList = new ArrayList<>();
        for(MovieBlockHeight movieBlockHeight : movieBlockHeightList){
            movieBlockHeightVOList.add(new MovieBlockHeightVO(movieBlockHeight));
        }
        return movieBlockHeightVOList;
    }

}
