package com.olticketsbooking.ticketsbooking.model;

import javax.persistence.*;

@Entity
@Table(name="user",
        uniqueConstraints ={@UniqueConstraint(columnNames={"username"})})
public class User {
    int id;
    //用户账号
    String username;
    //用户昵称
    String name;
    //密码
    String password;
    //用户电子邮件
    String email;
    //用户电话
    String telephone;
    //用户账号状态(0表示未激活 1表示激活 2zhuxiao )
    int state;
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


    public User() {
    }

    public User(int id){
        this.id = id;
    }

    public User(String username, String name, String password, String email, String telephone){
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.telephone = telephone;
    }

    @Id
    @GeneratedValue
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
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
