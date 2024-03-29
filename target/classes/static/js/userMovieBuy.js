var selectedSeats = []
var scheduleId;
var amount;
var order = {ticketId: [], couponId: 0};
var coupons = [];
var isVIP = false;
var useVIP = true;
var payTime=new Date().getTime();

$(document).ready(function () {
    scheduleId = parseInt(window.location.href.split('?')[1].split('&')[1].split('=')[1]);

    getInfo();

    function getInfo() {
        getRequest(
            '/ticket/get/occupiedSeats?scheduleId=' + scheduleId,
            function (res) {
                if (res.success) {
                    renderSchedule(res.content.scheduleItem, res.content.seats);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }
});

function renderSchedule(schedule, seats) {
    $('#schedule-hall-name').text(schedule.hallName);
    $('#order-schedule-hall-name').text(schedule.hallName);
    $('#schedule-fare').text(schedule.fare.toFixed(2));
    $('#order-schedule-fare').text(schedule.fare.toFixed(2));
    $('#schedule-time').text(schedule.startTime.substring(5, 7) + "月" + schedule.startTime.substring(8, 10) + "日 " + schedule.startTime.substring(11, 16) + "场");
    $('#order-schedule-time').text(schedule.startTime.substring(5, 7) + "月" + schedule.startTime.substring(8, 10) + "日 " + schedule.startTime.substring(11, 16) + "场");

    var hallDomStr = "";
    var seat = "";
    for (var i = 0; i < seats.length; i++) {
        var temp = ""
        for (var j = 0; j < seats[i].length; j++) {
            var id = "seat" + i + j

            if (seats[i][j] == 0) {
                // 未选
                temp += "<button class='cinema-hall-seat-choose' id='" + id + "' onclick='seatClick(\"" + id + "\"," + i + "," + j + ")'></button>";
            } else {
                // 已选中
                temp += "<button class='cinema-hall-seat-lock'></button>";
            }
        }
        seat += "<div>" + temp + "</div>";
    }
    var hallDom =
        "<div class='cinema-hall'>" +
        "<div>" +
        "<span class='cinema-hall-name'>" + schedule.hallName + "</span>" +
        "<span class='cinema-hall-size'>" + seats.length + '*' + seats[0].length + "</span>" +
        "</div>" +
        "<div class='cinema-seat'>" + seat +
        "</div>" +
        "</div>";
    hallDomStr += hallDom;

    $('#hall-card').html(hallDomStr);
}

function seatClick(id, i, j) {
    let seat = $('#' + id);
    if (seat.hasClass("cinema-hall-seat-choose")) {
        seat.removeClass("cinema-hall-seat-choose");
        seat.addClass("cinema-hall-seat");

        selectedSeats[selectedSeats.length] = [i, j]
    } else {
        seat.removeClass("cinema-hall-seat");
        seat.addClass("cinema-hall-seat-choose");

        selectedSeats = selectedSeats.filter(function (value) {
            return value[0] != i || value[1] != j;
        })
    }

    selectedSeats.sort(function (x, y) {
        var res = x[0] - y[0];
        return res === 0 ? x[1] - y[1] : res;
    });

    let seatDetailStr = "";
    if (selectedSeats.length == 0) {
        seatDetailStr += "还未选择座位"
        $('#order-confirm-btn').attr("disabled", "disabled")
    } else {
        for (let seatLoc of selectedSeats) {
            seatDetailStr += "<span>" + (seatLoc[0] + 1) + "排" + (seatLoc[1] + 1) + "座</span>";
        }
        $('#order-confirm-btn').removeAttr("disabled");
    }
    $('#seat-detail').html(seatDetailStr);
}

function orderConfirmClick() {
    $('#seat-state').css("display", "none");
    $('#order-state').css("display", "");

    var seats=new Array();
    for(let selectedSeat of selectedSeats){
        seats[seats.length]={
            columnIndex:selectedSeat[1],
            rowIndex:selectedSeat[0]
        }
    }

    var orderInfo={};
    var ticketVOList=new Array();
    $.ajax({
        type: 'POST',
        url:'/ticket/lockSeat',
        async:false,
        data:JSON.stringify({
            userId:sessionStorage.getItem('id'),
            scheduleId:scheduleId,
            seats:seats
        }),
        contentType: 'application/json',
        processData: false,
        success:function (res) {
            res.content.forEach(function (currentValue) {
                ticketVOList.push(currentValue);
            })
            orderInfo["ticketVOList"] = ticketVOList;
        },
        error:function (error) {
            alert("添加电影票出错");
        }})

    var fare;
    $.ajax({
        url:'/ticket/get/occupiedSeats?scheduleId=' +scheduleId,
        async:false,
        success:function (res) {
            if (res.success) {
                fare=res.content.scheduleItem.fare.toFixed(2);
            }
        },
        error:function (error) {
            alert(JSON.stringify(error));
        }
    });

    var total=fare*ticketVOList.length;
    orderInfo["total"]=total;

    var coupons=new Array();
    $.ajax({
        url:'/coupon/'+sessionStorage.getItem('id') + '/get',
        async:false,
        success:function (res) {
            res.content.forEach(function (currentValue) {
                coupons.push(currentValue);
            })
            orderInfo["coupons"] = coupons;
        },
        error:function (error) {
            alert(JSON.stringify(error));
        }
    });

    var activities=new Array();
    $.ajax({
        url:'/activity/get',
        async:false,
        success:function (res) {
            res.content.forEach(function (currentValue) {
                //delete res.currentValue["vo"];
                activities.push(currentValue);

            })
            orderInfo["activities"] = activities;
        },
        error:function (error) {
            alert(JSON.stringify(error));
        }
    });

    renderOrder(orderInfo);

    getRequest(
        '/vip/' + sessionStorage.getItem('id') + '/get',
        function (res) {
            isVIP = res.success;
            useVIP = res.success;
            if (isVIP) {
                $('#member-balance').html("<div><b>会员卡余额：</b>" + res.content.balance.toFixed(2) + "元</div>");
            } else {
                $("#member-pay").css("display", "none");
                $("#nonmember-pay").addClass("active");

                $("#modal-body-member").css("display", "none");
                $("#modal-body-nonmember").css("display", "");
            }
        },
        function (error) {
            alert(error);
        });
}

function switchPay(type) {
    useVIP = (type == 0);
    if (type == 0) {
        $("#member-pay").addClass("active");
        $("#nonmember-pay").removeClass("active");

        $("#modal-body-member").css("display", "");
        $("#modal-body-nonmember").css("display", "none");
    } else {
        $("#member-pay").removeClass("active");
        $("#nonmember-pay").addClass("active");

        $("#modal-body-member").css("display", "none");
        $("#modal-body-nonmember").css("display", "");
    }
}

function renderOrder(orderInfo) {
    var ticketStr = "<div>" + selectedSeats.length + "张</div>";
    for (let ticketInfo of orderInfo.ticketVOList) {
        ticketStr += "<div>" + (ticketInfo.rowIndex + 1) + "排" + (ticketInfo.columnIndex + 1) + "座</div>";
        order.ticketId.push(ticketInfo.id);
    }
    $('#order-tickets').html(ticketStr);

    var total = orderInfo.total.toFixed(2);
    $('#order-total').text(total);
    $('#order-footer-total').text("总金额： ¥" + total);


    var couponTicketStr = "";
    if (orderInfo.coupons.length == 0) {
        $('#order-discount').text("优惠金额：无");
        $('#order-actual-total').text(" ¥" + total);
        $('#pay-amount').html("<div><b>金额：</b>" + total + "元</div>");
    } else {
        coupons = orderInfo.coupons;
        for (let coupon of coupons) {
            couponTicketStr += "<option>满" + coupon.targetAmount + "减" + coupon.discountAmount + "</option>"
        }
        $('#order-coupons').html(couponTicketStr);
        changeCoupon(0);
    }
}

function changeCoupon(couponIndex) {
    order.couponId = coupons[couponIndex].id;
    $('#order-discount').text("优惠金额： ¥" + coupons[couponIndex].discountAmount.toFixed(2));
    var actualTotal = (parseFloat($('#order-total').text()) - parseFloat(coupons[couponIndex].discountAmount)).toFixed(2);
    $('#order-actual-total').text(" ¥" + actualTotal);
    $('#pay-amount').html("<div><b>金额：</b>" + actualTotal + "元</div>");
}

function payConfirmClick() {
    if (useVIP) {
        postPayRequest();
    } else {
        if (validateForm()) {
            if ($('#userBuy-cardNum').val() === "123123123" && $('#userBuy-cardPwd').val() === "123123") {
                postPayRequest();
            } else {
                alert("银行卡号或密码错误");
            }
        }
    }
}

// TODO:填空
function postPayRequest() {
    $('#order-state').css("display", "none");
    $('#success-state').css("display", "");
    $('#buyModal').modal('hide');
    payTime=Math.round(new Date().getTime()/1000);
    if (useVIP) {
        postRequest(
            '/ticket/vip/buy?ticketId=' + order.ticketId + '&couponId=' + order.couponId,
            null,
            function (res) {
                //alert("购票成功");
            },
            function (error) {
                alert("购票失败");
            }
        )
        var form = getPurchaseItem(1,0)
        //增加消费记录,下同
        postRequest(
            '/purchase/create',
            form,
            function (res) {
                //alert("购票成功");
                getRequest(
                    //获取刚才增加的消费记录的id,下同
                    '/purchase/getPurchaseId',
                    function(res){
                        postRequest(
                            '/purchase/addPurchaseAndTicket?purchaseId='+res.content+'&ticketId='+order.ticketId,//对应地添加消费记录和电影票
                            null,
                            function (){
                                //alert("关联电影票与消费记录成功");
                            },
                            function (error) {
                                alert(error);
                            }
                        );
                    },
                    function (error) {
                        alert(error)
                    }
                )
            },
            function (error) {
                alert("购票失败");
            }
        )
        //确认支付后更新用户的总消费,下同
        postRequest(
            '/updateTotalPurchase?purchaseAmount='+$("#order-actual-total").text().substring(2)+'&userId='+sessionStorage.getItem("id"),
            null,
            function (res) {
                //alert("购票成功");
            },
            function (error) {
                alert("购票失败");
            }
        )
        }else{
        postRequest(
            '/ticket/buy?ticketId=' + order.ticketId + '&couponId=' + order.couponId,
            null,
            function (res) {
                //alert("购票成功");
            },
            function (error) {
                alert("购票失败");
            }
        )
        postRequest(
            '/purchase/create',
            getPurchaseItem(0,0),
            function (res) {
                //alert("购票成功");
                getRequest(
                    '/purchase/getPurchaseId',
                    function(res){
                        postRequest(
                            '/purchase/addPurchaseAndTicket?purchaseId='+res.content+'&ticketId='+order.ticketId,
                            null,
                            function (){
                                //alert("关联电影票与消费记录成功");
                            },
                            function (error) {
                                alert(error);
                            }
                        );
                    },
                    function (error) {
                        alert(error)
                    }
                )
            },
            function (error) {
                alert("购票失败");
            }
        )
    }

}
function getPurchaseItem(payMethod,purchaseState){
    return{
        userId:sessionStorage.getItem("id"),
        amount:$("#order-actual-total").text().substring(2),
        payMethod:payMethod,
        purchaseState:purchaseState,
    }
}

function validateForm() {
    var isValidate = true;
    if (!$('#userBuy-cardNum').val()) {
        isValidate = false;
        $('#userBuy-cardNum').parent('.form-group').addClass('has-error');
        $('#userBuy-cardNum-error').css("visibility", "visible");
    }
    if (!$('#userBuy-cardPwd').val()) {
        isValidate = false;
        $('#userBuy-cardPwd').parent('.form-group').addClass('has-error');
        $('#userBuy-cardPwd-error').css("visibility", "visible");
    }
    return isValidate;
}