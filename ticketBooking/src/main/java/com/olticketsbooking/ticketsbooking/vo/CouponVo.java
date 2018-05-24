package com.olticketsbooking.ticketsbooking.vo;

import com.olticketsbooking.ticketsbooking.model.Coupon;
import com.olticketsbooking.ticketsbooking.utils.DateUtil;

public class CouponVo {
    int couponid;
    double discountvalue;
    int state;  // '0:未使用\n1:已使用\n2:已过期\n3:异常',
    long starttime;
    long endtime;
    DiscountVo discountVo;
    int userid;
    public CouponVo() {
    }
    public CouponVo(Coupon coupon) {
        this.couponid=coupon.getCouponid();
        this.discountvalue=coupon.getDiscountvalue();
        this.state=coupon.getState();
        this.starttime=coupon.getStarttime();
        this.endtime=coupon.getEndtime();
        this.discountVo=new DiscountVo(coupon.getDiscount());
        this.userid=coupon.getUserid();
    }


    public int getCouponid() {
        return couponid;
    }

    public void setCouponid(int couponid) {
        this.couponid = couponid;
    }


    public double getDiscountvalue() {
        return discountvalue;
    }

    public void setDiscountvalue(double discountvalue) {
        this.discountvalue = discountvalue;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public DiscountVo getDiscountVo() {
        return discountVo;
    }

    public void setDiscountVo(DiscountVo discountVo) {
        this.discountVo = discountVo;
    }


    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public long getStarttime() {
        return starttime;
    }

    public void setStarttime(long starttime) {
        this.starttime = DateUtil.getTimeLong(DateUtil.getDate());
    }

    public long getEndtime() {
        return endtime;
    }

    public void setEndtime(long endtime) {
        this.endtime = DateUtil.getTimeLong(DateUtil.getDate());
    }
}
