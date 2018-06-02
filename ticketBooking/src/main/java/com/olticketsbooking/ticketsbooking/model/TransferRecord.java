package com.olticketsbooking.ticketsbooking.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transferrecord")
public class TransferRecord {

    int transferrecordid;
    int transfertype;//0:pay 1:all-back 2:partly-back 3:other
    int formwhom;
    String fromname;
    int formcardid;
    int towhom;
    String toname;
    int tocardid;
    double amount;
    String description;
    int orderid;
    long transfertime;

    public TransferRecord() {
    }

    @Id
    @GeneratedValue
    public int getTransferrecordid() {
        return transferrecordid;
    }

    public void setTransferrecordid(int transferrecordid) {
        this.transferrecordid = transferrecordid;
    }

    public int getTransfertype() {
        return transfertype;
    }

    public void setTransfertype(int transfertype) {
        this.transfertype = transfertype;
    }

    public int getFormwhom() {
        return formwhom;
    }

    public void setFormwhom(int formwhom) {
        this.formwhom = formwhom;
    }

    public String getFromname() {
        return fromname;
    }

    public void setFromname(String fromname) {
        this.fromname = fromname;
    }

    public int getFormcardid() {
        return formcardid;
    }

    public void setFormcardid(int formcardid) {
        this.formcardid = formcardid;
    }

    public int getTowhom() {
        return towhom;
    }

    public void setTowhom(int towhom) {
        this.towhom = towhom;
    }

    public String getToname() {
        return toname;
    }

    public void setToname(String toname) {
        this.toname = toname;
    }

    public int getTocardid() {
        return tocardid;
    }

    public void setTocardid(int tocardid) {
        this.tocardid = tocardid;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public long getTransfertime() {
        return transfertime;
    }

    public void setTransfertime(long transfertime) {
        this.transfertime = transfertime;
    }
}
