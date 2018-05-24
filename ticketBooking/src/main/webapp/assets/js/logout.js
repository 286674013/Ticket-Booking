/**
 * Created by StevenWu on 17/2/14.
 */

$(function(){
   $('#logout').click(function(){
       jQuery.ajax({
           url: '/logoutMain',
           type: 'post',
           dataType: 'json',
           success: function (data) {
               if (data.result == true) {
                   top.location = '/index.html';
               } else {
                   swal("登录失败", data.reason, "error");
               }
           },
           error:function(data){
               swal("OMG", "服务器错误,请稍后重试!", "error");
           }
       });

   });


});