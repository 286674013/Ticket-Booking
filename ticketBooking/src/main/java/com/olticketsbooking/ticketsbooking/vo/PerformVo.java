package com.olticketsbooking.ticketsbooking.vo;

import com.olticketsbooking.ticketsbooking.model.Perform;
import com.olticketsbooking.ticketsbooking.utils.DateUtil;

public class PerformVo {
    int performid;
    int playroomid;
    int vueneid;
    String playroomname;
    String playroomdescription;
    String vuenename;
    String vuenedescription;
    String begintime;
    String endtime;
    double seatprice;
    String performtype;
    String performname;
    String performdescription;
    String vacancy;  //like1,2,3,4,5
    int countvacancy;
    int totalseats;
    String state; //0:初创,1:进行中,2:过期,3:取消,4:其他
    String performkeys;
    public PerformVo() {
    }

    public PerformVo(Perform perform) {
        this.performid=perform.getPerformid();
        this.playroomid=perform.getPlayroomid();
        this.vueneid=perform.getVueneid();
        this.playroomname=perform.getPlayroomname();
        this.playroomdescription=perform.getPlayroomdescription();
        this.performdescription=perform.getPerformdescription();
        this.vuenename=perform.getVuenename();
        this.vuenedescription=perform.getVuenedescription();
        this.begintime= DateUtil.getLongToDateString(perform.getBegintime());
        this.endtime= DateUtil.getLongToDateString(perform.getEndtime());
        this.seatprice=perform.getSeatprice();
        this.performtype=perform.getPerformtype();
        this.performdescription=perform.getPerformdescription();

        String seats="";
        for(int i=0;i<perform.getVacancy().length();i++){
            if(perform.getVacancy().charAt(i)=='1'){
                seats=seats+(i+1)+",";
            }
        }
        this.vacancy=seats;
        this.countvacancy=perform.getCountvacancy();
        this.performname=perform.getPerformname();
        this.totalseats=perform.getTotalseats();
        if (perform.getState() == 0) {
            this.state = "未开始";
        } else if (perform.getState() == 1) {
            this.state = "进行中";
        } else if (perform.getState() == 2) {
            this.state = "已过期";
        }else if (perform.getState() ==3){
            this.state = "已取消";
        }
        else {
            this.state = "其他";
        }
        this.performkeys=perform.getPerformkeys();
    }

    public int getTotalseats() {
        return totalseats;
    }

    public void setTotalseats(int totalseats) {
        this.totalseats = totalseats;
    }

    public int getPerformid() {
        return performid;
    }

    public void setPerformid(int performid) {
        this.performid = performid;
    }

    public String getPerformname() {
        return performname;
    }

    public void setPerformname(String performname) {
        this.performname = performname;
    }

    public int getPlayroomid() {
        return playroomid;
    }

    public void setPlayroomid(int playroomid) {
        this.playroomid = playroomid;
    }

    public int getVueneid() {
        return vueneid;
    }

    public void setVueneid(int vueneid) {
        this.vueneid = vueneid;
    }

    public String getPlayroomname() {
        return playroomname;
    }

    public void setPlayroomname(String playroomname) {
        this.playroomname = playroomname;
    }

    public String getPlayroomdescription() {
        return playroomdescription;
    }

    public void setPlayroomdescription(String playroomdescription) {
        this.playroomdescription = playroomdescription;
    }

    public String getVuenename() {
        return vuenename;
    }

    public void setVuenename(String vuenename) {
        this.vuenename = vuenename;
    }

    public String getVuenedescription() {
        return vuenedescription;
    }

    public void setVuenedescription(String vuenedescription) {
        this.vuenedescription = vuenedescription;
    }

    public String getBegintime() {
        return begintime;
    }

    public void setBegintime(String begintime) {
        this.begintime = begintime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public double getSeatprice() {
        return seatprice;
    }

    public void setSeatprice(double seatprice) {
        this.seatprice = seatprice;
    }

    public String getPerformtype() {
        return performtype;
    }

    public void setPerformtype(String performtype) {
        this.performtype = performtype;
    }

    public String getPerformdescription() {
        return performdescription;
    }

    public void setPerformdescription(String performdescription) {
        this.performdescription = performdescription;
    }

    public String getVacancy() {
        return vacancy;
    }

    public void setVacancy(String vacancy) {
        this.vacancy = vacancy;
    }

    public int getCountvacancy() {
        return countvacancy;
    }

    public void setCountvacancy(int countvacancy) {
        this.countvacancy = countvacancy;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPerformkeys() {
        return performkeys;
    }

    public void setPerformkeys(String performkeys) {
        this.performkeys = performkeys;
    }
}
