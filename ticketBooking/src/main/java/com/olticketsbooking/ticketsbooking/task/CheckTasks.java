package com.olticketsbooking.ticketsbooking.task;

import com.olticketsbooking.ticketsbooking.dao.EmailDao;
import com.olticketsbooking.ticketsbooking.dao.OrderDao;
import com.olticketsbooking.ticketsbooking.dao.PerformDao;
import com.olticketsbooking.ticketsbooking.model.EmailActivation;
import com.olticketsbooking.ticketsbooking.model.Orders;
import com.olticketsbooking.ticketsbooking.model.Perform;
import com.olticketsbooking.ticketsbooking.utils.DateUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class CheckTasks {
    @Resource
    EmailDao emailDao;
    @Resource
    OrderDao orderDao;
    @Resource
    PerformDao performDao;

    @Scheduled(cron = "0 0/1 *  * * ?")
    public void checkEmailValid() {
        List<EmailActivation> list = (List<EmailActivation>) emailDao.findAllEmailActivation().getObject();
        long nowTime = DateUtil.getPresentTimeLong();
//        System.out.println("***********************执行一次检查*************************");
        for (EmailActivation emailActivation : list) {
//            System.out.println(emailActivation.getEndtime() +"    "+nowTime);
            if (emailActivation.getEndtime() < nowTime && emailActivation.getActivationstate() == 0) {
                emailActivation.setActivationstate(2);
                emailDao.update(emailActivation);
            }
        }

    }
    @Scheduled(cron = "0 0/1 *  * * ?")
    public void checkOrderState(){
        List<Orders> list=(List<Orders>)orderDao.allOrder().getObject();
        long nowTime = DateUtil.getPresentTimeLong();
//        System.out.println("***********************执行一次检查*************************");
        for(Orders orders:list){
            if(orders.getOrderstate()==0&&(orders.getStarttime()+ DateUtil.getPassedMinLong(5.0))<nowTime){
                orderDao.cancleOrder(orders);
            }else if(orders.getOrderstate()==1&&(orders.getPerformstart()+ DateUtil.getPassedMinLong(30.0))<nowTime){
                orders.setOrderstate(3);
                orderDao.update(orders);
            }
        }
    }

    @Scheduled(cron = "0 0/1 *  * * ?")
    public void checkPerformState(){
        List<Perform> list=(List<Perform>)performDao.allPerform().getObject();
        long nowTime = DateUtil.getPresentTimeLong();
//        System.out.println("***********************执行一次检查*************************");
        for(Perform perform:list){
            if(perform.getState()==0&&perform.getBegintime()<nowTime){
                perform.setState(1);
                performDao.update(perform);
            }else if((perform.getState()==0||perform.getState()==1)&&perform.getEndtime()<nowTime){
                perform.setState(2);
                performDao.update(perform);
            }
        }
    }



}
