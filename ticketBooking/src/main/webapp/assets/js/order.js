
$(function(){
    init();
    $('#confirm').click(function(){
        addOrder();
    });
});

function init(){
    var disCount = 0.95;

    jQuery.ajax({
        url: '/user/discount',
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if (data.result == true) {
                disCount = data.object;
                // dateCheck(disCount);
            } else {
                top.location = "login.html"+window.location.search;
            }
        },
        error:function(data){
            swal("OMG", "服务器错误,请稍后重试!", "error");
        }
    });
    getPerformInfo($.getUrlParam("performid"));
    // dateCheck(disCount);
    // $('#room').html($.getUrlParam('roomname'));
    // dateCheck(disCount);
    // $('#start').blur(function(){
    //     dateCheck(disCount);
    // });
    // $('#end').blur(function(){
    //     dateCheck(disCount);
    // });


}
function getPerformInfo(id) {

    var result=new Object();
    result.performid=id;
    jQuery.ajax({
        url: '/findPerformById',
        type: 'post',
        dataType: 'json',
        data:result ,
        success: function (data) {
            if (data.result == true) {
                var perform = data.object;

                makevueneNode(perform.performid,perform.vueneid, perform.performname, perform.performtype, perform.seatprice, perform.begintime,perform.endtime,perform.countvacancy,perform.vuenename,perform.performdescription,perform.playroomname,perform.playroomdescription,perform.vacancy);
            } else {
                swal("Error", "获取列表失败,请重新刷新页面", "error");
            }
        },
        error: function (data) {
            swal("OMG", "服务器错误,请稍后重试!", "error");
        }
    });
}
function makevueneNode(performid,vueneid, performname, performtype, seatprice, begintime,endtime,countvacancy,vuenename,performdescription,playroomname,playroomdescription,vacancy) {
    console.log(playroomdescription);
    var result =
        "<div class='row vuene-row row-item'> " +
        "<div class='col-md-12 col-md-offset-1 item'  > <div> " +

        "<a class='vuene-name' href='order.html?performid=${performid}'><span >${performname}</span></a>" +
        // "<img class='img-vuene' src='/assets/image/vuene/0.png'> " +
        // "<img src='/assets/image/vuene/tip.png' class='img-tip'> " +
        "</div>" +
        " <table > <thead> <tr> <th  class='first-tr' width='120'>ID</th> <th width='90'>演出类型</th> <th width='70' >价格</th> " +
        "<th width='120' >开始时间</th> <th width='120' >结束时间</th> <th width='90'>余票</th>" +
        " <th width='110' >场馆名称</th> <th width='110' >放映室名称</th>  </tr> </thead> <tbody id='${tbodyCount}'>" +
        "<tr><td>${performid}</td><td>${performtype}</td><td>${seatprice}</td><td>${begintime}</td><td>${endtime}</td><td>${countvacancy}</td>" +
        "<td><a class='vuene-name' href='vuene.html?vueneid=${vueneid}'><span >${vuenename}</span></a></td><td>${playroomname}</td></tr>" +
        "</tbody> </table>" +
        "</div> " +
        "<div class='col-md-12 introduce'  >" +
        " <div class='col-md-12'>演出简介<br><p class='desp'>${performdescription} </p></div> " +
        " <div class='col-md-12'>座位描述<br><p class='desp'>${playroomdescription}</p></div> " +
        " <div class='col-md-11'>余座<br><p class='desp'>${vacancy}</p></div> " +
        " </div> " +

        "</div>";


    $.tmpl(result, {
        performid: performid,
        vueneid: vueneid,
        performname: performname,
        performtype: performtype,
        seatprice: seatprice,
        begintime: begintime,
        endtime: endtime,
        countvacancy: countvacancy,
        vuenename: vuenename,
        performdescription: performdescription,
        playroomname:playroomname,
        playroomdescription:playroomdescription,
        vacancy:vacancy
    }).appendTo($('#perform-info'));
}

function addOrder(){
    if (!checkInput()){
        console.log( $('#typechoice option:selected').val() +""+$('#discountchoice option:selected').val());
        jQuery.ajax({
            url: '/order/user/add',
            type: 'post',
            dataType: 'json',
            data: {
                buytype: $('#typechoice option:selected').val(),
                discounttype: $('#discountchoice option:selected').val(),
                // buytype: 0,
                // discounttype: 0,
                performid:$.getUrlParam('performid'),
                seats:$("input[ name='tickets' ]").val(),
                remark:$("input[ name='remark' ]").val()
            },
            success: function (data) {

                if (data.result == true) {
                    swal("下单成功", "请在5分钟以内完成支付", "success");
                } else {
                    swal("下单失败", data.reason, "error");
                }
            },
            error:function(data){
                swal("OMG", "服务器错误,请稍后重试!", "error");
            }
        });

    }


}