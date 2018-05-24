package com.olticketsbooking.ticketsbooking.model;



//DROP TABLE IF EXISTS `perform`;
///*!40101 SET @saved_cs_client     = @@character_set_client */;
///*!40101 SET character_set_client = utf8 */;
//        CREATE TABLE `perform` (
//        `performid` int(11) NOT NULL AUTO_INCREMENT,
//        `playroomid` int(11) NOT NULL,
//        `begintime` datetime NOT NULL,
//        `endtime` datetime NOT NULL,
//        `seatprice` varchar(255) NOT NULL COMMENT '根据seat的列呈数组排布。',
//        `type` varchar(255) DEFAULT NULL,
//        `description` varchar(2555) DEFAULT NULL,
//        `vacancy` varchar(2555) DEFAULT NULL COMMENT 'like 1-1,1-2,1-3,2-1...',
//        PRIMARY KEY (`performid`),
//        UNIQUE KEY `performid_UNIQUE` (`performid`)
//        ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
///*!40101 SET character_set_client = @saved_cs_client */;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "perform")
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
    String vacancy;  //like1,2,3,4,5
    int countvacancy;
    int totalseats;
    int state; //0:初创,1:进行中,2:过期,3:取消,4:其他

    public Perform() {
    }

    @Id
    @GeneratedValue
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

    public long getBegintime() {
        return begintime;
    }

    public void setBegintime(long begintime) {
        this.begintime = begintime;
    }

    public long getEndtime() {
        return endtime;
    }

    public void setEndtime(long endtime) {
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

    public int getTotalseats() {
        return totalseats;
    }

    public void setTotalseats(int totalseats) {
        this.totalseats = totalseats;
    }

    public int getCountvacancy() {
        return countvacancy;
    }

    public void setCountvacancy(int countvacancy) {
        this.countvacancy = countvacancy;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
