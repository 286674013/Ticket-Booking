
$(function(){

    jQuery.ajax({
        url: '/user/manager/info',
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if (data.result == true) {
                var object = data.object;
                $('#name').val(object.name);
                $('#percent').val(object.percent);
                $('#email').val(object.email);
                $('#telephone').val(object.telephone);
            }
        }

    });

    $('#confirm').click(function(){
        if (!checkInput()){
            jQuery.ajax({
                url: '/user/manager/update',
                type: 'post',
                dataType: 'json',
                data: $('#form').serialize(),
                success: function (data) {
                    if (data.result == true) {
                        swal("成功", "信息修改成功", "success");
                    }
                }

            });
        }
    });

});