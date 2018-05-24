package com.olticketsbooking.ticketsbooking.vo;

public class OrderFinaceManagerInfoVo {
    String date;
    double income;
    //预订人数
    long number;

    public OrderFinaceManagerInfoVo(String date, double income, long number) {
        this.date = date;
        this.income = income;
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }
}
