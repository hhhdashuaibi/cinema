$(document).ready(function () {

    getVIP();
    getCoupon();
    getRechargeList();
});

var isBuyState = true;
var vipCardId=-1;
var vipKind;
var targetAmount;
var discountAmount;
var discpuntPercent=0;
var nowTime =Date.parse(new Date());
function getVIP() {
    getRequest(
        '/vip/' + sessionStorage.getItem('id') + '/get',
        function (res) {
            if (res.success) {
                // 是会员
                $("#member-card").css("visibility", "visible");
                $("#member-card").css("display", "");
                $("#nonmember-card").css("display", "none");
                vipCardId = res.content.id;
                $("#member-kind").text(res.content.kind);
                $("#member-balance").text("¥" + res.content.balance.toFixed(2));
                sessionStorage.setItem("balance",res.content.balance.toFixed(2));
                $("#member-joinDate").text(res.content.joinDate.substring(0, 10));
                $("#member-description").text("满"+res.content.targetAmount+"送"+res.content.discountAmount);
                discpuntPercent=res.content.discountAmount/res.content.targetAmount;
                getAllVipKinds();
            } else {
                // 非会员
                getAllVipKinds();
                $("#member-card").css("display", "none");
                $("#nonmember-card").css("display", "");
            }
        },
        function (error) {
            alert(error);
        });
}
function getAllVipKinds() {
    getRequest(
        '/vip/getVIPKinds',
        function (res) {
            var VIPKinds = res.content;
            renderVIPKinds(VIPKinds);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
}

function renderVIPKinds(VIPKinds) {
    $(".content-BuyVIPKinds").empty();
    var VIPKindsDomStr = "";

    VIPKinds.forEach(function (VIPKind) {

        if(Date.parse(VIPKind.issueTime)<=nowTime && VIPKind.discountAmount/VIPKind.targetAmount>discpuntPercent) {
            VIPKindsDomStr +=
                "<div class='info'>" +
                "<div><b>" + VIPKind.name + "</b></div>" +
                "<div class='price'><b>" + VIPKind.price + "</b>元/张</div>" +
                "<div class= 'description' >" + "满" + VIPKind.targetAmount + "送" + VIPKind.discountAmount + "</div>" +
                "<button onClick='buyClick(this)' data-kind='" + VIPKind.name + "' data-targetAmount='" + VIPKind.targetAmount + "' data-discountAmount='" + VIPKind.discountAmount + "'>立即购买</button>" +
                "</div>";
        }

    });
    $(".content-BuyVIPKinds").append(VIPKindsDomStr);

}

function buyClick(e) {
    clearForm();
    $('#buyModal').modal('show')
    $("#userMember-amount-group").css("display", "none");
    isBuyState = true;
    vipKind=e.getAttribute("data-kind")
    targetAmount=e.getAttribute("data-targetAmount")
    discountAmount=e.getAttribute("data-discountAmount")
}

function chargeClick() {
    clearForm();
    $('#buyModal').modal('show')
    $("#userMember-amount-group").css("display", "");
    isBuyState = false;
}

function clearForm() {
    $('#userMember-form input').val("");
    $('#userMember-form .form-group').removeClass("has-error");
    $('#userMember-form p').css("visibility", "hidden");
}

function confirmCommit() {
    if (validateForm()) {
        if ($('#userMember-cardNum').val() === "123123123" && $('#userMember-cardPwd').val() === "123123") {
            if (isBuyState) {
                if(vipCardId>=0){
                    postRequest(
                        '/vip/updateVIPCardByUserId?userId='+sessionStorage.getItem('id')+'&kind='+vipKind+'&targetAmount='+targetAmount+'&discountAmount='+discountAmount,
                        null,
                        function (res) {
                            $('#buyModal').modal('hide');
                            alert("购买会员卡成功啦");
                            getVIP();
                        },
                        function (error) {
                            alert(error);
                        }
                        );
                }else {
                    postRequest(
                        '/vip/add',
                        {
                            userId: sessionStorage.getItem('id'),
                            kind: vipKind,
                            targetAmount: targetAmount,
                            discountAmount: discountAmount
                        },
                        function (res) {
                            $('#buyModal').modal('hide');
                            alert("购买会员卡成功");
                            getVIP();
                        },
                        function (error) {
                            alert(error);
                        });
                }
            } else {
                var payMoney=parseInt($('#userMember-amount').val());
                postRequest(
                    '/vip/charge',
                    {vipId: vipCardId, amount: payMoney},
                    function (res) {
                        $('#buyModal').modal('hide');
                        alert("充值成功");
                        getVIP();
                    },
                    function (error) {
                        alert(error);
                    });
                postRequest(
                    '/recharge/create',
                    getRechargeItem(payMoney),
                    function (res) {
                        //alert("充值成功");
                    },
                    function (error) {
                        alert("充值失败");
                    }
                )
            }
        } else {
            alert("银行卡号或密码错误");
        }
    }
}

function validateForm() {
    var isValidate = true;
    if (!$('#userMember-cardNum').val()) {
        isValidate = false;
        $('#userMember-cardNum').parent('.form-group').addClass('has-error');
        $('#userMember-cardNum-error').css("visibility", "visible");
    }
    if (!$('#userMember-cardPwd').val()) {
        isValidate = false;
        $('#userMember-cardPwd').parent('.form-group').addClass('has-error');
        $('#userMember-cardPwd-error').css("visibility", "visible");
    }
    if (!isBuyState && (!$('#userMember-amount').val() || parseInt($('#userMember-amount').val()) <= 0)) {
        isValidate = false;
        $('#userMember-amount').parent('.form-group').addClass('has-error');
        $('#userMember-amount-error').css("visibility", "visible");
    }
    return isValidate;
}

function getCoupon() {
    getRequest(
        '/coupon/' + sessionStorage.getItem('id') + '/get',
        function (res) {
            if (res.success) {
                var couponList = res.content;
                var couponListContent = '';
                for (let coupon of couponList) {
                    couponListContent += '<div class="col-md-6 coupon-wrapper"><div class="coupon"><div class="content">' +
                        '<div class="col-md-8 left">' +
                        '<div class="name">' +
                        coupon.name +
                        '</div>' +
                        '<div class="description">' +
                        coupon.description +
                        '</div>' +
                        '<div class="price">' +
                        '满' + coupon.targetAmount + '减' + coupon.discountAmount +
                        '</div>' +
                        '</div>' +
                        '<div class="col-md-4 right">' +
                        '<div>有效日期：</div>' +
                        '<div>' + formatDate(coupon.startTime) + ' ~ ' + formatDate(coupon.endTime) + '</div>' +
                        '</div></div></div></div>'
                }
                $('#coupon-list').html(couponListContent);

            }
        },
        function (error) {
            alert(error);
        });
}

function formatDate(date) {
    return date.substring(5, 10).replace("-", ".");
}

//渲染用户所有的充值记录
function getRechargeList() {
    getRequest(
        '/recharge/gety/' + sessionStorage.getItem('id'),//根据用户id获取充值记录
        function (res) {
                renderRechargeList(res.content);
        },
        function (error) {
            alert(error);
        });
}

//渲染充值记录
function renderRechargeList(list) {
    $('content-container').empty();
    list.forEach(function (rechargeInfo){
        getRequest(
            '/recharge/get/'+rechargeInfo.id,
            function (res) {
                renderRecharge(res.content);
            },
            function (error) {
                alert(error);
            }
        )
    });
}

//渲染充值记录详细信息
function renderRecharge(rechargeInfo) {
    var rechargeDomStr = '';
    var formalTime=rechargeInfo.rechargeTime;
    formalTime=formalTime.substring(0,10)+" "+formalTime.substring(11,19);
    rechargeDomStr +=
        "<tr>" +
        "<td>" + formalTime + "</td>" +
        "<td>" + rechargeInfo.payAmount + "</td>" +
        "<td>" + rechargeInfo.actualAmount + "</td>" +
        "<td>" + rechargeInfo.afterCardBalance + "</td>" +
        "</tr>";
    $('.recharge-on-list').append(rechargeDomStr);
}

function getRechargeItem(payMoney) {
    return{
        userId:sessionStorage.getItem("id"),
        payAmount:payMoney,
    }
}
