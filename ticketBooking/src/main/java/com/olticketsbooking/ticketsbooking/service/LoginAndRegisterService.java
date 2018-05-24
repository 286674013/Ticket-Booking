package com.olticketsbooking.ticketsbooking.service;

import com.olticketsbooking.ticketsbooking.config.MessageInfo;
import com.olticketsbooking.ticketsbooking.dao.ManagerDao;
import com.olticketsbooking.ticketsbooking.dao.UserDao;
import com.olticketsbooking.ticketsbooking.dao.VueneDao;
import com.olticketsbooking.ticketsbooking.model.Manager;
import com.olticketsbooking.ticketsbooking.model.User;
import com.olticketsbooking.ticketsbooking.model.Vuene;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class LoginAndRegisterService {
    @Resource
    UserDao userDao;
    @Resource
    VueneDao vueneDao;
    @Resource
    ManagerDao managerDao;

    public MessageInfo userLogin(String username, String password){

          MessageInfo messageInfo =  userDao.findUser(username);

        if (messageInfo.getObject() == null){
            return new MessageInfo(false,"用户名或密码错误");
        }
        if (messageInfo.isResult() == true){
            User user = (User) messageInfo.getObject();

            if(user.getState()==0){
                return new MessageInfo(false,"该用户尚未激活");
            }else if(user.getState()==2){
                return new MessageInfo(false,"该用户已被注销,请重新注册");
            }
           return checkPassword(user,user.getPassword(),password);
        }else {
            return messageInfo;
        }

    }
    public MessageInfo userRegister(User user){
        user.setState(0);
        user.setDiscount(0.95);
        user.setLevel(0);
        return userDao.register(user);
    }

    public MessageInfo vueneLogin(String vuenecode, String password){

        MessageInfo messageInfo =  vueneDao.findVuene(vuenecode);

        if (messageInfo.getObject() == null){
            return new MessageInfo(false,"用户名或密码错误");
        }
        if (messageInfo.isResult() == true){
            Vuene vuene = (Vuene) messageInfo.getObject();
            if(vuene.getState()==0){
                return new MessageInfo(false,"该场馆申请在审核中,请稍候");
            }else if(vuene.getState()==2){
                return new MessageInfo(false,"该场馆申请被驳回,请重新申请");
            }
            return checkPassword(vuene,vuene.getPassword(),password);

        }else {
            return messageInfo;
        }

    }

    public MessageInfo managerLogin(String username, String password){

        MessageInfo messageInfo =  managerDao.findManager(username);

        if (messageInfo.getObject() == null){
            return new MessageInfo(false,"用户名或密码错误");
        }
        if (messageInfo.isResult() == true){
            Manager manager = (Manager) messageInfo.getObject();


            return checkPassword(manager,manager.getPassword(),password);
        }else {
            return messageInfo;
        }

    }




    public MessageInfo vueneRegister(Vuene vuene){
        return vueneDao.register(vuene);
    }

    private MessageInfo checkPassword(Object object, String rightPassword, String inputPassword){
        if (rightPassword.equals(inputPassword))
            return new MessageInfo(true,object,"登录成功");
        else
            return new MessageInfo(false,"用户名或密码错误");

    }
}
