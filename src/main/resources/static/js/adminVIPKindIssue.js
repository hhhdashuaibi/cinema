$(document).ready(function () {
    if(sessionStorage.getItem('power')>0){
        $("#staffManageModal").hide();
    }
    else {
        $("#staffManageModal").show();
    }
    getAllVipKinds();
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
        $(".content-VipKinds").empty();
        var VIPKindsDomStr = "";

        VIPKinds.forEach(function (VIPKind) {

            VIPKindsDomStr+=
                "<div id='VIP' class='VIPKind-container' data-vipkind='"+JSON.stringify(VIPKind)+"'>" +
                "    <div class='VIPKind-card card'>" +
                "       <div class='VIPKind-line'>" +
                "           <span class='title'>"+VIPKind.name+"</span>"+
                "       </div>" +
                "       <div class='VIPKind-line'>" +
                "           <span>发布时间："+formatDate(new Date(VIPKind.issueTime))+"</span>" +
                // "           <span>  会员卡时限："+VIPKind.duration+"天"+"</span>" +
                "       </div>" +
                "    </div>" +
                "    <div class='VIPKind-coupon primary-bg'>" +
                "        <span class='title'>价格："+VIPKind.price+"</span>" +
                "        <span class='title'>满"+VIPKind.targetAmount+"减<span class='error-text title'>"+VIPKind.discountAmount+"</span></span>" +
                "    </div>" +
                "</div>";


        });
        $(".content-VipKinds").append(VIPKindsDomStr);
        var vipkind=document.getElementById('VIP');
        console.log(vipkind.dataset.vipkind);
    }

    $("#VIPKind-form-btn").click(function () {
        var form = {
            name: $("#VIPKind-name-input").val(),
            issueTime:$("#VIPKind-issue-time-input").val(),
            price:$("#VIPKind-price-input").val(),
            // duration:$("#VIPKind-duration-input").val(),
            targetAmount:$("#VIPKind-target-input").val(),
            discountAmount:$("#VIPKind-give-input").val()
            }

        postRequest(
            '/vip/issue',
            form,
            function (res) {
                if(res.success){
                    getAllVipKinds();
                    $("#VIPKindModal").modal('hide');
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });

    $(document).on('click','.VIPKind-container',function (e) {
        var VIPKind = JSON.parse(e.currentTarget.dataset.vipkind);
        $("#VIPKind-edit-name-input").val(VIPKind.name);
        $("#VIPKind-edit-target-input").val(VIPKind.targetAmount);
        $("#VIPKind-edit-give-input").val(VIPKind.discountAmount);
        $("#VIPKind-edit-issue-time-input").val(VIPKind.issueTime.slice(0,16));
        $("#VIPKind-edit-price-input").val(VIPKind.price);
        // $('#VIPKind-edit-duration-input').val(VIPKind.duration);
        $('#VIPKindEditModal').modal('show');
        $('#VIPKindEditModal')[0].dataset.VIPKindId = VIPKind.id;
        console.log(VIPKind);
    });

    $('#VIPKind-edit-form-btn').click(function () {
        var form = {
            name: $("#VIPKind-edit-name-input").val(),
            issueTime:$("#VIPKind-edit-issue-time-input").val(),
            price:$("#VIPKind-edit-price-input").val(),
            // duration:$("#VIPKind-edit-duration-input").val(),
            targetAmount:$("#VIPKind-edit-target-input").val(),
            discountAmount:$("#VIPKind-edit-give-input").val()
        };
        postRequest(
            '/vip/updateVIPCard?kind='+$("#VIPKind-edit-name-input").val()+'&targetAmount='+$("#VIPKind-edit-target-input").val()+'&discountAmount='+$("#VIPKind-edit-give-input").val(),
            null,
            function (res) {
                if(res.success){

                } else{
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
        postRequest(
            '/vip/updateVIPKind',
            form,
            function (res) {
                if(res.success){
                    getAllVipKinds();
                    $("#VIPKindEditModal").modal('hide');
                } else{
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );

    });

    $("#VIPKind-edit-remove-btn").click(function () {
        var r=confirm("确认要删除该排片信息吗")
        if (r) {
            deleteRequest(
                '/vip/deleteVIPKind?name='+$("#VIPKind-edit-name-input").val(),
                {},
                function (res) {
                    if(res.success){
                        getAllVipKinds();
                        $("#VIPKindEditModal").modal('hide');
                    } else{
                        alert(res.message);
                    }
                },
                function (error) {
                    alert(JSON.stringify(error));
                }
            );
        }
    })
})
