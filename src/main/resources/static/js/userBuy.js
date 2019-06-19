
$(document).ready(function () {
    getMovieList();

    function getMovieList() {
        getRequest(
            '/ticket/get/show/' + sessionStorage.getItem('id'),
            function (res) {
                renderTicketList(res.content);
            },
            function (error) {
                alert(error);
            });
    }



    // TODO:填空
    function renderTicketList(list) {
        $('content-container').empty();
        list.forEach(function (ticketInfo){
            getRequest(
                '/schedule/'+ticketInfo.scheduleId,
                function (res) {
                    renderTicket(res.content,ticketInfo);
                },
                function (error) {
                    alert(error);
                }
            )
        });

    }

    function renderTicket(schedule,ticketInfo) {
        var ticketDomStr='';
        ticketDomStr +=
            "<tr>" +
            "<td>" +schedule.movieName+"</td>" +
            "<td>"+schedule.hallName +"</td>"+
            "<td>"+(ticketInfo.rowIndex+1)+"排"+(ticketInfo.columnIndex+1)+"座" +"</td>"+
            "<td>"+JSON.stringify(schedule.startTime).substring(1,11)+" "+JSON.stringify(schedule.startTime).substring(12,20)+"</td>"+
            "<td>"+JSON.stringify(schedule.endTime).substring(1,11)+" "+JSON.stringify(schedule.endTime).substring(12,20) +"</td>"+
            "<td>"+ticketInfo.state+"</td>";
        if(ticketInfo.refundState){
            ticketDomStr+="<td><button type='button' class='btn btn-primary' data-backdrop= 'static' data-toggle='modal' data-target='#toRefundModal' data-index="+ticketInfo.id+"><i class='icon-plus-sign'></i>退票</button>";
        }else {
            ticketDomStr+="<td>无法退票</td>";
        }
        ticketDomStr+="</tr>";

        $('#ticket-table tbody').append(ticketDomStr);
    }

    $('#toRefundModal').on('show.bs.modal', function (e) {
        var button = $(e.relatedTarget);
        buttonLabel = parseInt(button.data("index"));


        $('#refund-rules-details').append("<text>尊敬的用户您好,我们将退还票价</text>");

    });
    
    $('#user-refund-btn').click(function () {
        if ($('#refund-account-input').val() === "123123123" && $('#refund-password-input').val() === "123123") {
            deleteTicket(buttonLabel);

        } else {
            alert("银行卡号或密码错误");
        }
    })


    function deleteTicket(ticket) {
        postRequest(
            '/ticket/delete?ticketId='+ ticket,
            {},
            function (res) {
                console.log("退票成功");
                $('#toRefundModal').modal('hide');
                ticketList = [];
                buttonLabel = 0;
                location.reload();
            },
            function (err) {
                console.log(JSON.stringify(err));
            }
        )
        getRequest(
            '/purchase/gett?ticketId='+ ticket,
            function (res) {
                postRequest(
                    '/purchase/cancle?purchaseId='+res.id,
                    null,
                    function (res) {
                        alert("订单已失效");
                    },
                    function (error) {
                        alert(error)
                    }
                )
            },
            function (error) {
                alert(error)
            }
        )

    }


});