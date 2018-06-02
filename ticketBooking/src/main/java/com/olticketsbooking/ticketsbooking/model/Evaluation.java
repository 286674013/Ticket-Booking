package com.olticketsbooking.ticketsbooking.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "evaluation")
public class Evaluation {
    int evaluationid;
    int orderid;
    int userid;
    int performid;
    int vueneid;
    String username;
    String performname;
    String vuenename;
    int point; //0-5
    String comment;

    public Evaluation() {
    }
    @Id
    @GeneratedValue
    public int getEvaluationid() {
        return evaluationid;
    }

    public void setEvaluationid(int evaluationid) {
        this.evaluationid = evaluationid;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getPerformid() {

        return performid;
    }

    public void setPerformid(int performid) {
        this.performid = performid;
    }

    public int getVueneid() {
        return vueneid;
    }

    public void setVueneid(int vueneid) {
        this.vueneid = vueneid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPerformname() {
        return performname;
    }

    public void setPerformname(String performname) {
        this.performname = performname;
    }

    public String getVuenename() {
        return vuenename;
    }

    public void setVuenename(String vuenename) {
        this.vuenename = vuenename;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
