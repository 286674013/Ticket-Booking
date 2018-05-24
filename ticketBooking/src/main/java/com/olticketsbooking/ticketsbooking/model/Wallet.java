package com.olticketsbooking.ticketsbooking.model;



//DROP TABLE IF EXISTS `wallet`;
///*!40101 SET @saved_cs_client     = @@character_set_client */;
///*!40101 SET character_set_client = utf8 */;
//        CREATE TABLE `wallet` (
//        `walletid` int(11) NOT NULL AUTO_INCREMENT,
//        `userid` int(11) NOT NULL,
//        `balance` double unsigned zerofill DEFAULT NULL,
//        `integral` double unsigned zerofill DEFAULT NULL,
//        PRIMARY KEY (`walletid`),
//        UNIQUE KEY `userid_UNIQUE` (`userid`)
//        ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
///*!40101 SET character_set_client = @saved_cs_client */;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "wallet")
public class Wallet {
    int walletid;
    int userid;
    double balance;
    String password;

    public Wallet() {
    }

    public Wallet(int userid) {
        this.userid = userid;
    }

    @Id
    @GeneratedValue
    public int getWalletid() {
        return walletid;
    }

    public void setWalletid(int walletid) {
        this.walletid = walletid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
