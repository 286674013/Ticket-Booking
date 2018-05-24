package com.olticketsbooking.ticketsbooking.dao;


import com.olticketsbooking.ticketsbooking.config.MessageInfo;
import com.olticketsbooking.ticketsbooking.model.Perform;
import com.olticketsbooking.ticketsbooking.vo.PerformVo;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PerformDao {

    @Resource
    BaseDao baseDao;

    public MessageInfo addPerform(Perform perform) {
        return baseDao.save(perform);
    }

    public MessageInfo update(Perform perform) {
        return baseDao.update(perform);
    }

    public MessageInfo save(Perform perform) {
        return baseDao.save(perform);
    }


    public MessageInfo findPerformById(int id) {
        return baseDao.findById(Perform.class, id);
    }

    public MessageInfo deletePerform(Perform room) {
        return baseDao.delete(room);
    }

    public MessageInfo allPerform() {
        return baseDao.getAll(Perform.class);

    }

    public MessageInfo findAllPerformByVuene(int vueneid) {
        Session session = baseDao.getSession();
        try {
            String queryString = "from " + Perform.class.getSimpleName()
                    + " as model where model.vueneid  =:vueneid";
//            and model.state=0
            Query queryObject = session.createQuery(queryString);
            queryObject.setInteger("vueneid", vueneid);
            List<Perform> lists = queryObject.list();

            return new MessageInfo(true, lists, "数据获取成功");
        } catch (Exception re) {
            re.printStackTrace();
            return new MessageInfo(false, "数据获取失败");
        } finally {
            session.close();
        }
    }

    public MessageInfo searchPerform(long begintime,long endtime,String keywords){
//        System.out.println(begintime+endtime+keywords);
        String[] array = keywords.split("\\+|-|\\*|/|\\b|\\t|\\|\0");
//        System.out.println(array[0].toString());
        int count=0;
        for(int i=0;i<array.length;i++){
            if(!array[i].equals("")&&!array[i].equals(null)&&!array[i].equals(" ")){
                count++;
            }
        }
        String[] keys=new String[count];
        if(count>0) {
            count=0;
            for(int i=0;i<array.length;i++){
                if(!array[i].equals("")&&!array[i].equals(null)&&!array[i].equals(" ")){
                    keys[count]=array[i];
                    count++;
                }
            }
        }
        Session session = baseDao.getSession();
        try {
            String queryString = "from " + Perform.class.getSimpleName()
                    + " as model where model.begintime >= :begintime and model.endtime<= :endtime  "
                    ;
            for(int i=0;i<count;i++){
                queryString=queryString+"and concat(performname, performtype,performdescription,vuenename,playroomname,vuenedescription) like '%"+keys[i]+"%' ";
            }
            System.out.println(queryString);
//            and model.state=0
            Query queryObject = session.createQuery(queryString);
            queryObject.setLong("begintime",begintime);
            queryObject.setLong("endtime",endtime);
            List<Perform> lists = queryObject.list();

            return new MessageInfo(true, lists, "数据获取成功");
        } catch (Exception re) {
            re.printStackTrace();
            return new MessageInfo(false, "数据获取失败");
        } finally {
            session.close();
        }
    }
}
