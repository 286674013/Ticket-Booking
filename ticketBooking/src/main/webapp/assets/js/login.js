

$(function(){
    login(0);

    var href = window.location.search.split("=");

    if (href[1] == 'user'){
        userLogin();
        login(0);
    }else if(href[1] == 'vuene'){
        vueneLogin();
        login(1);
    }else if(href[1] =='manager'){
        adminLogin();
        login(2);
    }



    $("input[name='username']").focus(function () {
        $("label[role='user-ico']").addClass("l-focus-user");
    });
    $("input[name='username']").blur(function () {
        $("label[role='user-ico']").removeClass("l-focus-user");
    });

    $("input[name='password']").focus(function () {
        $("label[role='password-ico']").addClass("l-focus-password");
    });

    $("input[name='password']").blur(function () {
        $("label[role='password-ico']").removeClass("l-focus-password");
    });

    $("#loginpanel li:eq(0)").click(function (){

        userLogin();
        login(0);

    });



    $("#loginpanel li:eq(1)").click(function (){

        vueneLogin();
        login(1);

    });

    $("#loginpanel li:eq(2)").click(function (){

        adminLogin();
        login(2);
    });
});


function userLogin(){
    if ($("#loginpanel li:eq(0)").hasClass("active")){
        return;
    }

    $("#loginpanel li:eq(1)").removeClass("active");
    $("#loginpanel li:eq(2)").removeClass("active");
    $("#loginpanel li:eq(0)").addClass("active");

    $("input[name='username']").attr('placeholder',"用户名/会员卡号");
    $("div[role='register'] a[role='userRegister']").html("免费注册");
    $("div[role='register']").show();

    $("#link").attr('href','userrig.html');


}

function vueneLogin(){
    if ($("#loginpanel li:eq(1)").hasClass("active")){
        return;
    }

    $("#loginpanel li:eq(0)").removeClass("active");
    $("#loginpanel li:eq(2)").removeClass("active");
    $("#loginpanel li:eq(1)").addClass("active");
    $("input[name='username']").attr('placeholder',"商家识别码");
    $("div[role='register'] a[role='userRegister']").html("免费开店");
    $("div[role='register']").show();
    $("#link").attr('href','vuenerig.html');
}

function adminLogin(){
    if ($("#loginpanel li:eq(2)").hasClass("active")){
        return;
    }

    $("#loginpanel li:eq(0)").removeClass("active");
    $("#loginpanel li:eq(1)").removeClass("active");
    $("#loginpanel li:eq(2)").addClass("active");
    $("input[name='username']").attr('placeholder',"管理员账号");
    $("div[role='register']").hide();
}

function login(type){
    $('#loginButton').unbind("click");
    $("#loginButton").click(function(){
        if( $('#username').val().length<3||$('#password').val().length<3){
            swal("Error", "总该输入点什么东西才能登录吧:)", "error");
            return;
        }

        var result = new Object();
        result.username =  $('#username').val();
        result.password = $.md5($('#password').val());
        result.type = type;
        jQuery.ajax({
            url: '/login',
            type: 'post',
            dataType: 'json',
            data: result,
            success: function (data) {
                if (data.result == true) {
                    swal("登录成功", data.reason, "success");
                    var test = $.getUrlParam('isconfirm');
                    if ($.getUrlParam('isconfirm')== "true"){
                        var params = window.location.search;
                        top.location = 'order.html'+params;
                    }

                   if (type == 0){
                        top.location = 'userinfo.html'
                    }else if(type == 1){
                        top.location = 'vueneorder.html'
                    }else {
                        top.location = 'managerorder.html'
                    }
                } else {
                    swal("登录失败", data.reason, "error");
                }
            },
            error:function(data){
                swal("OMG", "服务器错误,请稍后重试!", "error");
            }
        });
    });
}