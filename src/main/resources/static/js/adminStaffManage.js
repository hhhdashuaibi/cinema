$(document).ready(function () {
    getAllStaffs();
    function getAllStaffs() {
        getRequest(
            '/getStaff',
            function (res) {
                var staff = res.content;
                renderStaff(staff);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    function renderStaff(staff) {
        console.log(staff);
        $(".content-staff").empty();
        var staffDomStr = "";

        staff.forEach(function (oneStaff) {

            staffDomStr+=
                "<div id='staff' class='staff-container' data-oneStaff='"+JSON.stringify(oneStaff)+"'>" +
                "    <div class='staff-card card'>" +
                "       <div class='staff-line'>" +
                "           <span class='title'>姓名："+oneStaff.name+"</span>"+
                "       </div>" +
                "       <div class='staff-line'>" +
                "           <span class='title'>账号："+oneStaff.username+"</span>" +
                "       </div>" +
                "    </div>" +
                "</div>";


        });
        $(".content-staff").append(staffDomStr);
    }

    $("#staff-form-btn").click(function () {
        var form = {
            name: $("#staff-name-input").val(),
            username:$("#staff-username-input").val(),
            power:1
        }

        postRequest(
            '/updateStaff',
            form,
            function (res) {
                if(res.success){
                    getAllStaffs();
                    $("#staffModal").modal('hide');
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });

    $(document).on('click','.staff-container',function (e) {
        var oneStaff = JSON.parse(e.currentTarget.dataset.onestaff);
        $("#staff-edit-name-input").val(oneStaff.name);
        $("#staff-edit-username-input").val(oneStaff.username);
        $('#staffEditModal').modal('show');
        $('#staffEditModal')[0].dataset.staffId = oneStaff.id;
    });

    $('#staff-edit-form-btn').click(function () {
        var form = {
                name: $("#staff-edit-name-input").val(),
                username:$("#staff-edit-username-input").val(),
                power:1
        };
        postRequest(
            '/updateStaff',
            form,
            function (res) {
                if(res.success){
                    getAllStaffs();
                    $("#staffEditModal").modal('hide');
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );

    });

    $("#staff-edit-remove-btn").click(function () {
        var form = {
            name: null,
            username:$("#staff-edit-username-input").val(),
            power:2
        };
        postRequest(
            '/updateStaff',
            form,
            function (res) {
                if(res.success){
                    getAllStaffs();
                    $("#staffEditModal").modal('hide');
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    })
})
