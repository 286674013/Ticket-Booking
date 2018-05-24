package com.olticketsbooking.ticketsbooking.vo;

import com.olticketsbooking.ticketsbooking.model.User;
import com.olticketsbooking.ticketsbooking.utils.IDFormatUtil;

public class UserManagerVo {
    private  String id;
    private String username;
    private String balance;
    private double discount;
    private String state;


    public UserManagerVo(User user){
        this.id = IDFormatUtil.encodeUserId(user.getId());
        this.username = user.getName();
        this.balance = "¥"+user.getBalance();
        this.discount = user.getDiscount();

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }


    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
