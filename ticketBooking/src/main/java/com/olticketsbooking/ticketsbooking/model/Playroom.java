package com.olticketsbooking.ticketsbooking.model;



//DROP TABLE IF EXISTS `playroom`;
///*!40101 SET @saved_cs_client     = @@character_set_client */;
///*!40101 SET character_set_client = utf8 */;
//        CREATE TABLE `playroom` (
//        `playroomid` int(11) NOT NULL AUTO_INCREMENT,
//        `vueneid` int(11) NOT NULL,
//        `name` varchar(45) DEFAULT NULL,
//        `seatsperrow` int(10) DEFAULT NULL,
//        `countrows` int(10) unsigned zerofill NOT NULL,
//        `countseats` int(10) unsigned zerofill NOT NULL,
//        `description` varchar(2555) DEFAULT NULL,
//        PRIMARY KEY (`playroomid`),
//        UNIQUE KEY `playroomid_UNIQUE` (`playroomid`)
//        ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
///*!40101 SET character_set_client = @saved_cs_client */;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "playroom")
public class Playroom {
    int playroomid;
    int vueneid;
    String playroomname;
    int totalseats;
    String description;//用于描述座位排布
    int state;//0zhengchang  1 shanchu
    public Playroom() {
    }

    public Playroom(int vueneid,String playroomname,int totalseats,String description){
        this.vueneid =vueneid;
        this.playroomname=playroomname;
        this.totalseats=totalseats;
        this.description=description;

    }
    @Id
    @GeneratedValue
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

    public int getTotalseats() {
        return totalseats;
    }

    public void setTotalseats(int totalseats) {
        this.totalseats = totalseats;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
