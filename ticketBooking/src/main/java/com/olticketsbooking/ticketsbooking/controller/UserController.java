package com.olticketsbooking.ticketsbooking.controller;

import com.olticketsbooking.ticketsbooking.config.MessageInfo;
import com.olticketsbooking.ticketsbooking.model.Manager;
import com.olticketsbooking.ticketsbooking.model.User;
import com.olticketsbooking.ticketsbooking.model.Vuene;
import com.olticketsbooking.ticketsbooking.service.ManagerService;
import com.olticketsbooking.ticketsbooking.service.UserService;
import com.olticketsbooking.ticketsbooking.vo.UserVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
@Controller
public class UserController {
    @Resource
    UserService userService;



    @Resource
    ManagerService managerService;

    @RequestMapping("/user/manager/info")
    @ResponseBody
    public MessageInfo updateMangerInfo(HttpSession session){
        Manager manager = (Manager) session.getAttribute("manager");
        if (manager == null){
            return new MessageInfo(false,"登录信息获取失败,请重新登录");
        }
        return managerService.getInfo(manager.getManagerid());
    }

    @RequestMapping("/user/manager/update")
    @ResponseBody
    public MessageInfo updateMangerInfo(HttpSession session,String name,double percent,String email,String telephone){
        Manager manager = (Manager) session.getAttribute("manager");
        if (manager == null){
            return new MessageInfo(false,"登录信息获取失败,请重新登录");
        }
        return managerService.update(manager,name,percent,email,telephone);
    }

    @RequestMapping("/user/discount")
    @ResponseBody
    public MessageInfo getDiscount(HttpSession session){

        User user = (User) session.getAttribute("user");
        if (user != null){
            return  new MessageInfo(true,user.getDiscount(),"折扣获取成功");
        }else {
            return new MessageInfo(false,"折扣获取失败");
        }
    }

    @RequestMapping("/user/info")
    @ResponseBody
    public MessageInfo getUserInfo(HttpSession session){
        User user  = (User) session.getAttribute("user");
        MessageInfo messageInfo = userService.findUser(user.getId());
        if (messageInfo.isResult()){
            user = (User) messageInfo.getObject();
            return new MessageInfo(true,new UserVo(user) ,"用户信息获取成功!");
        }else {
            return messageInfo;
        }


    }

    @RequestMapping("/user/recharge")
    @ResponseBody
    public MessageInfo recharge(HttpSession session,String bankcard,String password,double money){

        return userService.recharge((User) session.getAttribute("user"),bankcard,password,money);
    }

    @RequestMapping("/user/update/info")
    @ResponseBody
    public MessageInfo updateInfo(HttpSession session,String name,String email,String telephone){
        User user  = (User) session.getAttribute("user");
        if (user != null){
            return   userService.updateInfo(user.getId(),name,email,telephone);
        }else
            return new MessageInfo(false,"请登录");
    }
    @RequestMapping("/user/update/password")
    @ResponseBody
    public MessageInfo updatePassword(HttpSession session,String oldPassword,String newPassword1,String newPassword2){
        User user  = (User) session.getAttribute("user");
        if (user != null){
            if (newPassword1.equals(newPassword2)){
                return userService.updatePassword(user.getId(),oldPassword,newPassword1);
            }else {
                return new MessageInfo(false,"新密码不一致,请重新输入!");
            }
        }else
            return new MessageInfo(false,"请登录");
    }

    @RequestMapping("/user/list")
    @ResponseBody
    public Object getAllUserManagerInfo(HttpSession session){
        Manager manager  = (Manager) session.getAttribute("manager");
        if (manager!=null){
            return userService.getAllUserManagerInfo();
        }else {
            return new MessageInfo(false,"请登录");
        }
    }

    @RequestMapping("/user/transform")
    @ResponseBody
    public MessageInfo point2balance(HttpSession session,int num){
        User user  = (User) session.getAttribute("user");
        if (user != null){
            return    userService.point2balance(user.getId(),num);

        }else
            return new MessageInfo(false,"请登录");
    }



    @RequestMapping("/user/manager/cancle")
    @ResponseBody
    public MessageInfo cancleUser(HttpSession session,String[] userid) {
        Manager manager = (Manager) session.getAttribute("manager");
        boolean alldone = true;
        if (manager == null) {
            return new MessageInfo(false, "登录信息获取失败,请重新登录");
        }
        for (String tem : userid) {
            if (!userService.cancleUser(Integer.parseInt(tem)).isResult()) {
                alldone = false;
            }
        }
        if (!alldone) {
            return new MessageInfo(true, "部分用户注销失败,请确认该用户是否可以取消");
        }


        return new MessageInfo(true, "所选用户已经全部注销");
    }


    @RequestMapping("/user/manager/financeinfo")
    @ResponseBody
    public MessageInfo getManagerFinanceInfo(HttpSession session){
        Manager manager = (Manager) session.getAttribute("manager");
        if (manager == null) {
            return new MessageInfo(false, "登录信息获取失败,请重新登录");
        }
        return managerService.getManagerFianceInfo();

    }

    @RequestMapping("/user/manager/getmoney")
    @ResponseBody
    public MessageInfo getmoney(HttpSession session,String bankcardid,String password,double money){
        Manager object =  (Manager) session.getAttribute("manager");
        if (object == null)
            return new MessageInfo(false,"请登录");

        return managerService.getmoney(object.getManagerid(),bankcardid,password,money);
    }
}
