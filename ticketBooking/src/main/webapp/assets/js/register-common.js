
function arrayTOjson(node) {
    var b = "{";
    for (var i = 0; i < node.length; i++) {
        b = b + "\"" + node[i].name + "\":\"" + node[i].value + "\",";
    }
    b = b.substring(0, b.length - 1) + "}";
    return b;
};
function register(url, id, type) {
    url = "/register/"+url;
    var form = $("#" + id);
    var reformval = $("#password").val();

    var test = checkInput();
    var testData= {
        "username":"chr","telephone":"130204343","name":"chrrr","password":"123456","email":"2886674013@qq.com"};
    if (!checkInput()) {
        $("#password").val($.md5(reformval));
        jQuery.ajax({
            url: url,
            type: 'post',
            dataType: 'json',
            // contentType:'application/json;charset=UTF-8',
            data: form.serialize(),
            // data: JSON.stringify(testData),
            async:false,
            success: function (data) {
                if (data.result == true) {
                    if (type == 0) {

                        swal("注册成功", data.reason, "success");
                    } else if (type == 1) {

                        // alert(data.object);
                        swal("注册成功", "登录码:"+data.object.vuenecode, "success");

                    }
                    top.location = '/assets/page/login.html'

                } else {
                    swal("注册失败", data.reason, "error");
                }

            },
            error: function (data) {
                swal("OMG", "服务器错误,请稍后重试!", "error");

            }
        });
    }
    $("#password").val(reformval);

}