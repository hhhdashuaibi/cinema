$(document).ready(function() {
    if(sessionStorage.getItem('power')>0){
        $("#staffManageModal").hide();
    }
    else {
        $("#staffManageModal").show();
    }

    var canSeeDate = 0;

    console.log("Hiii!!!!")

    getCanSeeDayNum();
    getCinemaHalls();
    getModifyHalls();

    $('#schedule-form-btn').click(function () {

        var hallName=$("#schedule-price-input").val();
        //var hallName=document.getElementById("schedule-hall-input").value;
        //var type=$("#schedule-hall-input").children('option:selected').val();
        var type=$("#schedule-hall-input option:selected").text().substring(0,1);//option:selected
        console.log("hallName is "+hallName);
        console.log("type is "+type);
        //console.log($("#schedule-hall-input option:selected").val());
        //console.log($("#schedule-hall-input option:selected").text());

        //TODO：表单验证，值不能为空
        if((hallName==null||hallName=='')){
            alert("影厅名称或大小未填写！");
            return;
        }

        getRequest(
            '/hall/add?name='+hallName+"&type="+type,
            function (res) {  //成功的回调函数
                if(res.success){
                    console.log("adding hall!!")
                    $("#scheduleModal").modal('hide'); //关闭原先增加影厅的窗口
                    alert("添加影厅成功！");
                } else {
                    alert(res.message);
                }
            },
            function (error) {  //失败的回调函数
                alert(JSON.stringify(error));
            }
        );
        /**
        postRequest(
            '/schedule/add',
            form,
            function (res) {
                if(res.success){
                    getSchedules();
                    $("#scheduleModal").modal('hide');
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
         */
    });

    $('#schedule-form-btn2').click(function () {

        var hallName=$("#schedule-hall-select option:selected").text();
        var newHallName=$("#newHallName").val();
        //var hallName=document.getElementById("schedule-hall-input").value;
        //var type=$("#schedule-hall-input").children('option:selected').val();
        var type=$("#schedule-hall-input2 option:selected").text().substring(0,1);//option:selected
        var id=$("#schedule-hall-select").val();
        console.log("id is "+id);
        console.log("hallName is "+hallName);
        console.log("newHallName is "+newHallName);
        console.log("type is "+type);
        //console.log($("#schedule-hall-input option:selected").val());
        //console.log($("#schedule-hall-input option:selected").text());

        //TODO：表单验证，值不能为空
        if((hallName==null||hallName=='')){
            alert("影厅名称或大小未填写！");
            return;
        }
        if((newHallName==null||newHallName=='')){
            alert("影厅名称或大小未填写！");
            return;
        }
        getRequest(
            '/hall/modify?name='+newHallName+"&type="+type+"&id="+id,
            function (res) {  //成功的回调函数
                if(res.success){
                    $("#scheduleModal2").modal('hide'); //关闭原先增加影厅的窗口
                    alert("修改影厅成功！");
                } else {
                    alert(res.message);
                }
            },
            function (error) {  //失败的回调函数
                alert(JSON.stringify(error));
            }
        );

    });
    $("#schedule-edit-remove-btn").click(function () {
        var r=confirm("确认要删除该影厅吗")
        if (r) {
            var id=$("#schedule-hall-select").val();
            console.log("要删除的影厅id是"+id);
            getRequest(
                '/hall/delete?id='+id,
                function (res) {  //成功的回调函数
                    if(res.success){
                        $("#scheduleModal2").modal('hide'); //关闭原先增加影厅的窗口
                        alert("删除影厅成功！");
                    } else {
                        alert(res.message);
                    }
                },
                function (error) {  //失败的回调函数
                    alert(JSON.stringify(error));
                }
            );
        }
    })

    function getModifyHalls() {
        var halls = [];
        getRequest(
            '/hall/all',
            function (res) {
                halls = res.content;

                hallId = halls[0].id;
                halls.forEach(function (hall) {
                    $('#schedule-hall-select').append("<option value="+ hall.id +">"+hall.name+"</option>");
                });
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    function addHall(){
        var hallName=$("schedule-price-input").val();
        var type='';


        getRequest(
            '/hall/add',
            function (res) {  //成功的回调函数

            },
            function (error) {  //失败的回调函数

            }
        );

    }

    if(sessionStorage.getItem('power')>0){
        $("#staffManageModal").hide();
    }
    else {
        $("#staffManageModal").show();
    }

    getCanSeeDayNum();
    getCinemaHalls();

    function getCinemaHalls() {
        var halls = [];
        getRequest(
            '/hall/all',
            function (res) {
                halls = res.content;
                renderHall(halls);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    function renderHall(halls){
        $('#hall-card').empty();
        var hallDomStr = "";
        halls.forEach(function (hall) {
            var seat = "";
            for(var i =0;i<hall.row;i++){
                var temp = ""
                for(var j =0;j<hall.column;j++){
                    temp+="<div class='cinema-hall-seat'></div>";
                }
                seat+= "<div>"+temp+"</div>";
            }
            var hallDom =
                "<div class='cinema-hall'>" +
                "<div>" +
                "<span class='cinema-hall-name'>"+ hall.name +"</span>" +
                "<span class='cinema-hall-size'>"+ hall.column +'*'+ hall.row +"</span>" +
                "</div>" +
                "<div class='cinema-seat'>" + seat +
                "</div>" +
                "</div>";
            hallDomStr+=hallDom;
        });
        $('#hall-card').append(hallDomStr);
    }

    function getCanSeeDayNum() {
        getRequest(
            '/schedule/view',
            function (res) {
                canSeeDate = res.content;
                $("#can-see-num").text(canSeeDate);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    $('#canview-modify-btn').click(function () {
       $("#canview-modify-btn").hide();
       $("#canview-set-input").val(canSeeDate);
       $("#canview-set-input").show();
       $("#canview-confirm-btn").show();
    });

    $('#canview-confirm-btn').click(function () {
        var dayNum = $("#canview-set-input").val();
        // 验证一下是否为数字
        postRequest(
            '/schedule/view/set',
            {day:dayNum},
            function (res) {
                if(res.success){
                    getCanSeeDayNum();
                    canSeeDate = dayNum;
                    $("#canview-modify-btn").show();
                    $("#canview-set-input").hide();
                    $("#canview-confirm-btn").hide();
                } else{
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    })
});