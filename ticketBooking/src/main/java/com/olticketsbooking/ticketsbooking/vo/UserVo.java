package com.olticketsbooking.ticketsbooking.vo;

import com.olticketsbooking.ticketsbooking.model.User;

public class UserVo {
    int id;
    //用户账号
    String username;
    //用户昵称
    String name;
    //用户电子邮件
    String email;
    //用户电话
    String telephone;
    //用户账号状态(0表示未激活 1表示激活)
    String state;
    //用户等级
    int level;
    //用户享有的折扣
    double discount;
    //用户账号余额
    double balance;
    //会员积分
    double point;
    //用户总的消费额
    double countspending;
    //用户总的下单数
    int countorders;


    public UserVo() {
    }

    public UserVo(int id){
        this.id = id;
    }

    public UserVo(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.username=user.getUsername();

        if(user.getState()==0){
            this.state="已注册";
        }else if(user.getState()==1){
            this.state="已激活";
        }else if(user.getState()==2){
            this.state="已取消";
        }else if(user.getState()==0){
            this.state="其他";
        }
        this.balance = user.getBalance();
        this.level = user.getLevel();
        this.discount = user.getDiscount();
        this.point = user.getPoint();
        this.email = user.getEmail();
        this.telephone = user.getTelephone();
        this.countorders=user.getCountorders();
        this.countspending=user.getCountspending();
    }
    public UserVo(String username, String name, String email, String telephone){
        this.username = username;
        this.name = name;
        this.email = email;
        this.telephone = telephone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getState() {
        return state;
    }

    public void setState(String  state) {
        this.state = state;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public double getCountspending() {
        return countspending;
    }

    public void setCountspending(double purchase) {
        this.countspending = purchase;
    }

    public int getCountorders() {
        return countorders;
    }

    public void setCountorders(int countorders) {
        this.countorders = countorders;
    }

}


