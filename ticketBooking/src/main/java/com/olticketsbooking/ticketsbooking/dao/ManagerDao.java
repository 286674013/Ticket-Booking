package com.olticketsbooking.ticketsbooking.dao;

import com.olticketsbooking.ticketsbooking.config.MessageInfo;
import com.olticketsbooking.ticketsbooking.model.BankCard;
import com.olticketsbooking.ticketsbooking.model.Manager;
import com.olticketsbooking.ticketsbooking.model.Vuene;
import com.olticketsbooking.ticketsbooking.utils.MathUtil;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
@Repository
public class ManagerDao {
    @Resource
    BaseDao baseDao;
    @Resource
    BankCardDao bankCardDao;

    public MessageInfo findManager(String username){
        return  baseDao.findByPropertySingle(Manager.class,"username",username);
    }
    public MessageInfo findManagerById(int id){
        return  baseDao.findById(Manager.class,id);
    }

    public MessageInfo update(Manager manager){
        return baseDao.update(manager);
    }

    public MessageInfo getmoney(int id,String bankcardid,String password,double money){
        MessageInfo messageInfo = findManagerById(id);
        if(messageInfo.getObject()==null){
            return new MessageInfo(false,"不存在该管理员信息");
        }
        Manager manager=(Manager) messageInfo.getObject();
        messageInfo=bankCardDao.findCard(bankcardid);
        if(messageInfo.getObject()==null){
            return new MessageInfo(false,"卡号或密码错误");
        }
        BankCard bankCard=(BankCard)messageInfo.getObject();
        if(!bankCard.getPassword().equals(password)){
            return new MessageInfo(false,"卡号或密码错误");
        }

        if(manager.getBalance()<money){
            return new MessageInfo(false,"余额不足以提现");
        }
        if(money<0){
            return new MessageInfo(false,"错误的金额");
        }
        Session session = baseDao.getSession();
        try {
            session.beginTransaction();

            money= MathUtil.roundNum(money,2);
            bankCard.setMoney(bankCard.getMoney()+money);
            manager.setBalance(manager.getBalance()-money);
            session.update(manager);
            session.update(bankCard);
            session.getTransaction().commit();
            session.clear();
            return new MessageInfo(true, "提现"+money+"成功!");
        } catch (Exception e) {
            e.printStackTrace();
            if (session != null) {
                session.getTransaction().rollback();
            }
            return new MessageInfo(false, "提现失败,请重新尝试!");
        } finally {
            session.close();
        }
    }
}
