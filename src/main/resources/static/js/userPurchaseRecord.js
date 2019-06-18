
$(document).ready(function () {
    getPurchaseList();

    function getPurchaseList() {
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
                    renderPurchase(res.content);
                },
                function (error) {
                    alert(error);
                }
            )
            getRequest(
                '/ticket/gett/purchase?purchaseId='+purchaseInfo.id,
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
        });
    }

    function renderPurchase(purchaseInfo) {
        var purchaseDomStr = '';
        var formalTime=purchaseInfo.time;
        formalTime=formalTime.substring(0,10)+" "+formalTime.substring(11,19);
        purchaseDomStr +=
            "<tr>" +
            "<td>" + formalTime + "</td>" +
            "<td>" + purchaseInfo.amount + "</td>" +
            "<td>" + purchaseInfo.payMethod + "</td>" +
            "<td>" + purchaseInfo.purchaseState + "</td>" +
            "<td>" + "<button class=\"btn btn-primary btn-lg\" data-toggle=\"modal\" data-target=\"#ticketsModal\">查看详情</button>" + "</td>" +
            "</tr>";
        $('.purchase-on-list').append(purchaseDomStr);
    }

    function renderPurchaseDetail(list) {
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

    function renderTicketDetail(scheduleInfo,ticketInfo){
        var ticketDomStr = '';
        ticketDomStr +=
            "<tr>" +
            "<td>" + scheduleInfo.movieName + "</td>" +
            "<td>" + (ticketInfo.rowIndex + 1) + "排" + (ticketInfo.columnIndex + 1) + "座" + "</td>" +
            "<td>" + formatDate(scheduleInfo.startTime) + " " + formatTime(scheduleInfo.startTime) + "</td>" +
            "</tr>";
        $('.ticket-detail-on-list').append(ticketDomStr);
    }

});