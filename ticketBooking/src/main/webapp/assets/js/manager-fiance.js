
$(function(){

    jQuery.ajax({
        url: '/user/manager/financeinfo',
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if(data.result == true) {
                var object = data.object;
                $('#time-tip').html(timeTip());
                $('#name').html(object.adminname);
                $('#balance').html( object.balance);
                $('#usernum').html(object.usernum);
                $('#vuenenum').html(object.vuenenum);
                $('#appointmentnum').html(object.appointmentnum);

                bar(object.checkin,object.uncheckin);

                var date = new Array();
                var data1 = new Array();
                var data2 = new Array();
                var list = object.list;
                for(var i = 0;i<list.length;i++){
                    var object2= list[i];
                    date.push(object2.date);
                    data1.push(object2.income);
                    data2.push(object2.number);
                }
                line(date,data1,data2);
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

});

function getmoney(){
    // alert("测试一下");
    jQuery.ajax({
        url: '/user/manager/getmoney',
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

function line(timeData,data1,data2){
    var myChart = echarts.init(document.getElementById('line'));




    option = {
        title: {
            text: '营业额/订单数',
            x: 'center'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                animation: false
            }
        },
        legend: {
            data:['每日营业额','订单数'],
            x: 'left'
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
        dataZoom: [
            {
                show: true,
                realtime: true,
                start: 30,
                end: 70,
                xAxisIndex: [0, 1]
            },
            {
                type: 'inside',
                realtime: true,
                start: 30,
                end: 70,
                xAxisIndex: [0, 1]
            }
        ],
        grid: [{
            left: 50,
            right: 50,
            height: '35%'
        }, {
            left: 50,
            right: 50,
            top: '55%',
            height: '35%'
        }],
        xAxis : [
            {
                type : 'category',
                boundaryGap : false,
                axisLine: {onZero: true},
                data: timeData
            },
            {
                gridIndex: 1,
                type : 'category',
                boundaryGap : false,
                axisLine: {onZero: true},
                data: timeData,
                position: 'top'
            }
        ],
        yAxis : [
            {
                name : '每日营业额',
                type : 'value'

            },
            {
                gridIndex: 1,
                name : '预订人数',
                type : 'value'
                // inverse: true
            }
        ],
        series : [
            {
                name:'每日营业额',
                type:'line',
                symbolSize: 8,
                hoverAnimation: false,
                data:data1
            },
            {
                name:'预订人数',
                type:'line',
                xAxisIndex: 1,
                yAxisIndex: 1,
                symbolSize: 8,
                hoverAnimation: false,
                data: data2
            }
        ]
    };
    $(window).resize(function(){
        myChart.resize();
    });
    myChart.setOption(option);
}

function bar(data1,data2){

    var myChart1 = echarts.init(document.getElementById('bar'));
    option1 = {
        title : {
            text: '下单/结单比',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['下单后完成','下单后取消']
        },
        series : [
            {
                name: '访问来源',
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:[
                    {value:data1, name:'下单后完成'},
                    {value:data2, name:'下单后取消'}
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