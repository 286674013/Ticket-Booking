

$(function(){
    $('#tablepool').bootstrapTable({

        url: '/user/list',
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
        // <th data-field="id">会员卡号</th>
        // <th data-field="name">会员姓名</th>
        // <th data-field="username">用户名</th>
        // <th data-field="balance">会员卡余额</th>
        // <th data-field="point">积分</th>
        // <th data-field="countspending">总消费</th>
        // <th data-field="countorders">总订单数</th>
        // <th data-field="level">等级</th>
        // <th data-field="discount">折扣</th>
        // <th data-field="email">邮箱</th>
        // <th data-field="telephone">电话</th>
        // <th data-field="state">状态</th>
        columns: [
            {
                field: 'check',//域值
            },
            {
                field: 'id',//域值
                visible: true,//false表示不显示
                sortable: true,//启用排序
                editable:false,
            },
            {
                field: 'name',//域值

            },
            {
                field: 'username',//域值

            },
            {
                field: 'balance',//域值

            },
            {
                field: 'point',//域值

            },
            {
                field: 'countspending',//域值

            },
            {
                field: 'countorders',//域值

            },
            {
                field: 'level',//域值

            },
            {
                field: 'discount',//域值

            },
            {
                field: 'email',//域值

            },
            {
                field: 'telephone',//域值

            },
            {
                field: 'state',//域值

            }
        ],
        onClickRow: function(row, $element) {
            //$element是当前tr的jquery对象
            // $element.css("background-color", "green");
        },//单击row事件
        locale: "zh-CN", //中文支持
        detailView: false, //是否显示详情折叠

    });

    $('#cancle').click(function () {
        var ids = new Array();
        var objects = $('#tablepool').bootstrapTable('getSelections');
        for (var i = 0; i < objects.length; i++) {
            ids.push(objects[i].id);
        }
        jQuery.ajax({
            url: '/user/manager/cancle',
            type: 'post',
            dataType: 'json',
            traditional: true,
            data: {
                'userid': ids
            },
            success: function (data) {
                if (data.result == true) {
                    swal("成功", "您所选的会员已取消", "success");

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




});

function rowStyle(row, index) {
    var classes = ['active', 'success', 'info', 'warning', 'danger'];

    if (row.state == '') {
        return {
            classes: classes[2]
        };
    }else if(row.state == '已过期'){
        return {
            classes: classes[4]
        };
    }
    return {};
}
function tableHeight() {
    return $(window).height() - 50;
}
(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
        (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
    m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
})(window,document,'script','//www.google-analytics.com/analytics.js','ga');
ga('create', 'UA-36708951-1', 'wenzhixin.net.cn');
ga('send', 'pageview');