package com.olticketsbooking.ticketsbooking.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(
        name = "perform"
)
public class Perform {
    int performid;
    int playroomid;
    int vueneid;
    String playroomname;
    String playroomdescription;
    String vuenename;
    String vuenedescription;
    long begintime;
    long endtime;
    double seatprice;
    String performtype;
    String performname;
    String performdescription;
    String vacancy;
    int countvacancy;
    int totalseats;
    int state;
    String performkeys;


    public Perform() {
    }

    @Id
    @GeneratedValue
    public int getPerformid() {
        return this.performid;
    }

    public void setPerformid(int performid) {
        this.performid = performid;
    }

    public String getPerformname() {
        return this.performname;
    }

    public void setPerformname(String performname) {
        this.performname = performname;
    }

    public int getPlayroomid() {
        return this.playroomid;
    }

    public void setPlayroomid(int playroomid) {
        this.playroomid = playroomid;
    }

    public int getVueneid() {
        return this.vueneid;
    }

    public void setVueneid(int vueneid) {
        this.vueneid = vueneid;
    }

    public String getPlayroomname() {
        return this.playroomname;
    }

    public void setPlayroomname(String playroomname) {
        this.playroomname = playroomname;
    }

    public String getPlayroomdescription() {
        return this.playroomdescription;
    }

    public void setPlayroomdescription(String playroomdescription) {
        this.playroomdescription = playroomdescription;
    }

    public String getVuenename() {
        return this.vuenename;
    }

    public void setVuenename(String vuenename) {
        this.vuenename = vuenename;
    }

    public String getVuenedescription() {
        return this.vuenedescription;
    }

    public void setVuenedescription(String vuenedescription) {
        this.vuenedescription = vuenedescription;
    }

    public long getBegintime() {
        return this.begintime;
    }

    public void setBegintime(long begintime) {
        this.begintime = begintime;
    }

    public long getEndtime() {
        return this.endtime;
    }

    public void setEndtime(long endtime) {
        this.endtime = endtime;
    }

    public double getSeatprice() {
        return this.seatprice;
    }

    public void setSeatprice(double seatprice) {
        this.seatprice = seatprice;
    }

    public String getPerformtype() {
        return this.performtype;
    }

    public void setPerformtype(String performtype) {
        this.performtype = performtype;
    }

    public String getPerformdescription() {
        return this.performdescription;
    }

    public void setPerformdescription(String performdescription) {
        this.performdescription = performdescription;
    }

    public String getVacancy() {
        return this.vacancy;
    }

    public void setVacancy(String vacancy) {
        this.vacancy = vacancy;
    }

    public int getTotalseats() {
        return this.totalseats;
    }

    public void setTotalseats(int totalseats) {
        this.totalseats = totalseats;
    }

    public int getCountvacancy() {
        return this.countvacancy;
    }

    public void setCountvacancy(int countvacancy) {
        this.countvacancy = countvacancy;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getPerformkeys() {
        return performkeys;
    }

    public void setPerformkeys(String performkeys) {
        this.performkeys = performkeys;
    }
}
