

$(function(){
    init();
    // $('#performchoice').multiselect({setMaxOptionNum:10,selectedHtmlValue:'多选'});

    $('#performkeys').manifest();

  


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
                addOrModify("/vuene/addPerform","添加")
            });
    });

    $('#tablepool').bootstrapTable('refresh');
    initModalOptions();

}

function initModalOptions() {
    var _select=document.getElementById("roomchoice");
    console.log(_select.value);
    _select.innerHTML="";

    jQuery.ajax({
        url: '/vuene/findRoomByVuene',
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if (data.result == true) {
                // swal("成功获取放映室列表", "获取放映室列表成功", "success");
                // console.log(data.object[0].playroomname.toString());
                for(var i=0;i<data.object.length;i++){
                    var temp=data.object[i];
                    // console.log(temp.playroomname);
                    var _option = new Option(temp.playroomname,temp.playroomid);
                    _select.options.add(_option);
                }
            } else {
                swal("获取放映室列表失败,请重新操作", data.reason, "error");
            }
            $('#tablepool').bootstrapTable('refresh');
        },
        error:function(data){
            swal("OMG", "服务器错误,删除失败,请稍后重试!", "error");
        }

    });

}

function getPerformKeys() {
    var key="";
    $(".mf_item").each(function(){
        key=key+$(this).text()+",";
    });
    return key;
}

function addOrModify(url,tip){
    // alert(getPerformKeys());
    // console.log($('#roomchoice option:selected').val()+$('#performchoice option:selected').val());
    if (!checkInput()) {
        var performid = $('#performid').val();
        if (performid == ''||!performid)
            performid = -1;
        jQuery.ajax({
            url: url,
            type: 'post',
            dataType: 'json',
            data: {
                performid:performid,

                playroomname: $('#roomchoice option:selected').val(),
                performtype: $('#performchoice option:selected').val(),

                performname: $('input[name="performname"]').val(),
                seatprice: $('input[name="seatprice"]').val(),
                begintime: $('input[name="begintime"]').val(),
                endtime: $('input[name="endtime"]').val(),
                totalseats: $('input[name="totalseats"]').val(),

                performkeys:getPerformKeys(),
                performdescription: $('input[name="performdescription"]').val()

            },
            success: function (data) {
                if (data.result == true) {
                    swal("成功", ""+tip+"成功", "success");
                } else {
                    swal("失败", data.reason, "error");
                }
                $('#tablepool').bootstrapTable('refresh');

                $('input[name="performid"]').val('');
                $('input[name="playroomname"]').val('');
                $('input[name="performtype"]').val('');

                $('input[name="performname"]').val('');
                $('input[name="seatprice"]').val('');
                $('input[name="begintime"]').val('');
                $('input[name="endtime"]').val('');
                $('input[name="totalseats"]').val('');

                $('input[name="performdescription"]').val('');

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

        $('input[name="performid"]').val(row.performid);
        $('input[name="playroomname"]').val(row.playroomname);
        $('input[name="performtype"]').val(row.performtype);

        $('input[name="performname"]').val(row.performname);
        $('input[name="seatprice"]').val(row.seatprice);
        $('input[name="begintime"]').val(row.begintime);
        $('input[name="endtime"]').val(row.endtime);
        $('input[name="totalseats"]').val(row.totalseats);
        $('input[name="performdescription"]').val(row.performdescription)       ;
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
                addOrModify("/vuene/updatePerform","修改")
            }
        );
    },
    'click .delete': function (e, value, row) {
        jQuery.ajax({
            url: '/vuene/setPerformDeleted',
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
    url:"/vuene/findPerformByVuene",
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
            field: 'performid',//域值
            title: 'ID',//标题
            visible: false,//false表示不显示
            sortable: true,//启用排序

        },
        {
            field: 'performname',//域值
            title: '演出名称',//标题
            visible: true,//false表示不显示
            sortable: true,//启用排序
            editable:true,
        },
        {
            field: 'performtype',//域值
            title: '演出类型',//内容
            visible: true,//false表示不显示
            sortable: true,//启用排序
            editable:true,
        },
        {
            field: 'state',//域值
            title: '演出状态',//内容
            visible: true,//false表示不显示
            sortable: true,//启用排序
            editable:true,
        },
        {
            field: 'seatprice',//域值
            title: '价格',//内容
            visible: true,//false表示不显示
            sortable: true,//启用排序
            editable:true,
        },
        {
            field: 'begintime',//域值
            title: '开始时间',//内容
            visible: true,//false表示不显示
            sortable: true,//启用排序
            editable:true,
        },
        {
            field: 'endtime',//域值
            title: '结束时间',//内容
            visible: true,//false表示不显示
            sortable: true,//启用排序
            editable:true,
        },
        {
            field: 'vuenename',//域值
            title: '场馆名称',//内容
            visible: true,//false表示不显示
            sortable: true,//启用排序
            editable:true,
        },
        {
            field: 'playroomname',//域值
            title: '放映室名称',//内容
            visible: true,//false表示不显示
            sortable: true,//启用排序
            editable:true,
        },
        {
            field: 'totalseats',//域值
            title: '座位数',//内容
            visible: true,//false表示不显示
            sortable: true,//启用排序
            editable:true,
        },
        {
            field: 'countvacancy',//域值
            title: '余座数',//内容
            visible: true,//false表示不显示
            sortable: true,//启用排序
            editable:true,
        },
        {
            field: 'vacancy',//域值
            title: '余座',//内容
            visible: true,//false表示不显示
            sortable: true,//启用排序
            editable:true,
        },
        {
            field: 'performdescription',//域值
            title: '演出描述',//内容
            visible: true,//false表示不显示
            sortable: true,//启用排序
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