

$(function(){
    init();
});

function init(){
    jQuery.ajax({
        url: '/vuene/financeinfo',
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if(data.result == true) {
                var object = data.object;
                $('#time-tip').html(timeTip());
                $('#name').html(object.name);
                $('#balance').html("¥" + object.balanceNum);
                $('#order').html( object.orderNum);

                barTable();
                var date = new Array();
                var data = new Array();
                var list = object.list;
                for(var i = 0;i<list.length;i++){
                    var object2= list[i];
                    date.push(object2.date);
                    data.push(object2.price);
                }
                lineTable(date,data);
            }else {
                swal("初始化失败", data.reason, "error");
            }
        },
        error:function(data){
            swal("OMG", "服务器错误,请稍后重试!", "error");
        }
    });

    $('#confirm').click(function(){
        getmoney();
    });
}
function getmoney(){
    // alert("测试一下");
    jQuery.ajax({
        url: '/vuene/getmoney',
        type: 'post',
        dataType: 'json',
        data:{
            bankcardid:$('#bank-card').val(),
            password:$('#password').val(),
            money:$('#money').val()
        },
        success: function (data) {
            if (data.result == true){
                swal("提现成功",data.reason,"success");
            }else {
                swal("提现失败",data.reason, "error");
            }
        },
        error:function(data){
            swal("OMG", "服务器错误,请稍后重试!", "error");
        }
    });
}


function barTable(){
    jQuery.ajax({
        url: '/order/vuene/getCountPay',
        type: 'post',
        dataType: 'json',
        success: function (data) {
            var myChart1 = echarts.init(document.getElementById('bar'));
            option1 = {
                title : {
                    text: '支付比例分布',
                    x:'center'
                },
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data: ['会员卡支付','银行卡支付','现金支付']
                },
                series : [
                    {
                        name: '支付来源',
                        type: 'pie',
                        radius : '55%',
                        center: ['50%', '60%'],
                        data:[
                            {value:data.object[1], name:'会员卡支付'},
                            {value:data.object[2], name:'银行卡支付'},
                            {value:data.object[0], name:'现金支付'}
                        ],
                        itemStyle: {
                            emphasis: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            };

            $(window).resize(function(){
                myChart1.resize();
            });
            myChart1.setOption(option1);
        },error:function(data){
                swal("OMG", "服务器错误,请稍后重试!", "error");
            }
        });

}

function lineTable(date,data){
    var myChart = echarts.init(document.getElementById('line'));




    option = {
        tooltip: {
            trigger: 'axis',
            position: function (pt) {
                return [pt[0], '10%'];
            }
        },
        title: {
            left: 'center',
            text: '业绩趋势',
        },
        toolbox: {
            feature: {
                dataZoom: {
                    yAxisIndex: 'none'
                },
                restore: {},
                saveAsImage: {}
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: date
        },
        yAxis: {
            type: 'value',
            boundaryGap: [0, '100%']
        },
        dataZoom: [{
            type: 'inside',
            start: 0,
            end: 10
        }, {
            start: 0,
            end: 10,
            handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
            handleSize: '80%',
            handleStyle: {
                color: '#fff',
                shadowBlur: 3,
                shadowColor: 'rgba(0, 0, 0, 0.6)',
                shadowOffsetX: 2,
                shadowOffsetY: 2
            }
        }],
        series: [
            {
                name:'销售额',
                type:'line',
                smooth:true,
                symbol: 'none',
                sampling: 'average',
                itemStyle: {
                    normal: {
                        color: 'rgb(255, 70, 131)'
                    }
                },
                areaStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: 'rgb(255, 158, 68)'
                        }, {
                            offset: 1,
                            color: 'rgb(255, 70, 131)'
                        }])
                    }
                },
                data: data
            }
        ]
    };
    $(window).resize(function(){
        myChart.resize();
    });
    myChart.setOption(option);
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