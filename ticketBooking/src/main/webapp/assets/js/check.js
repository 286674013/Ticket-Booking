function checkInput(){
    var haveError = false;
    $(".not-null").each(function(){
        var value = $(this).val();
        if (value.length<1){
            swal("Error", "总该输入一点信息吧:)", "error");
            $(this).css("border","1px red solid");
            haveError = true;
            return ;
        }else {
            $(this).css("border",'');
        }

    });
    $(".not-null-3").each(function(){
        var value = $(this).val();
        if (value.length<4){
            $(this).css("border","1px red solid");
            swal("Error", "输入的信息也太少了吧,明显不对嘛!:)", "error");
            haveError = true;
            return ;
        }else {
            $(this).css("border",'');
        }

    });



    $(".email").each(function(){
        var value = $(this).val();
        if (!(/^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/.test(value))){
            swal("Error", "请填写正确的邮箱:)", "error");
            $(this).css("border","1px red solid");
            haveError = true;
            return ;
        }else {
            $(this).css("border",'');
        }
    });

    $(".telephone").each(function(){
        var value = $(this).val();
        if (!(/^1[34578]\d{9}$/.test(value))){
            swal("Error", "请填写正确的电话:)", "error");
            $(this).css("border","1px red solid");
            haveError = true;
            return ;
        }else {
            $(this).css("border",'');
        }
    });

    $(".id-number").each(function(){
        var value = $(this).val();
        if (!(/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/.test(value)||/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{4}$/.test(value))){
            swal("Error", "请填写正确的身份证号码:)", "error");
            $(this).css("border","1px red solid");
            haveError = true;
            return ;
        }else {
            $(this).css("border",'');
        }
    });

    $(".checkconfirm").each(function(){


       if (!($(this).is(':checked'))){
           swal("Error", "请同意相关协议:)", "error");
           haveError = true;
           return;
       }

    });
    return haveError;
}

function matchDate(start,end){
    var start = new Date(start);
    var end = new Date(end);
    if (start.getTime()<=end.getTime())
        return true;
    else{
        swal("Error", "请选择正确的日期", "error");
        return false;
    }

}


