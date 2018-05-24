package com.olticketsbooking.ticketsbooking.utils;

import com.olticketsbooking.ticketsbooking.model.Orders;

import java.util.List;

public class OrderAnalyzeUtil {

    public static int[] countOrderState(List<Orders> list){
        int[] count={0,0,0,0};//'0:已下单\n1:已支付\n2:取消\n3:完成',4:实体购买(已取票,无法网络退票)
        for(Orders orders:list){
            count[orders.getOrderstate()]++;
        }
        return count;
    }
    public static int[] countOrderPay(List<Orders> list){
        int[] count={0,0,0};//'0:现金 1:余额  2:银行卡
        for(Orders orders:list){
            count[orders.getPaymethod()]++;
        }
        return count;
    }
}
