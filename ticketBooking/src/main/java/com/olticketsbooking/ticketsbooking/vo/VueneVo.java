package com.olticketsbooking.ticketsbooking.vo;

import com.olticketsbooking.ticketsbooking.model.Vuene;

public class VueneVo {
    int vueneid;
    String vuenecode;
    String password;
    String vuenename;
    String vueneaddress;
    String vuenephone;
    String vuenedescription;
    double balance;
    int state;
    public VueneVo() {
    }

    public VueneVo(Vuene vuene){
        this.vueneid=vuene.getVueneid();
        this.vuenecode=vuene.getVuenecode();
        this.password=vuene.getPassword();
        this.vuenename=vuene.getVuenename();
        this.vuenephone=vuene.getVuenephone();
        this.vueneaddress=vuene.getVueneaddress();
        this.vuenedescription=vuene.getVuenedescription();
        this.balance=vuene.getBalance();
        this.state=vuene.getState();


    }

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
