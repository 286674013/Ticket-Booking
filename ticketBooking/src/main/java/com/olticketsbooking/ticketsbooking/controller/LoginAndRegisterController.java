package com.olticketsbooking.ticketsbooking.controller;

import com.olticketsbooking.ticketsbooking.config.MessageInfo;
import com.olticketsbooking.ticketsbooking.mail.SendEmail;
import com.olticketsbooking.ticketsbooking.model.Manager;
import com.olticketsbooking.ticketsbooking.model.User;
import com.olticketsbooking.ticketsbooking.model.Vuene;
import com.olticketsbooking.ticketsbooking.service.EmailService;
import com.olticketsbooking.ticketsbooking.service.LoginAndRegisterService;
import com.olticketsbooking.ticketsbooking.service.UserService;
import com.olticketsbooking.ticketsbooking.service.VueneService;
import com.olticketsbooking.ticketsbooking.vo.LoginVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class LoginAndRegisterController {
    @Resource
    LoginAndRegisterService service;
    @Resource
    UserService userService;
    @Resource
    VueneService vueneService;
    @Resource
    EmailService emailService;

    @RequestMapping("/login")
    @ResponseBody
    //0用户 1商家 2管理员
    public MessageInfo login(HttpSession session, String username, String password, int type) {

        MessageInfo messageInfo = null;
        switch (type) {
            case 0:
                messageInfo = service.userLogin(username, password);
                if (messageInfo.isResult() == true)
                    session.setAttribute("user", (User) messageInfo.getObject());
                break;
            case 1:
                messageInfo = service.vueneLogin(username, password);
                if (messageInfo.isResult() == true)
                    session.setAttribute("vuene", (Vuene) messageInfo.getObject());

                break;
            case 2:
                messageInfo = service.managerLogin(username, password);
                if (messageInfo.isResult() == true)
                    session.setAttribute("manager", (Manager) messageInfo.getObject());
                break;
        }


        return messageInfo;
    }

    @RequestMapping("/logoutMain")
    @ResponseBody
    public MessageInfo logoutMain(HttpSession session) {
        try {
            session.setAttribute("user", null);
            session.setAttribute("vuene", null);
            session.setAttribute("manager", null);
        }catch (Exception e){
            e.printStackTrace();
            return new MessageInfo(false,"登出失败,请稍后重试");
        }
        return new MessageInfo(true,"登出成功");

    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.setAttribute("user", null);
        session.setAttribute("vuene", null);
        session.setAttribute("manager", null);
        return "index";
    }

    @RequestMapping("/islogin")
    @ResponseBody
    public LoginVo isLogin(HttpSession session) {
        Object object = session.getAttribute("user");
        if (object != null)
            return new LoginVo(((User) object).getName(), 0);
      object = session.getAttribute("vuene");
        if (object != null)
            return new LoginVo(((Vuene) object).getVuenename(), 1);
        object = session.getAttribute("manager");
        if (object != null)
            return new LoginVo(((Manager) object).getName(), 2);
        return new LoginVo(null,3);
    }

    @RequestMapping("/register/user")
    @ResponseBody
    public MessageInfo registerUser(HttpSession session, User user, HttpServletRequest request) throws Exception{
//        user=new User(request.getParameter("username"),request.getParameter("name"),request.getParameter("password"),request.getParameter("email"),request.getParameter("telephone"));
        Object tem = userService.findUser(user.getUsername()).getObject();
        if (tem != null) {
            return new MessageInfo(false, "该用户名已经被注册");
        }
        tem=userService.findUserByEmail(user.getEmail()).getObject();
        if (tem != null) {
            return new MessageInfo(false, "该邮箱已经被注册");
        }
        MessageInfo messageInfo = service.userRegister(user);
        emailService.sendActivationEmail(user.getId(),user.getEmail());
//        if (messageInfo.isResult() == true) {
//            session.setAttribute("user", (User) userService.findUser(user.getUsername()).getObject());
//        }

        return messageInfo;
    }

    @RequestMapping("/register/vuene")
    @ResponseBody
    public MessageInfo registerVuene(HttpSession session, Vuene vuene) {
        //场馆(不)需要考虑重名问题(连锁)_____
        Object tem = vueneService.findVuene(vuene.getVuenename()).getObject();
//        System.out.println(tem.toString());
        if (tem != null) {
            return new MessageInfo(false, "该场馆名已经被注册");
        }
        String randomcode=vueneService.getRandomCode(7);
        while(vueneService.codeUsing(randomcode).isResult()){
            System.out.println(randomcode +"  "+vueneService.codeUsing(randomcode).isResult());
            randomcode=vueneService.getRandomCode(7);
        }
        vuene.setVuenecode(randomcode);
        MessageInfo messageInfo = service.vueneRegister(vuene);
//        if (messageInfo.isResult() == true) {
//            messageInfo.setObject((Vuene) vueneService.findVuene(vuene.getVuenecode()).getObject());
//            session.setAttribute("vuene", (Vuene) vueneService.findVuene(vuene.getVuenecode()).getObject());
//        }
        return messageInfo;
    }

    @RequestMapping("/activation")
    @ResponseBody
    public  MessageInfo activation(int userid,String token){
        return emailService.makeActivation(userid,token);
    }



}
