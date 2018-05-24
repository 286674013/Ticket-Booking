
$(function(){
    $('#confirm').click(function(){

            update( $(this).attr('diy-url'));
    }
     );
});


function update(type) {
    var object = $('form input');
   if(!($(object[1]).val() ==   $(object[2]).val())) {
       swal("注意", "新密码两次必须要一致!", "info");
       return;
   }
    url = "/"+type+"/update/password";

    if (!checkInput()) {
        jQuery.ajax({
            url: url,
            type: 'post',
            dataType: 'json',
            data: {
                oldPassword: $.md5($('#oldPassword').val()),
                newPassword1:$.md5($('#newPassword1').val()),
                newPassword2:$.md5($('#newPassword2').val())
            },
            success: function (data) {
                if (data.result == true) {
                    swal("成功", "密码修改成功!", "success");
                } else {
                    swal("失败", data.reason, "error");
                }
                $('#oldPassword').val("");
                $('#newPassword1').val("");
                $('#newPassword2').val("");
            },
            error: function (data) {
                swal("OMG", "服务器错误,请稍后重试!", "error");
            }
        });
    }
}