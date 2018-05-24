package com.olticketsbooking.ticketsbooking.service;

import com.olticketsbooking.ticketsbooking.config.MessageInfo;
import com.olticketsbooking.ticketsbooking.dao.BankCardDao;
import com.olticketsbooking.ticketsbooking.dao.UserDao;
import com.olticketsbooking.ticketsbooking.model.BankCard;
import com.olticketsbooking.ticketsbooking.model.User;
import com.olticketsbooking.ticketsbooking.vo.UserManagerVo;
import com.olticketsbooking.ticketsbooking.vo.UserVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service
public class UserService {
    @Resource
    UserDao userDao;
    @Resource
    BankCardDao bankCardDao;
    public MessageInfo findUser(String username)throws Exception{
        return userDao.findUser(username);
//        return new MessageInfo(false,"shibai");
    }


    public MessageInfo findUser(int userid){
        return userDao.findUser(userid);
    }

    public MessageInfo findUserByEmail(String email){
        return userDao.findUserByEmail(email);
    }

    public MessageInfo recharge(User user, String bankcard, String password, double money){
        MessageInfo messageInfo = bankCardDao.findCard(bankcard);

        if (money <0){
            return new MessageInfo(false,"金额输入有误!");
        }

        if (messageInfo.getObject() == null)
            return  new MessageInfo(false,"银行卡卡号或密码错误");

        if (messageInfo.isResult()){

            BankCard bankCard = (BankCard) messageInfo.getObject();



            //1判断密码是否正确
            if (bankCard.getPassword().equals(password)){
//                2判断卡上余额是否充足
                if (bankCard.getMoney()-money>=0){
//                    3判断扣款是否成功
                    if( userDao.recharge(user, bankCard,money).isResult()){
                            return new MessageInfo(true,"充值成功");

                    }
                    return new MessageInfo(true,"充值成功");

                } else {
                    return new MessageInfo(false,"服务器错误,请稍后再充值!");
                }

            }else {
                return new MessageInfo(false,"该银行卡余额不足");
            }

        }else {
            return  new MessageInfo(false,"银行卡卡号或密码错误");
        }
    }

    public MessageInfo updateInfo(int userid,String name,String email,String telephone ){
        MessageInfo messageInfo = userDao.findUser(userid);
        if (messageInfo.isResult()){

            User user =(User) messageInfo.getObject();
            user.setName(name);
            user.setEmail(email);
            user.setTelephone(telephone);
            return userDao.update(user);
        }else {
            return messageInfo;
        }
    }

    public MessageInfo updatePassword(int userid,String oldPassword,String newPassword){
        MessageInfo messageInfo = userDao.findUser(userid);
        if (messageInfo.isResult()){
            User user =(User) messageInfo.getObject();
            if (user.getPassword().equals(oldPassword)) {
                user.setPassword(newPassword);
                return userDao.update(user);
            }else {
                return new MessageInfo(false,"密码错误,请重新输入");
            }
        } else {
            return messageInfo;
        }

    }

    public MessageInfo getAllUserManagerInfo(){
        MessageInfo messageInfo = userDao.findAllUser();
        if (messageInfo.isResult()){
            List<User> users = (List<User>) messageInfo.getObject();
            List<UserVo> results = new ArrayList<UserVo>();
            for (User tem:users)
                results.add(new UserVo(tem));
            return new MessageInfo(true,results,"数据获取成功");
        }else {
            return messageInfo;
        }
    }


    public MessageInfo cancleUser(int id){
        User user=(User)userDao.findUser(id).getObject();
        user.setState(2);
        return   userDao.update(user);
    }
//
//    public MessageInfo activeUser(int id){
//        return userDao.activeUser(id);
//    }

//       积分充值
    public MessageInfo point2balance(int id,int num){
        MessageInfo messageInfo = userDao.findUser(id);
        if (messageInfo.isResult()){
            User user =(User) messageInfo.getObject();
            if (user.getPoint()<num)
                return new MessageInfo(false,"积分不足");
            else {
                double balance = (num+0.0)/(100.0);
                user.setBalance(user.getBalance()+balance);
                user.setPoint(user.getPoint()-num);
                return userDao.update(user);
            }
        }else
            return messageInfo;

    }

}
