// JavaScript Document
var i=0;
var timer;
$(document).ready(function() {
    "use strict";
    /*当鼠标移到图片上时，隐藏和显示箭头按钮
    $("#igs").hover(function(){
        $(".btn").show();
    },function(){
        $(".btn").hide();
    });
    */

    $(".ig").eq(0).show().siblings().hide();
    //选择器获取的是class=“ig”的第一个对象，eq是索引功能，eq（0）是取第一个,第一个显示，其余兄弟元素隐藏

    AutoShow();
    initTopSell();
    initTopPopular();
    initMovieLists();

    //鼠标移到图片下标索引时，显示对应的图片
    $(".tab").hover(function(){  //hover函数，前一个参数是当鼠标移动到此元素上要执行的函数，后一个参数是当鼠标移开此元素时要执行的函数
        i=$(this).index();    //获取鼠标放到的元素的索引
        Show();
        clearInterval(timer);    //当鼠标放到此元素上时清除自动播放，清除轮播
    },function(){
        AutoShow();          //当鼠标移开此元素后让轮播继续自动进行
    });


    //单击左按钮图片向左轮播一次，后自动轮播
    $(".btn1").click(function(){
        clearInterval(timer);   //先停止轮播
        if(i==0){
            i=5;
        }
        else{
            i--;
        }
        Show();      //调用显示函数
        AutoShow();  //然后调用自动轮播函数

    });

    //单击右按钮图片向右轮播一次，后自动轮播
    $(".btn2").click(function(){
        clearInterval(timer);   //先停止轮播
        if(i==5){
            i=0;
        }
        else{
            i++;
        }
        Show();      //调用显示函数
        AutoShow();  //然后调用自动轮播函数

    });



});

function AutoShow(){   //自动轮播函数
    "use strict";
    timer=setInterval(function(){          //此处设置是让轮播图可以自动播放，由第一张图片开始，每隔四秒往下播放下一张
        i++;
        if(i==6){
            i=0;
        }

        Show();

    },4000);

}

function Show(){    //显示图片函数
    "use strict";
    $(".ig").eq(i).fadeIn(300).siblings().fadeOut(300);  //此处设置的是渐变出现和渐变隐藏，渐变的时间是0.3秒
    $(".tab").eq(i).addClass("bgcolor").siblings().removeClass("bgcolor"); //给选中的元素添加样式，为div颜色的变化
}

function initTopSell(){
    getRequest(
        '/statistics/boxOffice/total',
        function (res) {
            var movies=res.content;
            movies.forEach(function (movie) {
                var box=movie.boxOffice;
                if(box==null){
                    box=0;
                }
                $(".top-sell-list").append("<div class=\"statistic-item\">\n" +
                    "<span>"+movie.name+"</span>\n" +
                    "<span class=\"error-text\">"+box+"</span>\n" +
                    "</div>");
            });
        },
        function (error) {
            alert(JSON.stringify(error));
        });
}

function initTopPopular(){
    var days=7;
    var movieNum=5;
    getRequest(
        '/statistics/populars/movie?days='+days+'&movieNum='+movieNum,
        function (res) {
            var movies=res.content;
            movies.forEach(function (movie) {
                var box=movie.boxOffice;
                if(box==null){
                    box=0;
                }
                $(".top-expectation-list").append("<div class=\"statistic-item\">\n" +
                    "<span>"+movie.name+"</span>\n" +
                    "<span class=\"error-text\">"+box+"</span>\n" +
                    "</div>");
            });
        },
        function (error) {
            alert(JSON.stringify(error));
        });
}

function initMovieLists(){
    getRequest(
        '/movie/all/exclude/off',
        function (res) {
            console.log(res.content);
            var movies=res.content;

            movies.forEach(function (movie) {
                var like=movie.likeCount;
                if(like==null){
                    like=0;
                }
                $(".movie-off-list").append("<li itemtype=\"http://schema.org/Movie\" itemscope=\"\">\n" +
                    "                    <a href='/user/movieDetail?id="+ movie.id +"'>\n" +
                    "                        <div class=\"d1\">\n" +
                    "                            <img class=\"lazy\" style=\"display: inline;\" alt="+movie.name+" src="+movie.posterUrl+" itemprop=\"image\" data-original=\"http://img.movie.iecity.com/Upload/Movie/201905/23/20190523130942625.jpg\">\n" +
                    "                        </div>\n" +
                    "                        <div class=\"MovieTitle\">\n" +
                    "                            <h3 class=\"color-blue\" itemprop=\"name\">"+movie.name+"</h3>\n" +
                    "                            <div class=\"pf\">"+like+"</div>\n" +
                    "                        </div>\n" +
                    "                        <div class=\"MovieDetail\">"+movie.type+"</div>\n" +
                    "                    </a>\n" +
                    "                </li>");
            });


                /*$(".movie-off-list").append("<li itemtype=\"\" itemscope=\"\">\n" +
                    "                    <div class=\"d1\">\n" +
                    "                        <a href=\"film1041021101151181229756534853564948.html\" target=\"_blank\"><img class=\"lazy\" style=\"display: inline;\" alt="+movie.name+" src="+movie.posterUrl+" itemprop=\"image\" data-original=\"http://img.movie.iecity.com/Upload/Movie/201905/23/20190523130942625.jpg\"></a>\n" +
                    "                    </div>\n" +
                    "                    <div class=\"MovieTitle\">\n" +
                    "                        <a href='/user/movieDetail?id=\"+ movie.id +\"'>\n" +
                    "                            <h3 class=\"color-blue\" itemprop=\"name\">"+movie.name+"</h3>\n" +
                    "                        </a>\n" +
                    "                        <div class=\"pf\">"+like+"</div>\n" +
                    "                    </div>\n" +
                    "                    <div class=\"MovieDetail\">"+movie.type+"</div>\n" +
                    "                </li>");
            });*/
        },
        function (error) {
            alert(JSON.stringify(error));
        });
}
