package com.olticketsbooking.ticketsbooking.model;


import javax.persistence.*;

//DROP TABLE IF EXISTS `coupon`;
///*!40101 SET @saved_cs_client     = @@character_set_client */;
///*!40101 SET character_set_client = utf8 */;
//        CREATE TABLE `coupon` (
//        `couponid` int(11) NOT NULL AUTO_INCREMENT,
//        `userid` int(11) NOT NULL,
//        `discountid` int(11) NOT NULL,
//        `discountvalue` double NOT NULL,
//        `state` int(10) unsigned zerofill NOT NULL COMMENT '0:未使用\n1:已使用\n2:已过期\n3:异常',
//        `starttime` datetime NOT NULL,
//        `endtime` datetime NOT NULL,
//        PRIMARY KEY (`couponid`),
//        UNIQUE KEY `couponid_UNIQUE` (`couponid`)
//        ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
///*!40101 SET character_set_client = @saved_cs_client */;
@Entity
@Table(name = "coupon")

public class Coupon {
    int couponid;
    double discountvalue;
    int state;  // '0:未使用\n1:已使用\n2:已过期\n3:异常',
    long starttime;
    long endtime;
    Discount discount;
    int userid;
    public Coupon() {
    }


    @Id
    @GeneratedValue
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
    @ManyToOne
    @JoinColumn(name = "discountid")
    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
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
        this.starttime = starttime;
    }

    public long getEndtime() {
        return endtime;
    }

    public void setEndtime(long endtime) {
        this.endtime = endtime;
    }
}
