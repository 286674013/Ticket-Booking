package com.olticketsbooking.ticketsbooking.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

//DROP TABLE IF EXISTS `discountvalue`;
///*!40101 SET @saved_cs_client     = @@character_set_client */;
///*!40101 SET character_set_client = utf8 */;
//        CREATE TABLE `discountvalue` (
//        `discountid` int(11) NOT NULL AUTO_INCREMENT,
//        `discountvalue` double unsigned zerofill NOT NULL,
//        `description` varchar(255) DEFAULT NULL,
//        `integral` double unsigned zerofill NOT NULL COMMENT '所需积分',
//        `validity` int(10) unsigned zerofill NOT NULL COMMENT '有效期 天',
//        PRIMARY KEY (`discountid`),
//        UNIQUE KEY `discountid_UNIQUE` (`discountid`)
//        ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
///*!40101 SET character_set_client = @saved_cs_client */;
@Entity
@Table(name = "discountvalue")
public class Discount {
    int discountid;
    double discount;
    String description;
    double integral;//所需积分
    int validity;//有效期(天)


    public Discount() {
    }

    @Id
    @GeneratedValue
    public int getDiscountid() {
        return discountid;
    }

    public void setDiscountid(int discountid) {
        this.discountid = discountid;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getIntegral() {
        return integral;
    }

    public void setIntegral(double integral) {
        this.integral = integral;
    }

    public int getValidity() {
        return validity;
    }

    public void setValidity(int validity) {
        this.validity = validity;
    }
}
