/**
 * Created by StevenWu on 17/2/11.
 */


$(function(){
    init();
});

function init(){
    $('#add').click(function(){
        $('#myModal').modal('toggle');
        $("#myModal").modal().css({
            "margin-top": function () {
                return ($(this).height() / 5);
            }
        });
        $('#confirmB').unbind('click');
        $('#confirmB').click(
            function(){
                addOrModify("/vuene/addRoom","添加")
            });
    });

    $('#tablepool').bootstrapTable('refresh');

}



function addOrModify(url,tip){
    if (!checkInput()) {
        var roomid = $('#playroomid').val();
        if (roomid == ''||!roomid)
            roomid = -1;
        jQuery.ajax({
            url: url,
            type: 'post',
            dataType: 'json',
            data: {
                playroomid:roomid,
                playroomname: $('input[name="playroomname"]').val(),
                totalseats: $('input[name="totalseats"]').val(),
                description: $('input[name="description"]').val()

            },
            success: function (data) {
                if (data.result == true) {
                    swal("成功", ""+tip+"成功", "success");
                } else {
                    swal("失败", data.reason, "error");
                }
                $('#tablepool').bootstrapTable('refresh');
                $('input[name="playroomid"]').val('');
                $('input[name="playroomname"]').val('');
                $('input[name="totalseats"]').val('');
                $('input[name="description"]').val('');



            },
            error:function(data){
                swal("OMG", "服务器错误,"+tip+"失败,请稍后重试!", "error");
            }

        });
    }


}


function operateFormatter(value, row) {

    return ['<a style="margin-right: 15px" class="modify">修改</a> <a class="delete">删除</a> ' ];
}
window.operateEvents = {
    'click .modify': function (e, value, row) {
        $('#playroomid').val(row.playroomid);
        $('input[name="playroomname"]').val(row.playroomname);
        $('input[name="totalseats"]').val(row.totalseats);
        $('input[name="description"]').val(row.description);
        $('.modal-title').html('修改');
        $('#myModal').modal('toggle');
        $("#myModal").modal().css({
            "margin-top": function () {
                return ($(this).height() / 5);
            }
        });

        $('#confirmB').unbind('click');
        $('#confirmB').click(
            function(){
                addOrModify("/vuene/updateRoom","修改")
            }
        );
    },
    'click .delete': function (e, value, row) {
        jQuery.ajax({
            url: '/vuene/setRoomDeleted',
            type: 'post',
            dataType: 'json',
            data: {
                playroomid:row.playroomid
            },
            success: function (data) {
                if (data.result == true) {
                    swal("成功", "删除成功", "success");
                } else {
                    swal("失败", data.reason, "error");
                }
                $('#tablepool').bootstrapTable('refresh');
            },
            error:function(data){
                swal("OMG", "服务器错误,删除失败,请稍后重试!", "error");
            }

        });
    }
};





$('#tablepool').bootstrapTable({
    url:"/vuene/findRoomByVuene",
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
            field: 'playroomid',//域值
            title: 'ID',//标题
            visible: false,//false表示不显示
            sortable: true,//启用排序
            width : '5%',
        },
        {
            field: 'playroomname',//域值
            title: '放映室名称',//标题
            visible: true,//false表示不显示
            sortable: true,//启用排序
            width : '30%',
            editable:true,
        },
        {
            field: 'totalseats',//域值
            title: '座位数',//内容
            visible: true,//false表示不显示
            sortable: true,//启用排序
            width : '15%',
            editable:true,
        },
        {
            field: 'description',//域值
            title: '放映室描述',//内容
            visible: true,//false表示不显示
            sortable: true,//启用排序
            width : '50%',
            // formatter : function (value, row, index) {
            //     if (row['status'] === 1) {
            //         return '正常';
            //     }
            //     if (row['status'] === 0) {
            //         return '禁用';
            //     }
            //     return value;
            // }
        }
    ],
    onClickRow: function(row, $element) {
        //$element是当前tr的jquery对象
        // $element.css("background-color", "green");
    },//单击row事件
    locale: "zh-CN", //中文支持
    detailView: false, //是否显示详情折叠
    detailFormatter: function(index, row, element) {
        var html = '';
        $.each(row, function(key, val){
            html += "<p>" + key + ":" + val +  "</p>"
        });
        return html;
    }

});

$("#addRecord").click(function(){
    alert("name:" + $("#name").val() + " age:" +$("#age").val());
});

function tableHeight() {
    return $(window).height() - 50;
}
/**
 * 列的格式化函数 在数据从服务端返回装载前进行处理
 * @param  {[type]} value [description]
 * @param  {[type]} row   [description]
 * @param  {[type]} index [description]
 * @return {[type]}       [description]
 */
function infoFormatter(value, row, index)
{
    return "id:" + row.id + " name:" + row.name + " age:" + row.age;
}
// (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
//     (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
//     m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
// })(window,document,'script','../assets/js/analytics.js','ga');
// ga('create', 'UA-36708951-1', 'wenzhixin.net.cn');
// ga('send', 'pageview');