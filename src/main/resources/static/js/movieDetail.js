$(document).ready(function(){

    var movieId = parseInt(window.location.href.split('?')[1].split('&')[0].split('=')[1]);
    var userId = sessionStorage.getItem('id');
    var isLike = false;

    getMovie();
    if(sessionStorage.getItem('role') === 'admin')
        getMovieLikeChart();

    function getMovieLikeChart() {
       getRequest(
           '/movie/' + movieId + '/like/date',
           function(res){
               var data = res.content,
                    dateArray = [],
                    numberArray = [];
               data.forEach(function (item) {
                   dateArray.push(item.likeTime);
                   numberArray.push(item.likeNum);
               });

               var myChart = echarts.init($("#like-date-chart")[0]);

               // 指定图表的配置项和数据
               var option = {
                   title: {
                       text: '想看人数变化表'
                   },
                   xAxis: {
                       type: 'category',
                       data: dateArray
                   },
                   yAxis: {
                       type: 'value'
                   },
                   series: [{
                       data: numberArray,
                       type: 'line'
                   }]
               };

               // 使用刚指定的配置项和数据显示图表。
               myChart.setOption(option);
           },
           function (error) {
               alert(error);
           }
       );
    }

    //表单输入是否有效
    function validateMovieForm(data) {
        var isValidate = true;
        if(!data.name) {
            isValidate = false;
            $('#movie-name-input').parent('.form-group').addClass('has-error');
        }
        if(!data.posterUrl) {
            isValidate = false;
            $('#movie-img-input').parent('.form-group').addClass('has-error');
        }
        if(!data.startDate) {
            isValidate = false;
            $('#movie-date-input').parent('.form-group').addClass('has-error');
        }
        return isValidate;
    }

    function getMovie() {
        getRequest(
            '/movie/'+movieId + '/' + userId,
            function(res){
                var data = res.content;
                isLike = data.islike;
                repaintMovieDetail(data);
                repaintMovieForm(data);
            },
            function (error) {
                alert(error);
            }
        );
    }

    //在电影详情页面显示已存在的电影信息
    function repaintMovieDetail(movie) {
        !isLike ? $('.icon-heart').removeClass('error-text') : $('.icon-heart').addClass('error-text');
        $('#like-btn span').text(isLike ? ' 已想看' : ' 想 看');
        $('#movie-img').attr('src',movie.posterUrl);
        $('#movie-name').text(movie.name);
        $('#order-movie-name').text(movie.name);
        $('#movie-description').text(movie.description);
        $('#movie-startDate').text(new Date(movie.startDate).toLocaleDateString());
        $('#movie-type').text(movie.type);
        $('#movie-country').text(movie.country);


        $('#movie-language').text(movie.language);
        $('#movie-director').text(movie.director);
        $('#movie-starring').text(movie.starring);
        $('#movie-writer').text(movie.screenWriter);
        $('#movie-length').text(movie.length);
    }

    //在表单中显示已存在的电影信息
    function repaintMovieForm(movie) {
        $("#movie-edit-name-input").val(movie.name);
        $("#movie-edit-date-input").val(movie.startDate.slice(0,10));
        $("#movie-edit-img-input").val(movie.posterUrl);
        $("#movie-edit-description-input").val(movie.description);
        $("#movie-edit-type-input").val(movie.type);
        $("#movie-edit-length-input").val(movie.length);
        $("#movie-edit-country-input").val(movie.country);
        $("#movie-edit-language-input").val(movie.language);
        $("#movie-edit-director-input").val(movie.director);
        $("#movie-edit-star-input").val(movie.starring);
        $("#movie-edit-writer-input").val(movie.screenWriter);
    }

    //获取新输入的电影信息
    function getMovieForm(){
        return{
            id:movieId,
            name: $("#movie-edit-name-input").val(),
            posterUrl: $("#movie-edit-img-input").val(),
            director: $("#movie-edit-director-input").val(),
            screenWriter: $("#movie-edit-writer-input").val(),
            starring: $("#movie-edit-star-input").val(),
            type: $("#movie-edit-type-input").val(),
            country: $("#movie-edit-country-input").val(),
            language: $("#movie-edit-language-input").val(),
            startDate: $("#movie-edit-date-input").val(),
            length: $("#movie-edit-length-input").val(),
            description: $("#movie-edit-description-input").val()
        }
    }

    // user界面才有
    $('#like-btn').click(function () {
        var url = isLike ?'/movie/'+ movieId +'/unlike?userId='+ userId :'/movie/'+ movieId +'/like?userId='+ userId;
        postRequest(
             url,
            null,
            function (res) {
                 isLike = !isLike;
                getMovie();
            },
            function (error) {
                alert(error);
            });
    });


    // admin界面才有
    //修改电影


    $("#movie-edit-form-btn").click(function () {
        //TODO
        //修改时需要在对应html文件添加表单然后获取用户输入，提交给后端，还要对用户输入进行验证。（可参照添加电影&添加排片&修改排片）
        var newForm = getMovieForm();
        //表单验证
        if(!validateMovieForm(newForm)){
            return;
        }
        postRequest(
            '/movie/update',
            newForm,
            function (res) {
                if(res.success){
                    getMovie();
                    $("#movieModal").modal('hide');
                } else{
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });

    //下架电影
    $("#delete-btn").click(function () {
        //TODO
        //下架需要一个确认提示框，下架之后要对用户有所提示
        var r=confirm("确认要下架该影片吗")
        if (r) {
            deleteRequest(
                '/movie/off/batch',
                {movieIdList:[movieId]},
                function (res) {
                    if(res.success){
                        getMovie();
                        $("#movieModal").modal('hide');
                    } else{
                        alert(res.message);
                    }
                },
                function (error) {
                    alert(JSON.stringify(error));
                }
            );
            alert("电影下架成功")
        }
        else{
            alert("取消下架")
        }
    });

});