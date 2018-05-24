/**
 * Created by StevenWu on 17/2/14.
 */
$(function(){
    $('#tablepool').bootstrapTable({

        url: '/order/manager/list',
        dataField: "object",//服务端返回数据键值 就是说记录放的键值是rows，分页时使用总记录数的键值为total
        height: tableHeight(),//高度调整
        search: true,//是否搜索
        pagination: true,//是否分页
        pageSize: 20,//单页记录数
        pageList: [5, 10, 20, 50],//分页步进值
        sidePagination: "server",//服务端分页
        contentType: "application/json",//请求数据内容格式 默认是 application/json 自己根据格式自行服务端处理
        dataType: "json",//期待返回数据类型
        method: "post",//请求方式
        searchAlign: "left",//查询框对齐方式
        queryParamsType: "limit",//查询参数组织方式
        queryParams: function getParams(params) {
            //params obj
            params.other = "otherInfo";
            return params;
        },
        searchOnEnterKey: false,//回车搜索
        showRefresh: true,//刷新按钮
        showColumns: true,//列选择按钮
        buttonsAlign: "left",//按钮对齐方式
        toolbar: "#toolbar",//指定工具栏
        toolbarAlign: "right",//工具栏对齐方式
        //表格的列
        columns: [
            {
                field: 'check',//域值
            },
            {
                field: 'orderid',//域值
                visible: true,//false表示不显示
                sortable: true,//启用排序
                editable:false,
            },
            {
                field: 'originalprice',//域值

            },
            {
                field: 'price',//域值

            },
            {
                field: 'performname',//域值

            },
            {
                field: 'vuenename',//域值

            },
            {
                field: 'performstart',//域值

            },
            {
                field: 'performend',//域值

            },
            {
                field: 'starttime',//域值

            },
            {
                field: 'orderstate',//域值

            },
            {
                field: 'paytime',//域值

            },
            {
                field: 'cancletime',//域值

            },
            {
                field: 'seats',//域值

            },
            {
                field: 'remark',//域值

            }
        ],
        onClickRow: function(row, $element) {
            //$element是当前tr的jquery对象
            // $element.css("background-color", "green");
        },//单击row事件
        locale: "zh-CN", //中文支持
        detailView: false, //是否显示详情折叠

    });
    });

    $('#cancle').click(function () {
        var ids = new Array();
        var objects = $('#tablepool').bootstrapTable('getSelections');
        for (var i = 0; i < objects.length; i++) {
            ids.push(objects[i].orderid);
        }
        jQuery.ajax({
            url: '/order/manager/cancle',
            type: 'post',
            dataType: 'json',
            traditional: true,
            data: {
                'orderid': ids
            },
            success: function (data) {
                if (data.result == true) {
                    swal("成功", "您所选的订单已取消", "success");

                } else {
                    swal("取消失败", data.reason, "error");
                }
                $('#tablepool').bootstrapTable('refresh');
            },
            error: function (data) {
                swal("OMG", "服务器错误,取消订单失败,请稍后重试!", "error");
            }
        });

    });





function rowStyle(row, index) {
    var classes = ['active', 'success', 'info', 'warning', 'danger'];

    if (row.orderstate == '已取消') {
        return {
            classes: classes[4]
        };
    }else if(row.orderstate == '已完成'){
        return {
            classes: classes[1]
        };
    }
    return {};
}
function stateFormatter(value, row, index) {
    if (row.orderstate == '已支付') {
        return {
            disabled: false
        };
    }
    else {
        return {
            disabled: true
        }
    }
}
var num = 0;


function tableHeight() {
    return $(window).height() - 50;
}
(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
        (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
    m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
})(window,document,'script','//www.google-analytics.com/analytics.js','ga');
ga('create', 'UA-36708951-1', 'wenzhixin.net.cn');
ga('send', 'pageview');