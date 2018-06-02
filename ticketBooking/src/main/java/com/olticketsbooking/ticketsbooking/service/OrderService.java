package com.olticketsbooking.ticketsbooking.service;

import com.olticketsbooking.ticketsbooking.config.MessageInfo;
import com.olticketsbooking.ticketsbooking.dao.*;
import com.olticketsbooking.ticketsbooking.model.*;
import com.olticketsbooking.ticketsbooking.utils.DateUtil;
import com.olticketsbooking.ticketsbooking.utils.MathUtil;
import com.olticketsbooking.ticketsbooking.utils.OrderAnalyzeUtil;
import com.olticketsbooking.ticketsbooking.utils.SeatsUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderService {

    @Resource
    PerformDao performDao;
    @Resource
    OrderDao orderDao;
    @Resource
    UserDao userDao;
    @Resource
    CouponDao couponDao;
    @Resource
    VueneDao vueneDao;
    @Resource
    ManagerDao managerDao;
    @Resource
    PlayroomDao playroomDao;
    @Resource
    BankCardDao bankCardDao;



    public MessageInfo addOrder(User user, int performid,String seats,int buytype,String discounttype,String remark) {

        if (user.getState() == 0){
            return new MessageInfo(false,"该会员状态还未激活,不能预订");
        }

        MessageInfo messageInfo = performDao.findPerformById(performid);
        Perform perform=(Perform)messageInfo.getObject();
        MessageInfo messageInfo1 =vueneDao.findVuenebyid(perform.getVueneid());
        Vuene vuene=(Vuene) messageInfo1.getObject();
        if (messageInfo.isResult()&&messageInfo1.isResult()) {

            Orders orders = new Orders();
            //初始化订单状态
            orders.setOrderstate(0);
            //初始化座位信息
            int seatlength=1;
            String seatsToChoose="";
            if(buytype==0){//自选
                seatlength=SeatsUtil.getSeatFromInput(seats).length;
                if(!SeatsUtil.checkAllVacant(seats,perform.getVacancy())){
                    return new MessageInfo(false,"啊哦,你选的座位已经被别人抢走啦!");
                }else{
                    seatsToChoose=SeatsUtil.getSeatStringForDisplay(SeatsUtil.getSeatFromInput(seats));
                    perform.setVacancy(SeatsUtil.updateSeats(seats,perform.getVacancy()));
                }
            }else{
                seatlength=Integer.parseInt(seats);
                seatsToChoose=SeatsUtil.getVacantSeats(perform.getVacancy(),seatlength);
                if(!SeatsUtil.checkAllVacant(seatsToChoose,perform.getVacancy())){
                    return new MessageInfo(false,"啊哦,你选的座位已经被别人抢走啦!");
                }else{
                    perform.setVacancy(SeatsUtil.updateSeats(seatsToChoose,perform.getVacancy()));
                }

            }
            if(seatlength==0){
                return new MessageInfo(false,"错误的座位信息");
            }else if(seatlength>perform.getCountvacancy()){
                return new MessageInfo(false,"已经没有这么多空位了");
            }

            perform.setCountvacancy(perform.getCountvacancy()-seatlength);
            performDao.update(perform);
            //初始化折扣信息
            if(discounttype.equals("no")){
                orders.setDiscount(1);
            }else if(discounttype.equals("vip")){
                orders.setDiscount(user.getDiscount());
            }//优惠券类型暂未加入
            //初始化价格信息
            double price = MathUtil.roundNum(perform.getSeatprice() * orders.getDiscount(),2);

            orders.setStarttime(DateUtil.getPresentTimeLong());
            orders.setPrice(price*seatlength);
            orders.setOriginalprice(MathUtil.roundNum(perform.getSeatprice()*seatlength,2));
            orders.setCouponid(0);
            orders.setPerformdescription(perform.getPerformdescription());
            orders.setPerformid(perform.getPerformid());
            orders.setPerformname(perform.getPerformname());
            orders.setUserid(user.getId());
            orders.setUsername(user.getUsername());
            orders.setVueneid(vuene.getVueneid());
            orders.setVuenename(vuene.getVuenename());
            orders.setVueneaddress(vuene.getVueneaddress());
            orders.setPerformstart(perform.getBegintime());
            orders.setPerformend(perform.getEndtime());
            orders.setRemark(remark);
            orders.setSeats(seatsToChoose);
//            System.out.println(orders.getDiscount()+"折扣与座位"+orders.getSeats()+"价格"+orders.getPrice());
            userDao.updateUserPreference(user);
            return orderDao.addOrder(orders);
        }else{
            if(messageInfo.isResult()){
                return messageInfo;
            }else   if(messageInfo1.isResult()){
                return messageInfo1;
            }
        }
        return messageInfo;

    }



public MessageInfo addOrderVuene(Vuene vuene,int performid,int userid,String seats,double discount) {
    return orderDao.orderPay(vuene,performid,userid,seats,discount);
}


    public MessageInfo getUserOrders(int userid) {
        return orderDao.findOrderByUser(userid);
    }


    public MessageInfo orderPayBalance(int userid, int orderid) {
        MessageInfo messageInfo = userDao.findUser(userid);
        if (messageInfo.isResult()) {
            User user = (User) messageInfo.getObject();
            messageInfo = orderDao.findOrder(orderid);

            if (messageInfo.isResult()) {
                Orders order = (Orders) messageInfo.getObject();

                if (order.getPrice() <= user.getBalance()) {
                    return orderDao.payOrderByBalance(user, order);
                } else
                    return new MessageInfo(false, "会员账户余额不足,请选择其他方式进行支付!");


            } else
                return new MessageInfo(false, "订单信息查找错误,请稍等重试!");
        } else
            return new MessageInfo(false, "登录信息有误,请稍后重试!");


    }


    public MessageInfo orderPayBank(int userid, int orderid, String cardid, String password) {
        MessageInfo messageInfo = bankCardDao.findCard(cardid);
        if (messageInfo.getObject() == null)
            return new MessageInfo(false, "银行卡卡号或密码错误,请重新支付");
        if (messageInfo.isResult()) {

            BankCard bankCard = (BankCard) messageInfo.getObject();
            if(!password.equals(bankCard.getPassword())){
                return new MessageInfo(false, "银行卡卡号或密码错误,请重新支付");
            }
            messageInfo = userDao.findUser(userid);

            if (messageInfo.getObject() == null)
                return new MessageInfo(false, "登录信息有误,请稍后重试!");
            if (messageInfo.isResult()){
                User user =(User) messageInfo.getObject();
                messageInfo = orderDao.findOrder(orderid);

                if (messageInfo.getObject() == null){
                    return new MessageInfo(false,"订单信息查找错误,请稍等重试!");
                }

                if (messageInfo.isResult()){
                    Orders order =(Orders) messageInfo.getObject();
                    if(order.getPrice()>bankCard.getMoney()){
                        return new MessageInfo(false,"银行卡余额不足!");
                    }
                    return    orderDao.payOrderByBank(bankCard,user,order);
                }
            }

        }

        return new MessageInfo(false, "服务器错误,请稍后重试!");
    }

    public MessageInfo orderPayCash(int userid,int orderid) {
        MessageInfo messageInfo = userDao.findUser(userid);
        if (messageInfo.getObject() == null)
            return new MessageInfo(false, "登录信息有误,请稍后重试!");
        if (messageInfo.isResult()){
            User user =   (User) messageInfo.getObject();
            messageInfo = orderDao.findOrder(orderid);
            if (messageInfo.getObject() == null){
                return new MessageInfo(false,"订单信息查找错误,请稍等重试!");
            }
            if (messageInfo.isResult()){
                Orders order = (Orders) messageInfo.getObject();
                return orderDao.payOrderByCash(user,order);

            }
        }

        return new MessageInfo(false, "服务器错误,请稍后重试!");
    }

    public MessageInfo orderCancle(int id,int orderid){
        MessageInfo messageInfo = orderDao.findOrder(orderid);
        if (messageInfo.getObject() == null)
            return new MessageInfo(false,"订单信息查找错误,请稍等重试!");
        if (messageInfo.isResult()){
            Orders order = (Orders) messageInfo.getObject();
            if (order.getUserid() == id||order.getVueneid() == id){
                if (order.getOrderstate() == 0 ){
                    return orderDao.cancleOrder(order);
                }else if ( order.getOrderstate() ==1){
                    double percent=1;
                    if(DateUtil.getPresentTimeLong()>=order.getPerformstart()){
                        return new MessageInfo(false,"该订单已经到期,无法取消");
                    }else if(DateUtil.getDistanceOfTwoDate(DateUtil.getPresentTimeLong(),order.getPerformstart())>7){
                        percent=1;
                    }else if(DateUtil.getDistanceOfTwoDate(DateUtil.getPresentTimeLong(),order.getPerformstart())>5){
                        percent=0.9;
                    }else if(DateUtil.getDistanceOfTwoDate(DateUtil.getPresentTimeLong(),order.getPerformstart())>3){
                        percent=0.85;
                    }else if(DateUtil.getDistanceOfTwoDate(DateUtil.getPresentTimeLong(),order.getPerformstart())>2){
                        percent=0.75;
                    }else if(DateUtil.getDistanceOfTwoDate(DateUtil.getPresentTimeLong(),order.getPerformstart())>1){
                        percent=0.6;
                    }else if(DateUtil.getDistanceOfTwoDate(DateUtil.getPresentTimeLong(),order.getPerformstart())>0.5){
                        percent=0.55;
                    }else{
                        percent=0.5;
                    }
//                    System.out.println("返还额度比例"+percent);
                    return orderDao.cancleOrderByBalance(order,percent);
                }else {
                    return new MessageInfo(false,"该订单状态不允许更改");
                }
            }else {
                return new MessageInfo(false,"您没有权限改变该订单状态");
            }

        }
        return messageInfo;
    }
    public MessageInfo orderCancleByVuene(int id,int orderid){
        MessageInfo messageInfo = orderDao.findOrder(orderid);
        if (messageInfo.getObject() == null)
            return new MessageInfo(false,"订单信息查找错误,请稍等重试!");
        if (messageInfo.isResult()){
            Orders order = (Orders) messageInfo.getObject();
            if (order.getUserid() == id||order.getVueneid() == id){
                if (order.getOrderstate() == 0 ){
                    return orderDao.cancleOrder(order);
                }else if ( order.getOrderstate() ==1){
                    double percent=1;
                    return orderDao.cancleOrderByBalance(order,percent);
                }else {
                    return new MessageInfo(false,"该订单状态不允许更改");
                }
            }else {
                return new MessageInfo(false,"您没有权限改变该订单状态");
            }

        }
        return messageInfo;
    }
    public MessageInfo orderCancleByManager(int orderid){
        MessageInfo messageInfo = orderDao.findOrder(orderid);
        if (messageInfo.getObject() == null)
            return new MessageInfo(false,"订单信息查找错误,请稍等重试!");
        if (messageInfo.isResult()){
            Orders order = (Orders) messageInfo.getObject();
            if (order.getOrderstate() == 0 ){
                return orderDao.cancleOrder(order);
            }else if ( order.getOrderstate() ==1){
                    double percent=1;
                    return orderDao.cancleOrderByBalance(order,percent);
                }else {
                    return new MessageInfo(false,"该订单状态不允许更改");
                }
        }
        return messageInfo;
    }


    public MessageInfo getAllOrders(){
        return orderDao.allOrder();
    }


    public MessageInfo getVueneOrders(int vueneid){
        return orderDao.findOrderByVuene(vueneid);
    }


    public MessageInfo orderConfirmByUser(int userid,int orderid){
        MessageInfo messageInfo = orderDao.findOrder(orderid);

        if (messageInfo.getObject() == null)
            return new MessageInfo(false,"订单信息有误");

        if (messageInfo.isResult()){
            Orders order = (Orders) messageInfo.getObject();

            if (order.getUserid() != userid)
                return new MessageInfo(false,"您没有权限改变该订单状态");
            order.setOrderstate(3);
            return orderDao.update(order);

        }else {
            return   messageInfo;
        }
    }

    public MessageInfo orderConfirmByVuene(int vueneid,int orderid){
        MessageInfo messageInfo = orderDao.findOrder(orderid);

        if (messageInfo.getObject() == null)
            return new MessageInfo(false,"订单信息有误");

        if (messageInfo.isResult()){
            Orders order = (Orders) messageInfo.getObject();

            if (order.getVueneid() != vueneid)
                return new MessageInfo(false,"您没有权限改变该订单状态");
            order.setOrderstate(3);
            return orderDao.update(order);

        }else {
            return   messageInfo;
        }
    }


    public MessageInfo getOrdersBetweenTime(long start,long end){
        return orderDao.findOrderBetweenTimeOriginal(start,end);
    }
    public MessageInfo getOrdersBetweenTime(long start,long end,int vueneid){
        return orderDao.findOrderBetweenTimeOriginal(start,end,vueneid);
    }

    public MessageInfo getCountOrderState(long start,long end,int vueneid){
        return new MessageInfo(true,OrderAnalyzeUtil.countOrderState((List<Orders>) orderDao.findOrderBetweenTimeOriginal(start,end,vueneid).getObject()),"数据如下");

    }
    public MessageInfo getCountOrderPay(long start,long end,int vueneid){
        return new MessageInfo(true,OrderAnalyzeUtil.countOrderPay((List<Orders>) orderDao.findOrderBetweenTimeOriginal(start,end,vueneid).getObject()),"数据如下");

    }



}
