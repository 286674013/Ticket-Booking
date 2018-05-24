package com.olticketsbooking.ticketsbooking.service;

import com.olticketsbooking.ticketsbooking.config.MessageInfo;
import com.olticketsbooking.ticketsbooking.dao.PlayroomDao;
import com.olticketsbooking.ticketsbooking.model.Playroom;
import com.olticketsbooking.ticketsbooking.model.Vuene;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PlayroomService {
    @Resource
    PlayroomDao playroomDao;

    public MessageInfo addRoom(Vuene vuene, String playroomname, int totalseats, String description) {


        int vueneid=vuene.getVueneid();
        Playroom playroom=new Playroom(vueneid,playroomname,totalseats,description);
        playroom.setState(0);

        return playroomDao.addRoom(playroom);


    }

    public MessageInfo update(Vuene vuene,int playroomid, String playroomname, int totalseats, String description) {


        int vueneid=vuene.getVueneid();
        MessageInfo messageInfo=playroomDao.findRoomById(playroomid);
        if(messageInfo.getObject()==null){
            return new MessageInfo(false,"放映室信息获取失败,请重新操作");
        }
        Playroom playroom=(Playroom) messageInfo.getObject();
        playroom.setPlayroomname(playroomname);
        playroom.setTotalseats(totalseats);
        playroom.setDescription(description);
        return playroomDao.update(playroom);
    }



    public MessageInfo findRoomById(int playroomid) {

        return playroomDao.findRoomById(playroomid);
    }

    public MessageInfo deleteRoom(int playroomid) {
        MessageInfo messageInfo=playroomDao.findRoomById(playroomid);
        if(messageInfo==null){
            return new MessageInfo(false,"放映室信息获取失败,请重新操作");
        }
        return playroomDao.deleteRoom((Playroom) messageInfo.getObject());

    }

    public MessageInfo setRoomDeleted(int playroomid) {
        MessageInfo messageInfo=playroomDao.findRoomById(playroomid);
        if(messageInfo==null){
            return new MessageInfo(false,"放映室信息获取失败,请重新操作");
        }
        Playroom playroom=(Playroom) messageInfo.getObject();
        playroom.setState(1);
        return playroomDao.update(playroom);

    }

    public MessageInfo findRoomByVuene(int vueneid) {
        return playroomDao.findRoomByVuene(vueneid);
    }

}
