

$(function(){
    $("#select_k1").change(function(){
        if($(this).val() =="card"){
            $('.modal-body:first').append(" <div class='modal-body modal-card ' id='body-hidden'> <p>银行卡号</p> " +
                "<input type='text' class='form-control' placeholder='卡号' name='card' id='card'> " +
                "<p>密码</p> <input type='password' class='form-control' placeholder='密码' name='password' id='password'> " +
                "</div>");
        }else {
            $('#body-hidden').remove();
        }
    });
    init();
});

function init (){

    jQuery.ajax({
        url: '/order/user/list',
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if (data.result == true) {
               var object = data.object;
                $("tr").remove(".main-tr");
                for(var i = 0;i<object.length;i++){

                    append(object[i]);

                }
                $(" input[ name='card' ] ").val("");
                $(" input[ name='password' ] ").val("");
            } else {
                swal("订单列表获取失败,请稍后重试", data.reason, "error");
            }
        },
        error:function(data){
            swal("OMG", "服务器错误,请稍后重试!", "error");
        }
    });


}

function append(object){
    var main =    "<tr class='main-tr' > " +
        "<td  class='room-info' ><span >${orderid}</span></td> " +
        " <td class='room-item'><span style='color:#cf3025'>${originalprice}</span></td> " +
        " <td class='room-item'><span style='color:#cf3025'>${price}</span></td> " +
        "<td class='room-item' >${performname}</td> " +
        "<td class='room-item' >${vuenename}</td> " +
        "<td class='room-item' >${performstart}</td> " +
        "<td class='room-item' >${performend}</td> " +
        "<td class='room-item' >${starttime}</td> " +
        "<td class='room-item' >${orderstate}</td> " +
        "<td class='room-item'>${paytime}</td>" +
        "<td class='room-item'>${cancletime}</td>" +
        "<td class='room-item'><p style='width: 90px' class='stablewidth'>${seats}</p></td>" +
        "<td class='room-item'><p style='width: 90px' class='stablewidth'>${remark}</p></td>" ;


    var operator = "";
    if (object.orderstate == "已下单"){
        operator = "<td class='room-item-confirm'><button class='btn btn-primary btn-sm halfbutton halfbutton-first' orderid='${orderid}'  id='cl${orderid}''>取消</button>" +
            "<button class='btn btn-danger btn-sm halfbutton' orderid='${orderid}' id='pay${orderid}'>支付</button></td> </tr>";

    }else if (object.orderstate =="已支付"){
        operator = "<td class='room-item-confirm'><button class='btn btn-primary btn-sm halfbutton halfbutton-first' orderid='${orderid}'  id='cl${orderid}''>取消</button>" +
            "<button class='btn btn-danger btn-sm halfbutton' orderid='${orderid}' id='confirm${orderid}'>完成</button></td> </tr>";

    }else if (object.orderstate =="已取消"){
        operator = " <td class='room-item-confirm'><button class='btn btn-danger btn-sm' disabled='disabled'>已取消</button></td> </tr>";

    }else {
        operator = " <td class='room-item-confirm'><button class='btn btn-danger btn-sm' disabled='disabled'>已完成</button></td> </tr>";

    }
    main = main+operator;

    $.tmpl(main, {
        orderid:object.orderid,
        originalprice:object.originalprice,
        price:object.price,
        performname:object.performname,
        vuenename:object.vuenename,
        playroomname:object.playroomname,
        performstart:object.performstart,
        performend:object.performend,
        starttime:object.starttime,
        orderstate:object.orderstate,
        paytime:object.paytime,
        cancletime:object.cancletime,
        seats:object.seats,
        remark:object.remark
    }).appendTo($('#mainPanel'));
    if (object.orderstate == "已支付"){

        var cancleid = "#cl"+object.orderid;
        $(cancleid).click(function(){
                cancle(object.orderid);
        });
        var confirmid = "#confirm"+object.orderid;
        $(confirmid).click(function(){
            confirm(object.orderid);
        });

    }else  if (object.orderstate == "已下单"){
        var cancleid = "#cl"+object.orderid;
        $(cancleid).click(function(){
            cancle(object.orderid);
        });

        var confirmid = "#pay"+object.orderid;
        $(confirmid).click(function(){
            $('#myModal').modal('toggle');
            $("#myModal").modal().css({
                "margin-top": function () {
                    return ($(this).height() / 5);
                }
            });
            pay(object.orderid);
        });

    }
}

function cancle(id){
    console.log(id);
    jQuery.ajax({
        url: '/order/user/cancle',
        type: 'post',
        dataType: 'json',
        data: {
            orderid:id
        },
        success: function (data) {
            if (data.result == true) {
                swal("订单取消成功", "已为您取消订单!", "success");
            } else {
                swal("订单取消失败", data.reason, "error");
            }
            init();
        },
        error:function(data){
            swal("OMG", "服务器错误,请稍后重试!", "error");
        }
    });
}


function confirm(id){
    console.log(id);
    jQuery.ajax({
        url: '/order/user/confirm',
        type: 'post',
        dataType: 'json',
        data: {
            orderid:id
        },
        success: function (data) {
            if (data.result == true) {
                swal("订单完成成功", "已为您完成订单!", "success");
            } else {
                swal("订单完成失败", data.reason, "error");
            }
            init();
        },
        error:function(data){
            swal("OMG", "服务器错误,请稍后重试!", "error");
        }
    });
}



function pay(id){
    var func = "payBalance("+id+");";
    $("#select_k1").change(function(){

        if($(this).val() =="balance"){
             func = "payBalance("+id+");";

        }else if ($(this).val() =="card"){
             func = "payCard("+id+");";

        }else {
            func = "payCash("+id+");";
        }

        $('#confirm-pay').attr("onclick",func);
    });
    // $('#confirm-pay').attr("onclick",func);
}


function payCash(id){

    jQuery.ajax({
        url: '/order/pay/cash',
        type: 'post',
        dataType: 'json',
        data: {
            orderid:id
        },
        success: function (data) {
            if (data.result == true) {
                swal("订单支付成功", "期待您的观赏", "success");
            } else {
                swal("订单支付失败", data.reason, "error");
            }
            init();
        },
        error:function(data){
            swal("OMG", "服务器错误,请稍后重试!", "error");
        }
    });
}


function payCard(id){

    jQuery.ajax({
        url: '/order/pay/bank',
        type: 'post',
        dataType: 'json',
        data: {
            orderid:id,
            cardid:$('#card').val(),
            password:$('#password').val()
        },
        success: function (data) {
            if (data.result == true) {
                swal("订单支付成功", "期待您的观赏", "success");
            } else {
                swal("订单支付失败", data.reason, "error");
            }
            init();
        },
        error:function(data){
            swal("OMG", "服务器错误,请稍后重试!", "error");
        }
    });
}

function payBalance(id){

    jQuery.ajax({
        url: '/order/pay/balance',
        type: 'post',
        dataType: 'json',
        data: {
            orderid:id
        },
        success: function (data) {
            if (data.result == true) {
                swal("订单支付成功", "期待您的观赏", "success");
            } else {
                swal("订单支付失败", data.reason, "error");
            }
            init();
        },
        error:function(data){
            swal("OMG", "服务器错误,请稍后重试!", "error");
        }
    });
}