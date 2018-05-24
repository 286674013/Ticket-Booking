
$(function(){
    init();

    $('#confirm').click(function(){
       if (!checkInput()){
           jQuery.ajax({
               url: '/vuene/update/info',
               type: 'post',
               dataType: 'json',
               data:$('#form').serialize(),
               success: function (data) {
                   if(data.result == true) {
                       swal("成功", "信息已更新", "success");

                   }else {
                       swal("失败", "信息更新失败,请稍后再试", "error");
                   }
                   init();
               },
               error:function(data){
                   swal("OMG", "服务器错误,请稍后重试!", "error");
               }
           });
       }
    });
});

function init(){
    jQuery.ajax({
        url: '/vuene/updateinfo',
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if(data.result == true) {
                var object = data.object;
                console.log(object.vuenephone);
                $("input[name='vuenename']").val(object.vuenename);
                $("input[name='vueneaddress']").val(object.vueneaddress);
                $("input[name='vuenephone']").val(object.vuenephone);
                $("textarea[name='vuenedescription']").val(object.vuenedescription);
            }
        }
    });
}