package com.olticketsbooking.ticketsbooking.vo;

import com.olticketsbooking.ticketsbooking.model.EmailActivation;
import com.olticketsbooking.ticketsbooking.utils.DateUtil;

public class EmailActivationVo {
    int emailactivationid;
    String email;
    int userid;
    String activationstate;   //0:未激活 1;激活   2:超出时间
    String starttime;
    String endtime;
    String activationtime;
    String token;

    public EmailActivationVo() {
    }
    public EmailActivationVo(EmailActivation emailActivation) {
        this.emailactivationid=emailActivation.getEmailactivationid();
        this.email=emailActivation.getEmail();
        this.userid=emailActivation.getUserid();
        if(emailActivation.getActivationstate()==0){
            this.activationstate="未激活";
        }else if(emailActivation.getActivationstate()==1){
            this.activationstate="已激活";
        }else if(emailActivation.getActivationstate()==2){
            this.activationstate="已过期";
        }else{
            this.activationstate="未激活";
        }
        this.starttime= DateUtil.getLongToDateString(emailActivation.getStarttime());
        this.endtime= DateUtil.getLongToDateString(emailActivation.getEndtime());
        this.activationtime= DateUtil.getLongToDateString(emailActivation.getActivationtime());
        this.token=emailActivation.getToken();
    }

    public int getEmailactivationid() {
        return emailactivationid;
    }

    public void setEmailactivationid(int emailactivationid) {
        this.emailactivationid = emailactivationid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getActivationstate() {
        return activationstate;
    }

    public void setActivationstate(String activationstate) {
        this.activationstate = activationstate;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getActivationtime() {
        return activationtime;
    }

    public void setActivationtime(String activationtime) {
        this.activationtime = activationtime;
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
