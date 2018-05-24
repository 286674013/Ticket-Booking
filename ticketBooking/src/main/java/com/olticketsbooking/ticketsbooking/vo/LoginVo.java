package com.olticketsbooking.ticketsbooking.vo;

public class LoginVo {
    String name;
    // 0 用户登录 1 商家登录 2管理员登录 3未登录
    int type;

    public LoginVo(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
