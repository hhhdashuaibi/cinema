$(document).ready(function() {

    //ES6新api 不重复集合 Set
    var selectedMovieIds = new Set();
    var selectedMovieNames = new Set();
    var selectedRefundNames=new Set();

    getAllMovies();

    getRefunds();




    function getRefunds() {
        getRequest(
            '/refund/get',
            function (res) {
                var refunds = res.content;
                renderRefunds(refunds);
                refunds.forEach(function (refund) {
                    $('#refund-name').append("<option value="+ refund.id +">"+refund.name+"</option>");
                    console.log(refund.name)
                });
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    function renderRefunds(refunds) {
        $(".content-activity").empty();
        var activitiesDomStr = "";

        refunds.forEach(function (refund) {
            var movieDomStr = "";
            if(refund.movieList.length){
                refund.movieList.forEach(function (movie) {
                    movieDomStr += "<li class='refund-movie primary-text'>"+movie.name+"</li>";
                });
            }else{
                movieDomStr = "<li class='refund-movie primary-text'>所有热映电影</li>";
            }

            activitiesDomStr+=
                "<div class='refund-container'>" +
                "    <div class='refund-card card'>" +
                "       <div class='refund-line'>" +
                "           <span class='title'>规则名称： "+refund.name+"  </span>" +
                "           <span class='gray-text'>时间限制： "+refund.timeLimit+"天</span>" +
                "           <span class='title'>最低金额："+refund.targetAmount+"元</span>"+
                "       </div>" +
                "       <div class='refund-line'>" +
                "           <span>规则有效时间："+formatDate(new Date(refund.startTime))+"至"+formatDate(new Date(refund.endTime))+"</span>" +
                "       </div>" +
                "       <div class='refund-line'>" +
                "           <span>参与电影：</span>" +
                "               <ul>"+movieDomStr+"</ul>" +
                "       </div>" +
                "    </div>" +
                "</div>";
        });
        $(".content-refund").append(activitiesDomStr);
    }

    function getAllMovies() {
        getRequest(
            '/movie/all/exclude/off',
            function (res) {
                var movieList = res.content;
                $('#refund-movie-input').append("<option value="+ -1 +">所有电影</option>");
                $('#refund-change-movie-input').append("<option value="+ -1 +">所有电影</option>");

                movieList.forEach(function (movie) {
                    $('#refund-movie-input').append("<option value="+ movie.id +">"+movie.name+"</option>");
                    $('#refund-change-movie-input').append("<option value="+ movie.id +">"+movie.name+"</option>");
                });
            },
            function (error) {
                alert(error);
            }
        );
    }


    $('#refund-change-btn').click(function () {
        var form={
            name:$("#refund-name").val(),
            startTime:$("#refund-change-start-date").val(),
            endTime:$("#refund-change-end-date").val(),
            timeLimit:$("#refund-change-timelimit").val(),
            targetAmount:$("#refund-change-target-input").val(),
            movieList: [...selectedMovieIds],
        }

        postRequest(
            '/refund/change',
            form,
                function (res) {
                    if(res.success){
                        getRefunds();
                        $("#refund-changeModal").modal('hide');
                    }else {
                        alert(res.message());
                    }
                },
                function (error) {
                    alert(JSON.stringify(error));
                }

        )

    });



    $("#refund-form-btn").click(function () {
        console.log($("#refund-name-input").val());
        var form = {
            name: $("#refund-name-input").val(),
            startTime: $("#refund-start-date-input").val(),
            endTime: $("#refund-end-date-input").val(),
            timeLimit:$("#refund-time-input").val(),
            targetAmount:$("#refund-target-input").val(),
            movieList: [...selectedMovieIds],
        }

        postRequest(
            '/refund/publish',
            form,
            function (res) {
                if(res.success){
                    getRefunds();
                    $("#refundModal").modal('hide');
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });

    $('#refund-name').change(function () {
        var refundName=$('#refund-name').children('option:selected').text();
        selectedRefundNames.add(refundName);
    })


    $('#refund-movie-input').change(function () {
        var movieId = $('#refund-movie-input').val();
        var movieName = $('#refund-movie-input').children('option:selected').text();
        if(movieId==-1){
            selectedMovieIds.clear();
            selectedMovieNames.clear();
        } else {
            selectedMovieIds.add(movieId);
            selectedMovieNames.add(movieName);
        }
        renderSelectedMovies();
    });

    //渲染选择的参加活动的电影
    function renderSelectedMovies() {
        $('#selected-movies').empty();
        var moviesDomStr = "";
        selectedMovieNames.forEach(function (movieName) {
            moviesDomStr += "<span class='label label-primary'>"+movieName+"</span>";
        });
        $('#selected-movies').append(moviesDomStr);
    }


    $('#refund-change-movie-input').change(function () {
        var movieId = $('#refund-change-movie-input').val();
        var movieName = $('#refund-change-movie-input').children('option:selected').text();
        if(movieId==-1){
            selectedMovieIds.clear();
            selectedMovieNames.clear();
        } else {
            selectedMovieIds.add(movieId);
            selectedMovieNames.add(movieName);
        }
        renderRefundSelectedMovies();
    });

    //渲染选择的参加活动的电影
    function renderRefundSelectedMovies() {
        $('#selected-changed-movies').empty();
        var moviesDomStr = "";
        selectedMovieNames.forEach(function (movieName) {
            moviesDomStr += "<span class='label label-primary'>"+movieName+"</span>";
        });
        $('#selected-changed-movies').append(moviesDomStr);
    }
});