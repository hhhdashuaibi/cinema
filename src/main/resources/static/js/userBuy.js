
$(document).ready(function () {
    getMovieList();

    function getMovieList() {
        getRequest(
            '/ticket/get/' + sessionStorage.getItem('id'),
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

        if(ticketInfo.state!="未完成") {
            var ticketDomStr = '';
            ticketDomStr +=
                "<tr>" +
                "<td>" + schedule.movieName + "</td>" +
                "<td>" + schedule.hallName + "</td>" +
                "<td>" + (ticketInfo.rowIndex + 1) + "排" + (ticketInfo.columnIndex + 1) + "座" + "</td>" +
                "<td>" + formatDate(schedule.startTime) + " " + formatTime(schedule.startTime) + "</td>" +
                "<td>" + formatDate(schedule.endTime) + " " + formatTime(schedule.endTime) + "</td>" +
                "<td>" + ticketInfo.state + "</td>" +
                "</tr>";
            $('.ticket-on-list').append(ticketDomStr);
        }

    }


});