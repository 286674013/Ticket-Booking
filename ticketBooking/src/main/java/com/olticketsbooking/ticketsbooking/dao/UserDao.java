package com.olticketsbooking.ticketsbooking.dao;

import com.olticketsbooking.ticketsbooking.config.MessageInfo;
import com.olticketsbooking.ticketsbooking.model.BankCard;
import com.olticketsbooking.ticketsbooking.model.User;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;


@Repository
public class UserDao {
    @Resource
    BaseDao baseDao;

    public MessageInfo findUser(String username){
        return baseDao.findByPropertySingle(User.class,"username",username);
    }
    public MessageInfo findUserByEmail(String email){
        return baseDao.findByPropertySingle(User.class,"email",email);
    }

    public MessageInfo findUser(int userid){
        return baseDao.findById(User.class,userid);
    }

    public MessageInfo register(User user){
        return baseDao.save(user);
    }

    public MessageInfo update(User user){
        return baseDao.update(user);
    }

    public MessageInfo recharge(User user, BankCard bankCard, double money){

        Session session = baseDao.getSession();
        try {
            session.beginTransaction();
            user.setBalance(user.getBalance()+money);
            bankCard.setMoney(bankCard.getMoney()-money);
            session.update(user);
            session.update(bankCard);
            session.getTransaction().commit();
            session.clear();
            return new MessageInfo(true,"充值成功!");
        }catch (Exception e){
            e.printStackTrace();
            if (session!=null) {
                session.getTransaction().rollback();
            }
            return  new MessageInfo(false,"充值失败!");
        }finally {
            session.close();
        }
    }

    public MessageInfo findAllUser(){
        return baseDao.getAll(User.class);
    }

    public MessageInfo getAllUser(){
        return baseDao.getAll(User.class);
    }

    public MessageInfo getAllUsersNum(){
        Session session = baseDao.getSession();
        try{
            Long count = (Long ) session.createCriteria(User.class).setProjection(Projections.rowCount()).uniqueResult();
            return new MessageInfo(true,count,"数据获取成功!");
        }catch (Exception e){
            e.printStackTrace();
            return  new MessageInfo(false,"数据获取失败!");
        }finally {
            session.close();
        }
    }




}
