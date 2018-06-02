package com.olticketsbooking.ticketsbooking.dao;

import com.olticketsbooking.ticketsbooking.config.MessageInfo;
import com.olticketsbooking.ticketsbooking.model.BankCard;
import com.olticketsbooking.ticketsbooking.model.User;
import com.olticketsbooking.ticketsbooking.utils.ClassificationUtil;
import com.olticketsbooking.ticketsbooking.utils.DateUtil;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;


@Repository
public class UserDao {
    @Resource
    BaseDao baseDao;
    @Resource
    OrderDao orderDao;

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


    public MessageInfo updateUserPreference(User user){
        MessageInfo messageInfo=orderDao.findOrderPerformTypeByUserOriginal(user.getId());
        MessageInfo messageInfo1=orderDao.findOrderPerformTypeBetweenByUserOriginal(DateUtil.getPresentTimeLong()-DateUtil.getPassedDateLong(30),DateUtil.getPresentTimeLong(),user.getId());
        if (messageInfo.isResult()&&messageInfo1.isResult()){
            List<Object[]> list=(List<Object[]>)messageInfo.getObject();
            List<Object[]> list1=(List<Object[]>)messageInfo1.getObject();
            String[][] res=ClassificationUtil.getWeightedAverageValueOfUserPreference(ClassificationUtil.getPercentageOfPreference(list),0.6,ClassificationUtil.getPercentageOfPreference(list1),0.4);
            //System.out.print("******************      "+ClassificationUtil.getPreferencCombine(res)+"        *********************");
            user.setPreference(ClassificationUtil.getPreferencCombine(res));
            if(update(user).isResult()){
                return new MessageInfo(true,user.getPreference(),"更新用户爱好信息成功");
            }else {
                return new MessageInfo(false,"更新用户爱好信息失败");
            }

        }else{
            return new MessageInfo(false,"获取用户订单信息失败");
        }

    }

    public MessageInfo updateUserType(User user){
        MessageInfo messageInfo=orderDao.findOrderPerformTypeBetweenByUserOriginal(DateUtil.getPresentTimeLong()-DateUtil.getPassedDateLong(365),DateUtil.getPresentTimeLong(),user.getId());
        if (messageInfo.isResult()){
            List<Object[]> list=(List<Object[]>)messageInfo.getObject();
            int[] res=ClassificationUtil.getCountUserOrders(list);
            int type=ClassificationUtil.getUserType(res);
            if(type==0){
                int level=user.getLevel();
                if(level<5){
                    user.setUsertype(level);
                }else{
                    user.setUsertype(4);
                }
            }else{
                user.setUsertype(type+4);
            }
            if(update(user).isResult()){
                return new MessageInfo(true,user.getUsertype(),"更新用户分级信息成功");
            }else {
                return new MessageInfo(false,"更新用户分级信息失败");
            }
        }else{
            return new MessageInfo(false,"获取用户订单信息失败");
        }

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
