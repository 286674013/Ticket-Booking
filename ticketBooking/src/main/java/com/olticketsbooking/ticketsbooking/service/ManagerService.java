package com.olticketsbooking.ticketsbooking.service;

import com.olticketsbooking.ticketsbooking.config.MessageInfo;
import com.olticketsbooking.ticketsbooking.dao.ManagerDao;
import com.olticketsbooking.ticketsbooking.dao.OrderDao;
import com.olticketsbooking.ticketsbooking.dao.UserDao;
import com.olticketsbooking.ticketsbooking.dao.VueneDao;
import com.olticketsbooking.ticketsbooking.model.Manager;
import com.olticketsbooking.ticketsbooking.vo.ManagerFinaceVo;
import com.olticketsbooking.ticketsbooking.vo.OrderFinaceManagerInfoVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ManagerService {
    @Resource
    ManagerDao managerDao;
    @Resource
    OrderDao orderDao;
    @Resource
    UserDao userDao;
    @Resource
    VueneDao vueneDao;

    public MessageInfo getInfo(int id){
        return  managerDao.findManagerById(id);
    }

    public MessageInfo update(Manager manager,String name, double percent, String email, String telephone){
        manager.setName(name);
        manager.setPercent(percent);
        manager.setEmail(email);
        manager.setTelephone(telephone);
        return  managerDao.update(manager);
    }

    public MessageInfo getManagerFianceInfo(){
        MessageInfo messageInfo = managerDao.findManagerById(1);
        if (messageInfo.isResult()){

            Manager manager =(Manager) messageInfo.getObject();
            Long appointmentnum =(Long) orderDao.getAllOrdersNum().getObject();
            Long checkinNum = (Long) orderDao.getAllCheckInNum().getObject();
            Long userNum =(Long) userDao.getAllUsersNum().getObject();
            Long vuenenum=(Long)vueneDao.getAllVueneNum().getObject();
            List<OrderFinaceManagerInfoVo> list = (List<OrderFinaceManagerInfoVo> )orderDao.countIncomeAndNum().getObject();
            return new MessageInfo(true,new ManagerFinaceVo(manager.getName(),manager.getBalance(),userNum,vuenenum,appointmentnum,checkinNum,list),"数据获取成功");
        }else {
            return messageInfo;
        }

    }
    public MessageInfo getmoney(int id,String bankcardid,String password,double money){
        return managerDao.getmoney(id,bankcardid,password,money);
    }

}
