package com.olticketsbooking.ticketsbooking.model;



//DROP TABLE IF EXISTS `emailactivation`;
///*!40101 SET @saved_cs_client     = @@character_set_client */;
///*!40101 SET character_set_client = utf8 */;
//        CREATE TABLE `emailactivation` (
//        `emailactivationid` int(11) NOT NULL AUTO_INCREMENT,
//        `email` varchar(155) DEFAULT NULL,
//        `code` varchar(45) NOT NULL,
//        `starttime` datetime NOT NULL,
//        `endtime` datetime DEFAULT NULL,
//        `activationtime` datetime DEFAULT NULL,
//        PRIMARY KEY (`emailactivationid`),
//        UNIQUE KEY `emailactivationid_UNIQUE` (`emailactivationid`),
//        UNIQUE KEY `email_UNIQUE` (`email`)
//        ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
///*!40101 SET character_set_client = @saved_cs_client */;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emailactivation")
public class EmailActivation {
    int emailactivationid;
    String email;
    int userid;
    int activationstate;   //0:未激活 1;激活   2:超出时间
    long starttime;
    long endtime;
    long activationtime;
    String token;

    public EmailActivation() {
    }

    @Id
    @GeneratedValue
    public int getEmailactivationid() {
        return emailactivationid;
    }

    public void setEmailactivationid(int emailactivationid) {
        this.emailactivationid = emailactivationid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getActivationstate() {
        return activationstate;
    }

    public void setActivationstate(int activationstate) {
        this.activationstate = activationstate;
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

    public long getActivationtime() {
        return activationtime;
    }

    public void setActivationtime(long activationtime) {
        this.activationtime = activationtime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
