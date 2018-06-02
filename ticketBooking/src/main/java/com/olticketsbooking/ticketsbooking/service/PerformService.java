package com.olticketsbooking.ticketsbooking.service;

import com.olticketsbooking.ticketsbooking.config.MessageInfo;
import com.olticketsbooking.ticketsbooking.dao.PerformDao;
import com.olticketsbooking.ticketsbooking.dao.PlayroomDao;
import com.olticketsbooking.ticketsbooking.dao.UserDao;
import com.olticketsbooking.ticketsbooking.dao.VueneDao;
import com.olticketsbooking.ticketsbooking.model.Perform;
import com.olticketsbooking.ticketsbooking.model.Playroom;
import com.olticketsbooking.ticketsbooking.model.Vuene;
import com.olticketsbooking.ticketsbooking.utils.ClassificationUtil;
import com.olticketsbooking.ticketsbooking.utils.DateUtil;
import com.olticketsbooking.ticketsbooking.utils.SeatsUtil;
import com.olticketsbooking.ticketsbooking.vo.PerformVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class PerformService {
    @Resource
    VueneDao vueneDao;
    @Resource
    PlayroomDao playroomDao;
    @Resource
    PerformDao performDao;
    @Resource
    UserDao userDao;

    public MessageInfo addPerform(Vuene vuene, int playroomid, String begintime, String endtime,
                                  double seatprice, String performtype, String performname,
                                  String performkeys,String performdescription){
//        String playroomname,String playroomdescription
        MessageInfo messageInfo=playroomDao.findRoomById(playroomid);
        if (messageInfo.getObject() == null){
            return new MessageInfo(false,"登录信息获取失败,请重新登录");
        }
        Playroom playroom=(Playroom)messageInfo.getObject();

        Perform perform=new Perform();
        int totalseats=playroom.getTotalseats();
        perform.setBegintime(DateUtil.getTimeLong(begintime));
        perform.setEndtime(DateUtil.getTimeLong(endtime));
        perform.setVueneid(vuene.getVueneid());
        perform.setVuenename(vuene.getVuenename());
        perform.setVuenedescription(vuene.getVuenedescription());
        perform.setPlayroomid(playroomid);
        perform.setPlayroomname(playroom.getPlayroomname());
        perform.setPlayroomdescription(playroom.getDescription());
        perform.setSeatprice(seatprice);
        perform.setPerformtype(performtype);
        perform.setPerformname(performname);
        perform.setPerformdescription(performdescription);
        perform.setPerformkeys(ClassificationUtil.getFormatString(performkeys));
        perform.setCountvacancy(totalseats);
        perform.setTotalseats(totalseats);
        perform.setVacancy(SeatsUtil.getSeatsAll(totalseats));
        perform.setState(0);
        return performDao.addPerform(perform);
    }
    public MessageInfo updatePerform(Vuene vuene,int performid, int playroomid, String begintime, String endtime,
                                     double seatprice, String performtype, String performname,
                                     String performkeys,String performdescription){
        MessageInfo messageInfo=playroomDao.findRoomById(playroomid);
        if (messageInfo.getObject() == null){
            return new MessageInfo(false,"演出室信息获取失败,请重新操作");
        }
        Playroom playroom=(Playroom)messageInfo.getObject();
        int totalseats=playroom.getTotalseats();
        MessageInfo messageInfo2=performDao.findPerformById(performid);
        if (messageInfo2.getObject() == null){
            return new MessageInfo(false,"演出信息获取失败,请重新登录");
        }
        Perform perform=(Perform)messageInfo2.getObject();
        perform.setVueneid(vuene.getVueneid());
        perform.setVuenename(vuene.getVuenename());
        perform.setVuenedescription(vuene.getVuenedescription());
        perform.setPlayroomid(playroomid);
        perform.setPlayroomname(playroom.getPlayroomname());
        perform.setPlayroomdescription(playroom.getDescription());
        perform.setBegintime(DateUtil.getTimeLong(begintime));
        perform.setEndtime(DateUtil.getTimeLong(endtime));
        perform.setSeatprice(seatprice);
        perform.setPerformtype(performtype);
        perform.setPerformname(performname);
        perform.setPerformdescription(performdescription);
        perform.setCountvacancy(totalseats);
        perform.setTotalseats(totalseats);
        perform.setVacancy(SeatsUtil.getSeatsAll(totalseats));
        perform.setState(0);
        perform.setPerformkeys(ClassificationUtil.getFormatString(performkeys));
        return performDao.update(perform);
    }
    public MessageInfo findPerformById(int performid){
        MessageInfo messageInfo=performDao.findPerformById(performid);
        return new MessageInfo(messageInfo.isResult(),new PerformVo((Perform)messageInfo.getObject()),messageInfo.getReason() );
    }
    public MessageInfo findPerformByVuene(int vueneid){
        return translateMessageInfo(performDao.findAllPerformByVuene(vueneid));
    }
    public  MessageInfo findAllPerform(){
        return translateMessageInfo(performDao.allPerform());
    }
    public MessageInfo translateMessageInfo(MessageInfo messageInfo){
        if (messageInfo.getObject() == null){
            return new MessageInfo(false,"演出信息获取失败,请重新操作");
        }
        List<Perform> lists = (List<Perform>)messageInfo.getObject();
        List<PerformVo> result = new ArrayList<PerformVo>();
        for (Perform tem : lists)
            result.add(new PerformVo(tem));
        return new MessageInfo(true,result,"演出信息获取成功,请继续操作");
    }

    public MessageInfo searchPerform(String begintime,String endtime,String keywords){
//        System.out.println(begintime+endtime+keywords);
        return translateMessageInfo(performDao.searchPerform(DateUtil.getTimeDateOnlyLong(begintime), DateUtil.getTimeDateOnlyLong(endtime)+86400000,keywords));
    }

}
