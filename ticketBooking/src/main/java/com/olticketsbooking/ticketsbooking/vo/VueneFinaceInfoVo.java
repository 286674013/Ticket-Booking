package com.olticketsbooking.ticketsbooking.vo;

import java.util.LinkedList;
import java.util.List;


public class VueneFinaceInfoVo {
    private String name;
    //总收入
    private Long orderNum;
    private double balanceNum;
    private List<VueneFinaceCountVo> list;

    public VueneFinaceInfoVo() {
    }

    public VueneFinaceInfoVo(String name, Long orderNum,  double balanceNum,  List<VueneFinaceCountVo> list) {
        this.name = name;
        this.orderNum = orderNum;
        this.balanceNum = balanceNum;
        for(VueneFinaceCountVo o:list){
            o.setDate(o.getDate().split(" ")[0]);
        }
        this.list = combine(list);
    }
    public List<VueneFinaceCountVo> combine(List<VueneFinaceCountVo> list){
        List<VueneFinaceCountVo> result=new LinkedList<VueneFinaceCountVo>();;
        List<String> list1=new LinkedList<String>();
        for(VueneFinaceCountVo o:list){
            if(!list1.contains(o.getDate())){
                list1.add(o.getDate());
            }
        }
        double[] income=new double[list1.size()];
        for(VueneFinaceCountVo o:list){
            int index=list1.indexOf(o.getDate());
            income[index]= income[index]+o.getPrice();
        }
        for(int i=0;i<list1.size();i++){
            result.add(new VueneFinaceCountVo(list1.get(i),income[i]));
        }
        return result;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }


    public double getBalanceNum() {
        return balanceNum;
    }

    public void setBalanceNum(double balanceNum) {
        this.balanceNum = balanceNum;
    }

    public List<VueneFinaceCountVo> getList() {
        return list;
    }

    public void setList(List<VueneFinaceCountVo> list) {
        this.list = list;
    }
}
