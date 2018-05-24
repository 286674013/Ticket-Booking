package com.olticketsbooking.ticketsbooking.vo;

import com.olticketsbooking.ticketsbooking.model.Orders;
import com.olticketsbooking.ticketsbooking.utils.DateUtil;


public class OrderVo {
    int orderid;
    int userid;
    String username;
    int vueneid;
    String vuenename;
    String vueneaddress;
    int performid;
    String performname;
    String performdescription;

    String starttime;
    String paytime;
    String cancletime;

    String performstart;
    String performend;

    String seats;///1,2,3....


    String orderstate;//'0:已下单\n1:已支付\n2:取消\n3:完成',
    int couponid;
    int paymethod;//0 现金  1:钱包  2:银行卡   3:其他
    double originalprice;
    double discount;
    double price;

    String remark;

    public OrderVo() {
    }
    public OrderVo(Orders orders){
        this.orderid= orders.getOrderid();
        this.userid= orders.getUserid();
        this.username= orders.getUsername();
        this.vueneid= orders.getVueneid();
        this.vuenename= orders.getVuenename();
        this.vueneaddress= orders.getVueneaddress();
        this.performid= orders.getPerformid();
        this.performname=orders.getPerformname();
        this.performdescription= orders.getPerformdescription();

        this.starttime= DateUtil.getLongToDateString(orders.getStarttime());
        this.paytime= DateUtil.getLongToDateString(orders.getPaytime());
        this.cancletime= DateUtil.getLongToDateString(orders.getCancletime());

        this.performstart= DateUtil.getLongToDateString(orders.getPerformstart());
        this.performend= DateUtil.getLongToDateString(orders.getPerformend());

        this.seats= orders.getSeats();///1,2,3....


        if (orders.getOrderstate() == 0) {
            this.orderstate = "已下单";
        } else if (orders.getOrderstate() == 1) {
            this.orderstate = "已支付";
        } else if (orders.getOrderstate() == 2) {
            this.orderstate = "已取消";
        }else if (orders.getOrderstate() ==3){
            this.orderstate = "已完成";
        }
        else {
            this.orderstate = "已完成";
        }
        this.couponid= orders.getCouponid();
        this.originalprice= orders.getOriginalprice();
        this.discount= orders.getDiscount();
        this.price= orders.getPrice();
        this.paymethod=orders.getPaymethod();
        this.remark=orders.getRemark();
    }

    public int getPaymethod() {
        return paymethod;
    }

    public void setPaymethod(int paymethod) {
        this.paymethod = paymethod;
    }

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

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getPaytime() {
        return paytime;
    }

    public void setPaytime(String paytime) {
        this.paytime = paytime;
    }

    public String getCancletime() {
        return cancletime;
    }

    public void setCancletime(String cancletime) {
        this.cancletime = cancletime;
    }

    public String getPerformstart() {
        return performstart;
    }

    public void setPerformstart(String performstart) {
        this.performstart = performstart;
    }

    public String getPerformend() {
        return performend;
    }

    public void setPerformend(String performend) {
        this.performend = performend;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getOrderstate() {
        return orderstate;
    }

    public void setOrderstate(String orderstate) {
        this.orderstate = orderstate;
    }

    public int getCouponid() {
        return couponid;
    }

    public void setCouponid(int couponid) {
        this.couponid = couponid;
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

    public String getPerformname() {
        return performname;
    }

    public void setPerformname(String performname) {
        this.performname = performname;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
