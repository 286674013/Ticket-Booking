package com.olticketsbooking.ticketsbooking.vo;

import java.util.LinkedList;
import java.util.List;

public class ManagerFinaceVo {
    String adminname;
    String balance;
    Long usernum;
    Long vuenenum;
    Long appointmentnum;
    Long checkin;
    Long uncheckin;
    List<OrderFinaceManagerInfoVo> list;

    public ManagerFinaceVo(String adminname, double balance,  Long usernum, Long vuenenum, Long appointmentnum, Long checkin, List<OrderFinaceManagerInfoVo> list) {
        this.adminname = adminname;
        this.balance ="¥"+ balance;
        this.usernum = usernum;
        this.vuenenum=vuenenum;
        this.appointmentnum = appointmentnum;
        this.checkin = checkin;
        this.uncheckin = appointmentnum-checkin;
        for(OrderFinaceManagerInfoVo o:list){
            o.setDate(o.getDate().split(" ")[0]);
        }

        this.list = combine(list);
    }

    public List<OrderFinaceManagerInfoVo> combine(List<OrderFinaceManagerInfoVo> list){
        List<OrderFinaceManagerInfoVo> result=new LinkedList<OrderFinaceManagerInfoVo>();;
        List<String> list1=new LinkedList<String>();
        for(OrderFinaceManagerInfoVo o:list){
            if(!list1.contains(o.getDate())){
                list1.add(o.getDate());
            }
        }
        double[] income=new double[list1.size()];
        long[] number=new long[list1.size()];
        for(OrderFinaceManagerInfoVo o:list){
            int index=list1.indexOf(o.getDate());
            income[index]= income[index]+o.getIncome();
            number[index]=number[index]+o.getNumber();
        }
        for(int i=0;i<list1.size();i++){
            result.add(new OrderFinaceManagerInfoVo(list1.get(i),income[i],number[i]));
        }
        return result;
    }
    public String getAdminname() {
        return adminname;
    }

    public void setAdminname(String adminname) {
        this.adminname = adminname;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public Long getUsernum() {
        return usernum;
    }

    public void setUsernum(Long usernum) {
        this.usernum = usernum;
    }

    public Long getAppointmentnum() {
        return appointmentnum;
    }

    public void setAppointmentnum(Long appointmentnum) {
        this.appointmentnum = appointmentnum;
    }

    public Long getCheckin() {
        return checkin;
    }

    public void setCheckin(Long checkin) {
        this.checkin = checkin;
    }

    public Long getUncheckin() {
        return uncheckin;
    }

    public void setUncheckin(Long uncheckin) {
        this.uncheckin = uncheckin;
    }

    public Long getVuenenum() {
        return vuenenum;
    }

    public void setVuenenum(Long vuenenum) {
        this.vuenenum = vuenenum;
    }

    public List<OrderFinaceManagerInfoVo> getList() {
        return list;
    }

    public void setList(List<OrderFinaceManagerInfoVo> list) {
        this.list = list;
    }
}
