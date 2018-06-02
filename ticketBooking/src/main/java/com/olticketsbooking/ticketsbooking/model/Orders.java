package com.olticketsbooking.ticketsbooking.model;




//DROP TABLE IF EXISTS `order`;
///*!40101 SET @saved_cs_client     = @@character_set_client */;
///*!40101 SET character_set_client = utf8 */;
//        CREATE TABLE `order` (
//        `orderid` int(11) NOT NULL AUTO_INCREMENT,
//        `starttime` datetime NOT NULL,
//        `endtime` datetime NOT NULL,
//        `state` varchar(45) NOT NULL COMMENT '0:已下单\n1:已支付\n2:取消\n3:完成',
//        `userid` int(11) NOT NULL,
//        `performid` int(11) NOT NULL,
//        `seats` varchar(2555) NOT NULL,
//        `originalprice` double DEFAULT NULL,
//        `hasdiscount` int(10) unsigned zerofill DEFAULT NULL COMMENT '0:no\n1:yes',
//        `couponid` int(11) DEFAULT NULL,
//        `discountvalue` double unsigned zerofill DEFAULT NULL,
//        `price` double NOT NULL,
//        `address` varchar(255) DEFAULT NULL,
//        `performstart` datetime DEFAULT NULL,
//        PRIMARY KEY (`orderid`),
//        UNIQUE KEY `orderid_UNIQUE` (`orderid`)
//        ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Orders {
    int orderid;
    int userid;
    String username;
    int vueneid;
    String vuenename;
    String vueneaddress;
    int performid;
    String performname;
    String performdescription;

    long starttime;
    long paytime;
    long cancletime;

    long performstart;
    long performend;

    int countseats;
    String seats;///1,2,3....


    int orderstate;//'0:已下单\n1:已支付\n2:取消\n3:完成',4:实体购买(已取票,无法网络退票)
    int couponid;
    int paymethod;//0 现金  1:钱包  2:银行卡   3:其他
    double originalprice;
    double discount;
    double price;
    String remark;

    public Orders() {
    }
    public Orders(int userid,String username,int vueneid,String vuenename,String vueneaddress ,int performid,String performdescription) {
    }
    @Id
    @GeneratedValue
    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPerformname() {
        return performname;
    }

    public void setPerformname(String performname) {
        this.performname = performname;
    }

    public int getVueneid() {
        return vueneid;
    }

    public void setVueneid(int vueneid) {
        this.vueneid = vueneid;
    }

    public String getVuenename() {
        return vuenename;
    }

    public void setVuenename(String vuenename) {
        this.vuenename = vuenename;
    }

    public String getVueneaddress() {
        return vueneaddress;
    }

    public void setVueneaddress(String vueneaddress) {
        this.vueneaddress = vueneaddress;
    }

    public int getPerformid() {
        return performid;
    }

    public void setPerformid(int performid) {
        this.performid = performid;
    }

    public String getPerformdescription() {
        return performdescription;
    }

    public void setPerformdescription(String performdescription) {
        this.performdescription = performdescription;
    }

    public long getStarttime() {
        return starttime;
    }

    public void setStarttime(long starttime) {
        this.starttime = starttime;
    }

    public long getPaytime() {
        return paytime;
    }

    public void setPaytime(long paytime) {
        this.paytime = paytime;
    }

    public long getCancletime() {
        return cancletime;
    }

    public void setCancletime(long cancletime) {
        this.cancletime = cancletime;
    }

    public long getPerformstart() {
        return performstart;
    }

    public void setPerformstart(long performstart) {
        this.performstart = performstart;
    }

    public long getPerformend() {
        return performend;
    }

    public void setPerformend(long performend) {
        this.performend = performend;
    }

    public int getCountseats() {
        return countseats;
    }

    public void setCountseats(int countseats) {
        this.countseats = countseats;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public int getOrderstate() {
        return orderstate;
    }

    public void setOrderstate(int orderstate) {
        this.orderstate = orderstate;
    }

    public int getCouponid() {
        return couponid;
    }

    public void setCouponid(int couponid) {
        this.couponid = couponid;
    }

    public int getPaymethod() {
        return paymethod;
    }

    public void setPaymethod(int paymethod) {
        this.paymethod = paymethod;
    }

    public double getOriginalprice() {
        return originalprice;
    }

    public void setOriginalprice(double originalprice) {
        this.originalprice = originalprice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

