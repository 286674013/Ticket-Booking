

$(function () {


    $('#start').val($.getUrlParam("begintime"));
    $('#end').val($.getUrlParam("endtime"));
    $('#keywords').val($.getUrlParam("keywords"));
    searchvuene($('#start').val(), $('#end').val(),$('#keywords').val());
    $('#re-select').click(function () {
        if (matchDate($('#start').val(), $('#end').val())){
            searchvuene($('#start').val(), $('#end').val(),$('#keywords').val());
        }
            $('#re-select').blur();


    });

    jQuery.ajax({
        url: '/islogin',
        type: 'post',
        dataType: 'json',
        success: function (data) {
            var module2 = timeTip()+data.name;
            if (data.type == 0){
                module2 += "<li><a href='/assets/page/userinfo.html' >个人中心</a></li> ";
                $('#mainul').html(module2);
            }else if (data.type ==1){
                module2 += "<li><a href='/assets/page/vueneorder.html' >个人中心</a></li> ";
                $('#mainul').html(module2);
            }else if (data.type ==2){
                module2 += "<li><a href='/assets/page/managerorder.html' >个人中心</a></li> ";
                $('#mainul').html(module2);
            }
        }

    });

});

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


function searchvuene(begintime, endtime,keywords) {
    var result = new Object();
    result.begintime = begintime;
    result.endtime = endtime;
    result.keywords= keywords;
    // console.log(keywords);
    jQuery.ajax({
        url: '/search',
        type: 'post',
        dataType: 'json',
        data: result,
        success: function (data) {
            if (data.result == true) {
                var object = data.object;
                $("div").remove(".tip-no-match");
                $("div").remove(".vuene-row");
                if (object.length == 0) {
                    novuene();
                } else {
                    for (var i = 0; i < object.length; i++) {
                        var perform = object[i];
                        // makevueneNode(i, vuene.id, vuene.name, vuene.location, vuene.description, vuene.rooms);
                        makevueneNode(i, perform.performid,perform.vueneid, perform.performname, perform.performtype, perform.seatprice, perform.begintime,perform.endtime,perform.countvacancy,perform.vuenename,perform.performdescription);
                    }

                }

            } else {
                swal("Error", "获取列表失败,请重新刷新页面", "error");
            }
        },
        error: function (data) {
            swal("OMG", "服务器错误,请稍后重试!", "error");
        }
    });

}

function novuene() {
    var result = " <div class='tip-no-match'>未找到相关演出,请重新搜索!</div>";

    $("div").remove(".vuene-row");
    $.tmpl(result).appendTo($('#mainPanel'));
}

function makevueneNode(countNum, performid,vueneid, performname, performtype, seatprice, begintime,endtime,countvacancy,vuenename,performdescription) {
    countNum += 1;
    var isFirstRow = "row-item";
    var tbodyCount = "tbody" + countNum;
    var picTip =countNum% 5;

    if (countNum == 1) {
        isFirstRow = "";
    }
    var result =
        "<div class='row vuene-row ${isFirstRow}'> " +
        "<div class='col-md-6 col-md-offset-1 item'  > <div> " +

        "<a class='vuene-name' href='order.html?performid=${performid}'><span >${countNum}-${performname}</span></a>" +
        "<img class='img-vuene' src='/assets/image/vuene/${picTip}.png'> " +
        // "<img src='/assets/image/vuene/tip.png' class='img-tip'> " +
        "</div> <table > <thead> <tr> <th  class='first-tr'>ID</th> <th width='180'>演出类型</th> <th width='90' >价格</th> " +
        "<th width='90' >开始时间</th> <th width='109' >结束时间</th> <th width='90'>余票</th>" +
        " <th width='130' >场馆名称</th>  </tr> </thead> <tbody id='${tbodyCount}'>" +
        "<tr><td>${performid}</td><td>${performtype}</td><td>${seatprice}</td><td>${begintime}</td><td>${endtime}</td><td>${countvacancy}</td>" +
        "<td><a class='vuene-name' href='vuene.html?vueneid=${vueneid}'><span >${vuenename}</span></a></td></tr>"+
        "</tbody> </table></div> " +
        "<div class='col-md-3 introduce'  >" +
        " <div >演出简介</div> <p>${performdescription} </p>" +
        " </div> "+
        "</div>";


    $.tmpl(result, {
        isFirstRow: isFirstRow,
        picTip: picTip,
        countNum: countNum,
        performid:performid,
        vueneid:vueneid,
        performname:performname,
        performtype:performtype,
        seatprice:seatprice,
        begintime:begintime,
        endtime:endtime,
        countvacancy:countvacancy,
        vuenename:vuenename,
        performdescription:performdescription
    }).appendTo($('#mainPanel'));


    var c =$(".confirm-a");
    $(".confirm-a").each(function(){
        $(this).click(function(){
            $(this).attr("href",$(this).attr("href")+"&begintime="+$('#start').val()+"&endtime="+$('#end').val());
        });
    });

}

