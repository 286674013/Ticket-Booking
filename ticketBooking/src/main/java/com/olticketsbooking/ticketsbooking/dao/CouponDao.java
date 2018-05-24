package com.olticketsbooking.ticketsbooking.dao;

import com.olticketsbooking.ticketsbooking.config.MessageInfo;
import com.olticketsbooking.ticketsbooking.model.Coupon;
import com.olticketsbooking.ticketsbooking.vo.CouponVo;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Repository
public class CouponDao {

    @Resource
    BaseDao baseDao;

    public MessageInfo addCoupon(Coupon coupon) {
        return baseDao.save(coupon);
    }

    public MessageInfo update(Coupon coupon) {
        return baseDao.update(coupon);
    }

    public MessageInfo save(Coupon coupon) {
        return baseDao.save(coupon);
    }


    public MessageInfo findCouponById(int id) {
        return baseDao.findById(Coupon.class, id);
    }

    public MessageInfo deleteCoupon(Coupon coupon) {
        return baseDao.delete(coupon);
    }


    public MessageInfo findAllCoupon(int couponid) {
        Session session = baseDao.getSession();
        try {
            String queryString = "from " + Coupon.class.getSimpleName()
                    + " as model where model.couponid  =:couponid and model.state=0";
            Query queryObject = session.createQuery(queryString);
            queryObject.setInteger("couponid", couponid);
            List<Coupon> lists = queryObject.list();
            List<CouponVo> result = new ArrayList<CouponVo>();
            for (Coupon tem : lists)
                result.add(new CouponVo(tem));
            return new MessageInfo(true, result, "数据获取成功");
        } catch (Exception re) {
            re.printStackTrace();
            return new MessageInfo(false, "数据获取失败");
        } finally {
            session.close();
        }
    }
}
