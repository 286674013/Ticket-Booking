/**
 * Created by StevenWu on 17/2/9.
 */


$(function(){
    init();
    $('#confirm').click(function(){
        update();
    });
});
function init() {
    jQuery.ajax({
        url: '/user/info',
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if (data.result == true) {
                var object = data.object;
                $('#name').val(object.name);
                $('#email').val(object.email);
                $('#telephone').val(object.telephone);
            } else {
                swal("个人信息获取失败", data.reason, "error");
            }
            $('#name').focus();
        },
        error: function (data) {
            swal("OMG", "服务器错误,请稍后重试!", "error");
        }
    });
}


function update() {
    if (!checkInput()) {
        jQuery.ajax({
            url: '/user/update/info',
            type: 'post',
            dataType: 'json',
            data: $('form').serialize(),
            success: function (data) {
                if (data.result == true) {
                    swal("成功", "个人信息修改成功!", "success");
                } else {
                    swal("失败", data.reason, "error");
                }
                init();
            },
            error: function (data) {
                swal("OMG", "服务器错误,请稍后重试!", "error");
            }
        });
    }
}