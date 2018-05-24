package com.olticketsbooking.ticketsbooking.model;


//DROP TABLE IF EXISTS `venue`;
///*!40101 SET @saved_cs_client     = @@character_set_client */;
///*!40101 SET character_set_client = utf8 */;
//        CREATE TABLE `venue` (
//        `vueneid` int(11) NOT NULL AUTO_INCREMENT,
//        `venuecode` varchar(7) NOT NULL,
//        `venuename` varchar(45) NOT NULL,
//        `address` varchar(255) DEFAULT NULL,
//        `description` varchar(2555) DEFAULT NULL,
//        PRIMARY KEY (`vueneid`),
//        UNIQUE KEY `venuecode_UNIQUE` (`venuecode`),
//        UNIQUE KEY `venueid_UNIQUE` (`vueneid`)
//        ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
///*!40101 SET character_set_client = @saved_cs_client */;

import javax.persistence.*;

@Entity
@Table(name = "vuene",
        uniqueConstraints ={@UniqueConstraint(columnNames={"vuenecode"})})

public class Vuene {
    int vueneid;
    String vuenecode;
    String password;
    String vuenename;
    String vueneaddress;
    String vuenephone;
    String vuenedescription;
    double balance;
    int state;// 0未激活  1激活  2停用
    public Vuene() {
    }


    @Id
    @GeneratedValue
    public int getVueneid() {
        return vueneid;
    }

    public void setVueneid(int vueneid) {
        this.vueneid = vueneid;
    }

    public String getVuenecode() {
        return vuenecode;
    }

    public void setVuenecode(String vuenecode) {
        this.vuenecode = vuenecode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVuenename() {
        return vuenename;
    }

    public void setVuenename(String vuenename) {
        this.vuenename = vuenename;
    }

    public String getVuenephone() {
        return vuenephone;
    }

    public void setVuenephone(String vuenephone) {
        this.vuenephone = vuenephone;
    }

    public String getVueneaddress() {
        return vueneaddress;
    }

    public void setVueneaddress(String vueneaddress) {
        this.vueneaddress = vueneaddress;
    }

    public String getVuenedescription() {
        return vuenedescription;
    }

    public void setVuenedescription(String vuenedescription) {
        this.vuenedescription = vuenedescription;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
