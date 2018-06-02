package com.olticketsbooking.ticketsbooking.dao;

import com.olticketsbooking.ticketsbooking.config.MessageInfo;
import com.olticketsbooking.ticketsbooking.model.*;
import com.olticketsbooking.ticketsbooking.utils.ClassificationUtil;
import com.olticketsbooking.ticketsbooking.utils.DateUtil;
import com.olticketsbooking.ticketsbooking.utils.MathUtil;
import com.olticketsbooking.ticketsbooking.utils.SeatsUtil;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class VueneDao {
    @Resource
    BaseDao baseDao;
    @Resource
    BankCardDao bankCardDao;
    @Resource
    OrderDao orderDao;
    @Resource
    PerformDao performDao;
    @Resource
    PlayroomDao playroomDao;

    public MessageInfo findVuene(String vuenecode){
        return   baseDao.findByPropertySingle(Vuene.class,"vuenecode",vuenecode);
    }

    public MessageInfo findVuenebyid(int vueneid){
        return baseDao.findById(Vuene.class,vueneid);
    }

    public MessageInfo register(Vuene vuene){
        return baseDao.save(vuene);
    }

    public MessageInfo update(Vuene vuene){
        return baseDao.update(vuene);
    }

    public MessageInfo getAllVuene(){
        return baseDao.getAll(Vuene.class);
    }

    public MessageInfo approveVuene(int vueneid){
        MessageInfo messageInfo=findVuenebyid(vueneid);
        if(messageInfo.getObject()==null){
            return new MessageInfo(false,"获取相关场馆信息失败");
        }
        Vuene vuene=(Vuene) messageInfo.getObject();
        vuene.setState(1);
        return baseDao.update(vuene);
    }

    public MessageInfo disapproveVuene(int vueneid){
        MessageInfo messageInfo=findVuenebyid(vueneid);
        if(messageInfo.getObject()==null){
            return new MessageInfo(false,"获取相关场馆信息失败");
        }
        Vuene vuene=(Vuene) messageInfo.getObject();
        vuene.setState(2);
        return baseDao.update(vuene);
    }

    public MessageInfo getUnapproveVuene() {

        MessageInfo messageInfo = baseDao.findByProperty(Vuene.class, "state", 0);
        if (messageInfo.isResult()) {

            return new MessageInfo(true, messageInfo.getObject(), "数据获取成功");
        } else
            return new MessageInfo(false, "数据获取失败");
    }

    public MessageInfo codeUsing(String code){
        MessageInfo messageInfo = baseDao.findByProperty(Vuene.class, "vuenecode", code);
        if (messageInfo.isResult()) {

            return new MessageInfo(true, messageInfo.getObject(), "数据获取成功");
        } else
            return new MessageInfo(false, "数据获取失败");
    }

    public MessageInfo getAllVueneNum(){
        Session session = baseDao.getSession();
        try{
            Long count = (Long ) session.createCriteria(Vuene.class).setProjection(Projections.rowCount()).uniqueResult();
            System.out.println("**************res:"+count);
            return new MessageInfo(true,count,"数据获取成功!");
        }catch (Exception e){
            e.printStackTrace();
            return  new MessageInfo(false,"数据获取失败!");
        }finally {
            session.close();
        }
    }
    public MessageInfo getmoney(int vueneid,String bankcardid,String password,double money){
        MessageInfo messageInfo = findVuenebyid(vueneid);
        if(messageInfo.getObject()==null){
            return new MessageInfo(false,"不存在该场馆信息");
        }
        Vuene vuene=(Vuene)messageInfo.getObject();
        messageInfo=bankCardDao.findCard(bankcardid);
        if(messageInfo.getObject()==null){
            return new MessageInfo(false,"卡号或密码错误");
        }
        BankCard bankCard=(BankCard)messageInfo.getObject();
        if(!bankCard.getPassword().equals(password)){
            return new MessageInfo(false,"卡号或密码错误");
        }

        if(vuene.getBalance()<money){
            return new MessageInfo(false,"余额不足以提现");
        }
        if(money<0){
            return new MessageInfo(false,"错误的金额");
        }
        Session session = baseDao.getSession();
        try {
            session.beginTransaction();

            money=MathUtil.roundNum(money,2);
            bankCard.setMoney(bankCard.getMoney()+money);
            vuene.setBalance(vuene.getBalance()-money);
            session.update(vuene);
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


    //获取场馆上个月的订单信息及当前(结算时刻)的场馆放映室信息
    public MessageInfo updateVueneTypeInfo(Vuene vuene){
        //上月订单数据(其实也可使用perform数据,但是扩拓展性弱)
        MessageInfo messageInfo= orderDao.findOrderBetweenTimeOriginal(DateUtil.getAfterLastMonthdate(2),DateUtil.getBeforeNextMonthdate(0),vuene.getVueneid());
//        MessageInfo messageInfo1= performDao.findAllPerformByVueneBetween(DateUtil.getAfterLastMonthdate(2),DateUtil.getBeforeNextMonthdate(0),vuene.getVueneid());
        MessageInfo messageInfo1=playroomDao.findRoomByVueneOriginal(vuene.getVueneid());
        if(messageInfo.isResult()&&messageInfo1.isResult()){
            List<Orders> lists =(List<Orders>)messageInfo.getObject();
//            List<Perform> lists1=(List<Perform>)messageInfo1.getObject();
            List<Playroom> list =(List<Playroom>)messageInfo1.getObject();
            int type=ClassificationUtil.getVueneType(lists,list);
            vuene.setVuenetype(type);
            if(update(vuene).isResult()){
                return new MessageInfo(true,type, "更新场馆类型信息成功!");
            }else{
                return new MessageInfo(false,type, "更新场馆类型信息失败!");
            }
        }else{
            return new MessageInfo(false, "获取场馆相关信息失败!");
        }
//        return new MessageInfo(false, "更新场馆类型信息失败!");
    }
}

