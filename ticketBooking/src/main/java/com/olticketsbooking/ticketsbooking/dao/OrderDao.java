package com.olticketsbooking.ticketsbooking.dao;

import com.olticketsbooking.ticketsbooking.config.MessageInfo;
import com.olticketsbooking.ticketsbooking.model.*;
import com.olticketsbooking.ticketsbooking.utils.DateUtil;
import com.olticketsbooking.ticketsbooking.utils.MathUtil;
import com.olticketsbooking.ticketsbooking.utils.SeatsUtil;
import com.olticketsbooking.ticketsbooking.vo.OrderFinaceManagerInfoVo;
import com.olticketsbooking.ticketsbooking.vo.OrderVo;
import com.olticketsbooking.ticketsbooking.vo.VueneFinaceCountVo;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDao {
    @Resource
    BaseDao baseDao;
    @Resource
    ManagerDao managerDao;
    @Resource
    VueneDao vueneDao;
    @Resource
    UserDao userDao;
    @Resource
    PerformDao performDao;
    public MessageInfo addOrder(Orders orders) {
        return baseDao.save(orders);
    }

    public MessageInfo update(Orders orders) {
        return baseDao.update(orders);
    }

    public MessageInfo findOrder(int id) {
        return baseDao.findById(Orders.class, id);
    }

    public MessageInfo findOrderByUser(int userid) {
        Session session = baseDao.getSession();
        try {
            String queryString = "from " + Orders.class.getSimpleName()
                    + " as model where model.userid  =:userid ";
            Query queryObject = session.createQuery(queryString);
            queryObject.setInteger("userid", userid);
            List<Orders> lists = queryObject.list();
            List<OrderVo> result = new ArrayList<OrderVo>();
            for (Orders o : lists) {
                result.add(new OrderVo(o));
            }
            return new MessageInfo(true, result, "数据获取成功");
        } catch (Exception re) {
            re.printStackTrace();
            return new MessageInfo(false, "数据获取失败");
        } finally {
            session.close();
        }

    }


    public MessageInfo findOrderByVuene(int vueneid) {
        Session session = baseDao.getSession();
        try {
            String queryString = "from " + Orders.class.getSimpleName()
                    + " as model where model.vueneid  =:vueneid ";
            Query queryObject = session.createQuery(queryString);
            queryObject.setInteger("vueneid", vueneid);
            List<Orders> lists = queryObject.list();
            List<OrderVo> result = new ArrayList<OrderVo>();
            for (Orders o : lists) {
                result.add(new OrderVo(o));
            }
            return new MessageInfo(true, result, "数据获取成功");
        } catch (Exception re) {
            re.printStackTrace();
            return new MessageInfo(false, "数据获取失败");
        } finally {
            session.close();
        }

    }

    public MessageInfo findOrderBetweenTime(long begintime,long endtime) {
        Session session = baseDao.getSession();
        try {
            String queryString = "from " + Orders.class.getSimpleName()
                    + " as model where model.starttime  >:begintime and model.starttime  <:endtime ";
            Query queryObject = session.createQuery(queryString);
            queryObject.setLong("begintime", begintime);
            queryObject.setLong("endtime", endtime);
            List<Orders> lists = queryObject.list();

            return new MessageInfo(true, lists, "数据获取成功");
        } catch (Exception re) {
            re.printStackTrace();
            return new MessageInfo(false, "数据获取失败");
        } finally {
            session.close();
        }

    }
    public MessageInfo findOrderBetweenTime(long begintime,long endtime,int vueneid) {
        Session session = baseDao.getSession();
        try {
            String queryString = "from " + Orders.class.getSimpleName()
                    + " as model where model.starttime  >:begintime and model.starttime  <:endtime and model.vueneid=:vueneid ";
            Query queryObject = session.createQuery(queryString);
            queryObject.setLong("begintime", begintime);
            queryObject.setLong("endtime", endtime);
            queryObject.setLong("vueneid", vueneid);
            List<Orders> lists = queryObject.list();

            return new MessageInfo(true, lists, "数据获取成功");
        } catch (Exception re) {
            re.printStackTrace();
            return new MessageInfo(false, "数据获取失败");
        } finally {
            session.close();
        }

    }



    public MessageInfo allOrder() {

        return baseDao.getAll(Orders.class);

    }
    public MessageInfo cancleOrder(Orders orders) {
        MessageInfo messageInfo1=performDao.findPerformById(orders.getPerformid());
        Perform perform=(Perform)messageInfo1.getObject();
        Session session = baseDao.getSession();
        try {
            session.beginTransaction();
            orders.setOrderstate(2);
            orders.setCancletime(DateUtil.getPresentTimeLong());
            perform.setVacancy(SeatsUtil.getSeatsBack(orders.getSeats(),perform.getVacancy()));
            perform.setCountvacancy(perform.getCountvacancy()+SeatsUtil.getSeatFromInput(orders.getSeats()).length);
            session.update(perform);
            session.update(orders);
            session.getTransaction().commit();
            session.clear();
            return new MessageInfo(true, "订单取消成功!");
        } catch (Exception e) {
            e.printStackTrace();
            if (session != null) {
                session.getTransaction().rollback();
            }
            return new MessageInfo(false, "订单取消失败,请重新尝试!");
        } finally {
            session.close();
        }
    }


    public MessageInfo cancleOrderByBalance(Orders orders,double percent) {
        MessageInfo messageInfo=userDao.findUser(orders.getUserid());
        User user=(User)messageInfo.getObject();
        MessageInfo messageInfo1=managerDao.findManagerById(1);
        Manager manager=(Manager)messageInfo1.getObject();
        MessageInfo messageInfo2=vueneDao.findVuenebyid(orders.getVueneid());
        Vuene vuene=(Vuene) messageInfo2.getObject();
        MessageInfo messageInfo3=performDao.findPerformById(orders.getPerformid());
        Perform perform=(Perform)messageInfo3.getObject();
        Session session = baseDao.getSession();
        try {
            session.beginTransaction();
            if(orders.getPaytime()!=0) {
                user.setBalance(MathUtil.roundNum(user.getBalance() + orders.getPrice() * percent, 2));
                manager.setBalance(MathUtil.roundNum(manager.getBalance() - manager.getPercent() * orders.getPrice() * percent, 2));
                vuene.setBalance(MathUtil.roundNum(vuene.getBalance() - (1 - manager.getPercent()) * orders.getPrice() * percent, 2));
                user.setPoint(MathUtil.roundIntNum(user.getPoint() - orders.getPrice()));
                user.setCountspending(MathUtil.roundNum(user.getCountspending() - orders.getPrice(), 2));
                user.setCountorders(MathUtil.roundIntNum(user.getCountorders() - 1));
                user.setLevel(getUserlevelUpdate(user.getCountspending()));
                user.setDiscount(getUserdiscountUpdate(user.getCountspending()));
            }
            orders.setOrderstate(2);
            orders.setPaymethod(0);
            orders.setCancletime(DateUtil.getPresentTimeLong());
            perform.setVacancy(SeatsUtil.getSeatsBack(orders.getSeats(),perform.getVacancy()));
            perform.setCountvacancy(perform.getCountvacancy()+SeatsUtil.getSeatFromInput(orders.getSeats()).length);
            session.update(perform);
            session.update(user);
            session.update(orders);
            session.update(manager);
            session.update(vuene);
            session.getTransaction().commit();
            session.clear();
            return new MessageInfo(true, "订单取消成功!");
        } catch (Exception e) {
            e.printStackTrace();
            if (session != null) {
                session.getTransaction().rollback();
            }
            return new MessageInfo(false, "订单取消失败,请重新尝试!");
        } finally {
            session.close();
        }
    }

    public MessageInfo payOrderByBalance(User user, Orders orders) {
        MessageInfo messageInfo1=managerDao.findManagerById(1);
        Manager manager=(Manager)messageInfo1.getObject();
        MessageInfo messageInfo2=vueneDao.findVuenebyid(orders.getVueneid());
        Vuene vuene=(Vuene) messageInfo2.getObject();
        Session session = baseDao.getSession();
        try {
            session.beginTransaction();
            if(user.getBalance()<orders.getPrice()){
                return new MessageInfo(false, "订单支付失败(余额不足),请重新支付!");
            }
            user.setBalance(MathUtil.roundNum(user.getBalance() - orders.getPrice(), 2));
            manager.setBalance(MathUtil.roundNum(manager.getBalance()+manager.getPercent()*orders.getPrice(),2));
            vuene.setBalance(MathUtil.roundNum(vuene.getBalance()+(1-manager.getPercent())*orders.getPrice(),2));
            user.setPoint(MathUtil.roundIntNum(user.getPoint() + orders.getPrice()));
            user.setCountspending(MathUtil.roundNum(user.getCountspending() + orders.getPrice(), 2));
            user.setCountorders(MathUtil.roundIntNum(user.getCountorders() + 1));
            user.setLevel(getUserlevelUpdate(user.getCountspending()));
            user.setDiscount(getUserdiscountUpdate(user.getCountspending()));
            orders.setOrderstate(1);
            orders.setPaymethod(1);
            orders.setPaytime(DateUtil.getPresentTimeLong());
            session.update(user);
            session.update(orders);
            session.update(manager);
            session.update(vuene);
            session.getTransaction().commit();
            session.clear();
            return new MessageInfo(true, "订单支付成功!");
        } catch (Exception e) {
            e.printStackTrace();
            if (session != null) {
                session.getTransaction().rollback();
            }
            return new MessageInfo(false, "订单支付失败,请重新支付!");
        } finally {
            session.close();
        }
    }

    public MessageInfo payOrderByBank(BankCard bankCard, User user, Orders orders) {
        MessageInfo messageInfo1=managerDao.findManagerById(1);
        Manager manager=(Manager)messageInfo1.getObject();
        MessageInfo messageInfo2=vueneDao.findVuenebyid(orders.getVueneid());
        Vuene vuene=(Vuene) messageInfo2.getObject();
        Session session = baseDao.getSession();
        try {
            session.beginTransaction();
            if(bankCard.getMoney()<orders.getPrice()){
                return new MessageInfo(false, "订单支付失败(余额不足),请重新支付!");
            }
            bankCard.setMoney(MathUtil.roundNum(bankCard.getMoney() - orders.getPrice(), 2));
            manager.setBalance(MathUtil.roundNum(manager.getBalance()+manager.getPercent()*orders.getPrice(),2));
            vuene.setBalance(MathUtil.roundNum(vuene.getBalance()+(1-manager.getPercent())*orders.getPrice(),2));
            user.setPoint(MathUtil.roundIntNum(user.getPoint() + orders.getPrice()));
            user.setCountspending(MathUtil.roundNum(user.getCountspending() + orders.getPrice(), 2));
            user.setCountorders(MathUtil.roundIntNum(user.getCountorders() + 1));

            orders.setOrderstate(1);
            orders.setPaymethod(2);
            orders.setPaytime(DateUtil.getPresentTimeLong());
            user.setLevel(getUserlevelUpdate(user.getCountspending()));
            user.setDiscount(getUserdiscountUpdate(user.getCountspending()));

            session.update(user);
            session.update(orders);
            session.update(bankCard);
            session.update(manager);
            session.update(vuene);
            session.getTransaction().commit();
            session.clear();
            return new MessageInfo(true, "订单支付成功!");
        } catch (Exception e) {
            e.printStackTrace();
            if (session != null) {
                session.getTransaction().rollback();
            }
            return new MessageInfo(false, "订单支付失败,请重新支付!");
        } finally {
            session.close();
        }
    }

    public MessageInfo payOrderByCash(User user, Orders orders) {
        MessageInfo messageInfo1=managerDao.findManagerById(1);
        Manager manager=(Manager)messageInfo1.getObject();
        MessageInfo messageInfo2=vueneDao.findVuenebyid(orders.getVueneid());
        Vuene vuene=(Vuene) messageInfo2.getObject();
        Session session = baseDao.getSession();
        try {
            session.beginTransaction();
            manager.setBalance(MathUtil.roundNum(manager.getBalance()+manager.getPercent()*orders.getPrice(),2));
            vuene.setBalance(MathUtil.roundNum(vuene.getBalance()-manager.getPercent()*orders.getPrice(),2));
            user.setPoint(MathUtil.roundIntNum(user.getPoint() + orders.getPrice()));
            user.setCountspending(MathUtil.roundNum(user.getCountspending() + orders.getPrice(), 2));
//            System.out.println(MathUtil.roundNum(manager.getBalance()+manager.getPercent()*orders.getPrice(),2));
            user.setCountorders(MathUtil.roundIntNum(user.getCountorders() + 1));
            user.setLevel(getUserlevelUpdate(user.getCountspending()));
            user.setDiscount(getUserdiscountUpdate(user.getCountspending()));
            orders.setOrderstate(1);
            orders.setPaymethod(0);
            orders.setPaytime(DateUtil.getPresentTimeLong());
            session.update(user);
            session.update(orders);
            session.update(manager);
            session.update(vuene);
            session.getTransaction().commit();
            session.clear();
            return new MessageInfo(true, "订单支付成功!");
        } catch (Exception e) {
            e.printStackTrace();
            if (session != null) {
                session.getTransaction().rollback();
            }
            return new MessageInfo(false, "订单支付失败,请重新支付!");
        } finally {
            session.close();
        }
    }

    public double getUserdiscountUpdate(double countspending){
        double level=1;
        if(countspending>1000000){
           level=0.66;
        }else if(countspending>800000){
            level=0.68;
        }
        else if(countspending>500000){
            level=0.7;
        }
        else if(countspending>200000){
            level=0.75;
        }
        else if(countspending>100000){
            level=0.77;
        }
        else if(countspending>50000){
            level=0.8;
        }
        else if(countspending>20000) {
            level=0.83;
        }
        else if(countspending>10000){
            level=0.85;
        }
        else if(countspending>5000){
            level=0.88;
        }
        else if(countspending>1000){
            level=0.9;
        }else{
            level=0.95;
        }
        return level;
    }
    public int getUserlevelUpdate(double countspending){
        int level=0;
        if(countspending>1000000){
            level=10;
        }else if(countspending>800000){
            level=9;
        }
        else if(countspending>500000){
            level=8;
        }
        else if(countspending>200000){
            level=7;
        }
        else if(countspending>100000){
            level=6;
        }
        else if(countspending>50000){
            level=5;
        }
        else if(countspending>20000) {
            level=4;
        }
        else if(countspending>10000){
            level=3;
        }
        else if(countspending>5000){
            level=2;
        }
        else if(countspending>1000){
            level=1;
        }else{
            level=0;
        }
        return level;
    }
    public MessageInfo orderPay(Vuene vuene,int performid,int userid,String seats,double discount) {
        MessageInfo messageInfo = performDao.findPerformById(performid);
        Perform perform = (Perform) messageInfo.getObject();
        MessageInfo messageInfo1=managerDao.findManagerById(1);
        Manager manager=(Manager)messageInfo1.getObject();
        MessageInfo messageInfo2=userDao.findUser(userid);

        Orders orders = new Orders();


        Session session = baseDao.getSession();
        try {
            session.beginTransaction();


                //初始化订单状态
                orders.setOrderstate(0);
                //初始化座位信息
                int seatlength = 1;
                String seatsToChoose = "";
                seatlength = SeatsUtil.getSeatFromInput(seats).length;
                if (!SeatsUtil.checkAllVacant(seats, perform.getVacancy())) {
                    return new MessageInfo(false, "啊哦,你选的座位已经被别人抢走啦!");
                } else {
                    seatsToChoose = SeatsUtil.getSeatStringForDisplay(SeatsUtil.getSeatFromInput(seats));
                    perform.setVacancy(SeatsUtil.updateSeats(seats, perform.getVacancy()));
                }
                if (seatlength == 0) {
                    return new MessageInfo(false, "错误的座位信息");
                } else if (seatlength > perform.getCountvacancy()) {
                    return new MessageInfo(false, "已经没有这么多空位了");
                }

                perform.setCountvacancy(perform.getCountvacancy() - seatlength);
                performDao.update(perform);
                //初始化折扣信息
                orders.setDiscount(discount);
                //初始化价格信息
                double price = MathUtil.roundNum(perform.getSeatprice() * orders.getDiscount(), 2);

                orders.setStarttime(DateUtil.getPresentTimeLong());
                orders.setOriginalprice(MathUtil.roundNum(perform.getSeatprice() * seatlength, 2));
                orders.setPrice(MathUtil.roundNum(orders.getOriginalprice()*discount,2));
                orders.setCouponid(0);
                orders.setPerformdescription(perform.getPerformdescription());
                orders.setPerformid(perform.getPerformid());
                orders.setPerformname(perform.getPerformname());
                orders.setUserid(userid);
                orders.setVueneid(vuene.getVueneid());
                orders.setVuenename(vuene.getVuenename());
                orders.setVueneaddress(vuene.getVueneaddress());
                orders.setPerformstart(perform.getBegintime());
                orders.setPerformend(perform.getEndtime());
                orders.setRemark("线下购买");
                orders.setSeats(seatsToChoose);

            manager.setBalance(MathUtil.roundNum(manager.getBalance()+manager.getPercent()*orders.getPrice(),2));
            vuene.setBalance(MathUtil.roundNum(vuene.getBalance()-manager.getPercent()*orders.getPrice(),2));
            if(messageInfo2.getObject()!=null){
                User user=(User)messageInfo2.getObject();
                user.setPoint(MathUtil.roundIntNum(user.getPoint() + orders.getPrice()));
                user.setCountspending(MathUtil.roundNum(user.getCountspending() + orders.getPrice(), 2));
                user.setCountorders(MathUtil.roundIntNum(user.getCountorders() + 1));
                user.setLevel(getUserlevelUpdate(user.getCountspending()));
                user.setDiscount(getUserdiscountUpdate(user.getCountspending()));
                session.update(user);
                orders.setUsername(user.getUsername());
            }
            orders.setOrderstate(4);
            orders.setPaymethod(0);
            orders.setPaytime(DateUtil.getPresentTimeLong());
            if(!addOrder(orders).isResult()){
                return new MessageInfo(false, "创建订单失败,请重试!");//在此创建订单,同时设置好所有信息
            }
            session.update(manager);
            session.update(vuene);

            session.getTransaction().commit();
            session.clear();
            return new MessageInfo(true, "下单成功!");
        } catch (Exception e) {
            e.printStackTrace();
            if (session != null) {
                session.getTransaction().rollback();
            }
            return new MessageInfo(false, "下单失败,请重新结算!");
        } finally {
            session.close();
        }
    }

    public MessageInfo getAllOrdersNum(){
        Session session = baseDao.getSession();
        try{
            Long count = (Long ) session.createCriteria(Orders.class).setProjection(Projections.rowCount()).uniqueResult();
            return new MessageInfo(true,count,"数据获取成功!");
        }catch (Exception e){
            e.printStackTrace();
            return  new MessageInfo(false,"数据获取失败!");
        }finally {
            session.close();
        }
    }

    public MessageInfo getAllOrdersNumByVuene(int vueneid){
        Session session = baseDao.getSession();
        try{
            Long count = (Long ) session.createCriteria(Orders.class).add(Restrictions.and(Restrictions.eq("vueneid",vueneid),
                    Restrictions.eq("orderstate",3))).setProjection(Projections.rowCount()).uniqueResult();
            return new MessageInfo(true,count,"数据获取成功!");
        }catch (Exception e){
            e.printStackTrace();
            return  new MessageInfo(false,"数据获取失败!");
        }finally {
            session.close();
        }
    }
    public MessageInfo getAllCheckInNum(){
        Session session = baseDao.getSession();
        try{
            Long count = (Long ) session.createCriteria(Orders.class).add(Restrictions.eq("orderstate",3)).setProjection(Projections.rowCount()).uniqueResult();
            return new MessageInfo(true,count,"数据获取成功!");
        }catch (Exception e){
            e.printStackTrace();
            return  new MessageInfo(false,"数据获取失败!");
        }finally {
            session.close();
        }
    }
    public MessageInfo countIncomeAndNum(){
        Session session = baseDao.getSession();
        try {
            List result = session.createCriteria(Orders.class).setProjection(
                    Projections.projectionList()
                            .add(Projections.rowCount())
                            .add(Projections.sum("price"))
                            .add(Projections.groupProperty("starttime"))).list();
            List<OrderFinaceManagerInfoVo> orderFinaceManagerInfoVos = new ArrayList<OrderFinaceManagerInfoVo>();

            for (int i = 0; i < result.size(); i++) {
                Object[] objects = (Object[]) result.get(i);
                orderFinaceManagerInfoVos.add(new OrderFinaceManagerInfoVo(DateUtil.getLongToDateString((Long)objects[2]) ,(Double)objects[1],(Long)objects[0]));
            }

            return new MessageInfo(true, orderFinaceManagerInfoVos, "数据获取成功");

        } catch (Exception e) {
            e.printStackTrace();
            return new MessageInfo(false, "查询失败");
        } finally {
            session.close();
        }

    }


    public MessageInfo countIncome(int vueneid) {
        Session session = baseDao.getSession();
        try {
            List result = session.createCriteria(Orders.class).add(Restrictions.and(Restrictions.eq("vueneid",vueneid),
                    Restrictions.eq("orderstate",3))).setProjection(
                    Projections.projectionList()
                            .add(Projections.sum("price"))
                            .add(Projections.groupProperty("starttime"))).list();
            List<VueneFinaceCountVo> vueneFinaceCountVos = new ArrayList<VueneFinaceCountVo>();

            for (int i = 0; i < result.size(); i++) {
                Object[] objects = (Object[]) result.get(i);
                vueneFinaceCountVos.add(new VueneFinaceCountVo(DateUtil.getLongToDateString((Long) objects[1]), (Double) objects[0]));
            }

            return new MessageInfo(true, vueneFinaceCountVos, "数据获取成功");

        } catch (Exception e) {
            e.printStackTrace();
            return new MessageInfo(false, "查询失败");
        } finally {
            session.close();
        }
    }

}
