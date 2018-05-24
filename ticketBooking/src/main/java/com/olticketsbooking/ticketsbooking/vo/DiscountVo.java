package com.olticketsbooking.ticketsbooking.vo;

import com.olticketsbooking.ticketsbooking.model.Discount;

public class DiscountVo {
    int discountid;
    double discount;
    String description;
    double integral;//所需积分
    int validity;//有效期(天)


    public DiscountVo() {
    }

    public DiscountVo(Discount discount) {
        this.discountid=discount.getDiscountid();
        this.discount=discount.getDiscountid();
        this.description=discount.getDescription();
        this.integral=discount.getIntegral();
        this.validity=discount.getValidity();
    }

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
