package com.olticketsbooking.ticketsbooking.dao;

import com.olticketsbooking.ticketsbooking.config.MessageInfo;
import com.olticketsbooking.ticketsbooking.model.Playroom;
import com.olticketsbooking.ticketsbooking.vo.PlayroomVo;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Repository
public class PlayroomDao {


    @Resource
    BaseDao baseDao;

    public MessageInfo addRoom(Playroom playroom) {
        return baseDao.save(playroom);
    }

    public MessageInfo update(Playroom playroom) {
        return baseDao.update(playroom);
    }

    public MessageInfo save(Playroom playroom) {
        return baseDao.save(playroom);
    }


    public MessageInfo findRoomById(int id) {
        return baseDao.findById(Playroom.class, id);
    }

    public MessageInfo deleteRoom(Playroom room) {
        return baseDao.delete(room);
    }

    public MessageInfo findRoomByVuene(int vueneid) {
        Session session = baseDao.getSession();
        try {
            String queryString = "from " + Playroom.class.getSimpleName()
                    + " as model where model.vueneid  =:vueneid and model.state=0";
            Query queryObject = session.createQuery(queryString);
            queryObject.setInteger("vueneid", vueneid);
            List<Playroom> lists = queryObject.list();
            List<PlayroomVo> result = new ArrayList<PlayroomVo>();
            for (Playroom tem : lists)
                result.add(new PlayroomVo(tem));
            return new MessageInfo(true, result, "数据获取成功");
        } catch (Exception re) {
            re.printStackTrace();
            return new MessageInfo(false, "数据获取失败");
        } finally {
            session.close();
        }
    }

    public MessageInfo findRoomByVueneOriginal(int vueneid) {
        Session session = baseDao.getSession();
        try {
            String queryString = "from " + Playroom.class.getSimpleName()
                    + " as model where model.vueneid  =:vueneid and model.state=0";
            Query queryObject = session.createQuery(queryString);
            queryObject.setInteger("vueneid", vueneid);
            List<Playroom> lists = queryObject.list();

            return new MessageInfo(true, lists, "数据获取成功");
        } catch (Exception re) {
            re.printStackTrace();
            return new MessageInfo(false, "数据获取失败");
        } finally {
            session.close();
        }
    }


}
