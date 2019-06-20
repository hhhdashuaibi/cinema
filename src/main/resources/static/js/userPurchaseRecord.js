
$(document).ready(function () {
    getPurchaseList();

    function getPurchaseList() {
        //获取用户所有的purchase
        getRequest(
            '/purchase/gety/' + sessionStorage.getItem('id'),
            function (res) {
                renderPurchaseList(res.content);
            },
            function (error) {
                alert(error);
            });
    }

    // TODO:填空
    function renderPurchaseList(list) {
        $('content-container').empty();
        list.forEach(function (purchaseInfo){
            getRequest(
                '/purchase/get/'+purchaseInfo.id,
                function (res) {
                    //渲染某一条消费记录的信息
                    renderPurchase(res.content);
                },
                function (error) {
                    alert(error);
                }
            )

        });
    }

    function renderPurchase(onePurchaseInfo) {
        var purchaseDomStr = '';
        var formalTime=onePurchaseInfo.time;
        formalTime=formalTime.substring(0,10)+" "+formalTime.substring(11,19);
        purchaseDomStr +=
            "<tr>" +
            "<td>" + formalTime + "</td>" +
            "<td>" + onePurchaseInfo.amount + "</td>" +
            "<td>" + onePurchaseInfo.payMethod + "</td>" +
            "<td>" + "<button class=\"btn btn-primary btn-lg\" data-toggle=\"modal\" data-target=\"#purchaseTicketsModal\" onclick='renderDetail("+onePurchaseInfo.id+")'>查看详情</button>" + "</td>" +
            "</tr>";
        $('.purchase-on-list').append(purchaseDomStr);

    }
});

function renderDetail(id) {
    $('.purchase-detail-on-list').empty();
    getRequest(
        '/ticket/gett/purchase?purchaseId='+id,
        function (res) {
            if(res.success){
                renderPurchaseDetail(res.content);
            }else{
                alert(res.message);
            }
        },
        function (error) {
            alert(error);
        }
    )
}

//渲染消费记录对应的若干张票的信息
function renderTicketDetail(scheduleInfo,ticketInfo){
    var ticketDomStr = '';
    ticketDomStr +=
        "<tr>" +
        "<td>" + scheduleInfo.movieName + "</td>" +
        "<td>" + (ticketInfo.rowIndex + 1) + "排" + (ticketInfo.columnIndex + 1) + "座" + "</td>" +
        "<td>" + formatDate(scheduleInfo.startTime) + " " + formatTime(scheduleInfo.startTime) + "</td>" +
        "<td>" + ticketInfo.price + "</td>"
    "</tr>";
    $('.purchase-detail-on-list').append(ticketDomStr);
}

//渲染某条消费记录的详细信息
function renderPurchaseDetail(list) {
    $('content-container').empty();
    list.forEach(function (ticketInfo){
        getRequest(
            '/schedule/'+ticketInfo.scheduleId,
            function (res) {
                renderTicketDetail(res.content,ticketInfo);
            },
            function (error) {
                alert(error);
            }
        )
    });
}