package com.olticketsbooking.ticketsbooking.service;

import com.olticketsbooking.ticketsbooking.config.MessageInfo;
import com.olticketsbooking.ticketsbooking.dao.EmailDao;
import com.olticketsbooking.ticketsbooking.dao.UserDao;
import com.olticketsbooking.ticketsbooking.model.EmailActivation;
import com.olticketsbooking.ticketsbooking.model.User;
import com.olticketsbooking.ticketsbooking.utils.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;

@Service
public class EmailService {
    @Resource
    EmailDao emailDao;
    @Resource
    UserDao userDao;

    public MessageInfo sendActivationEmail(int userid,String email){
        String token=getRandomCode(32);
        while (emailDao.findEmailActivationByToken(token).getObject()!=null){
            token=getRandomCode(32);
        }
        emailDao.send(email,"票票账号注册激活邮件",
                "您的激活链接:http://localhost:8080/activation?userid="+userid+"&token="+token+"     点击该链接即可完成激活!");
        EmailActivation emailActivation=new EmailActivation();
        emailActivation.setActivationstate(0);
        emailActivation.setEmail(email);
        emailActivation.setStarttime(DateUtil.getPresentTimeLong());
        emailActivation.setEndtime(DateUtil.getPassedDateLong(1)+emailActivation.getStarttime());
        emailActivation.setToken(token);
        emailActivation.setUserid(userid);
        return emailDao.addEmailActivation(emailActivation);

    }

    public MessageInfo makeActivation(int userid,String token){
        MessageInfo messageInfo=emailDao.findEmailActivationByToken(token);
        if(messageInfo.getObject()==null){
            return new MessageInfo(false,"不存在该激活信息");
        }
        EmailActivation emailActivation=(EmailActivation)messageInfo.getObject();
        if(emailActivation.getUserid()!=userid){
            return new MessageInfo(false,"不存在该激活信息");
        }
        emailActivation.setActivationtime(DateUtil.getPresentTimeLong());
        emailActivation.setActivationstate(1);
        User user =(User)userDao.findUser(userid).getObject();
        user.setState(1);


        if(userDao.update(user).isResult()&& emailDao.update(emailActivation).isResult()){
            emailDao.send(emailActivation.getEmail(),"票票账号注册激活成功",
                    "尊敬的"+user.getUsername()+",您好,您的账号已经激活成功,祝您使用愉快");
            return new MessageInfo(true,"更新用户状态成功");
        }
        return  new MessageInfo(false,"更新用户注册信息失败,请重新尝试");
    }

    public String getRandomCode(int length){
        StringBuffer flag = new StringBuffer();
        for (int i = 0; i < length; i++)
        {
            String sources = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"; // 加上一些字母，就可以生成pc站的验证码了
            Random rand = new Random();


            flag.append(sources.charAt(rand.nextInt(48)) + "");


        }
        //            System.out.println(flag.toString());
        return flag.toString();
    }
}
