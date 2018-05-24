
$(function(){
   init();
   $('#confirmB').click(function(){
       jQuery.ajax({
           url: '/user/recharge',
           type: 'post',
           dataType: 'json',
           data:{
               bankcard:$('#bank-card').val(),
               password:$('#password').val(),
               money:$('#money').val()
           },
           success: function (data) {
               if (data.result == true){
                   swal("充值成功",data.reason,"success");
               }else {
                   swal("充值失败",data.reason, "error");
               }
               init();
           },
           error:function(data){
               swal("OMG", "服务器错误,充值失败,请稍后重试!", "error");
               init();
           }

       });

   });

    $(function(){
        $('#confirmC').click(function(){
            jQuery.ajax({
                url: '/user/transform',
                type: 'post',
                dataType: 'json',
                data:{
                    num:$('#point').val()},
                success: function (data) {
                    if (data.result == true) {
                        swal("兑换成功", "您已经成功兑换积分", "success");
                        init();
                    } else {
                        swal("兑换失败", data.reason, "error");
                    }
                },
                error:function(data){
                    swal("OMG", "服务器错误,请稍后重试!", "error");
                }
            });
        });
    });

    // $('#active').click(function(){
    //     jQuery.ajax({
    //         url: '/user/active',
    //         type: 'post',
    //         dataType: 'json',
    //         success: function (data) {
    //             if (data.result == true){
    //                 swal("激活成功",data.reason,"success");
    //             }else {
    //                 swal("激活失败",data.reason, "error");
    //             }
    //             init();
    //         },
    //         error:function(data){
    //             swal("OMG", "服务器错误,激活失败,请稍后重试!", "error");
    //             init();
    //         }
    //
    //     });
    // });
    //
    // $('#giveup').click(function(){
    //     jQuery.ajax({
    //         url: '/user/cancle',
    //         type: 'post',
    //         dataType: 'json',
    //         success: function (data) {
    //             if (data.result == true){
    //                 swal("注销成功",data.reason,"success");
    //             }else {
    //                 swal("注销失败",data.reason, "error");
    //             }
    //             init();
    //         },
    //         error:function(data){
    //             swal("OMG", "服务器错误,注销失败,请稍后重试!", "error");
    //             init();
    //         }
    //
    //     });
    // })


});

function init(){
    jQuery.ajax({
        url: '/user/info',
        type: 'post',
        dataType: 'json',
        success: function (data) {
            var object = data.object;
            // console.log(object.discount)
            $('#time-tip').html(timeTip());
            $('#name').html(object.name);
            $('#vip-card').html(vipCardDeal(object.id));
            $('#level').html(object.level+"级");
            $('#discount').html(object.discount);
            $('#score').html(object.point);
            $('#balance').html(object.balance);
            $('#countspense').html(object.countspending);
        },
        error:function(data){
            swal("OMG", "服务器错误,请稍后重试!", "error");
        }
    });
}

function recharge(){
    // alert("测试一下");
    jQuery.ajax({
        url: '/user/recharge',
        type: 'post',
        dataType: 'json',
        data:{
            bankcard:$('#bank-card').val(),
            password:$('#password').val(),
            money:$('#money').val()
        },
        success: function (data) {
          if (data.result == true){
              swal("充值成功",data.reason,"success");
          }else {
              swal("充值失败",data.reason, "error");
          }
        },
        error:function(data){
            swal("OMG", "服务器错误,请稍后重试!", "error");
        }
    });
}

function timeTip(){
    var localDate = new Date();
    hour = localDate.getHours();
    if((hour>=0 && hour<=5)||(hour>=18&&hour<=23))
        return "晚上好,";
    else if (hour>=6&&hour<=11)
        return "早上好,";
    else if (hour>=12&&hour<=2)
        return "中午好,";
    else
        return "下午好,";
}

function vipCardDeal(id){
    id = id+"";
    var length = id.length;
    for (var i = 0;i<5-length;i++)
    id="0"+id;

    id= "VP"+id;
    return id;
}


