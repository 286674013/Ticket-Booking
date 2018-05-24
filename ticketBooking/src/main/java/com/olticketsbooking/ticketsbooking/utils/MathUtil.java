package com.olticketsbooking.ticketsbooking.utils;

import java.math.BigDecimal;


public class MathUtil {
    public static double roundNum(double num,int count){
        BigDecimal   tem   =   new BigDecimal(num);
        return tem.setScale(count,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static int roundIntNum(double num){
        BigDecimal   tem   =   new BigDecimal(num);
        return tem.setScale(0,BigDecimal.ROUND_HALF_UP).intValue();
    }
//    public  static  void main(String args[]){
//      System.out.println( roundNum(11.24,1));
//        System.out.println( roundNum(11.25,1));
//        System.out.println( roundNum(11.245,1));
//        System.out.println( roundNum(11.54,1));
//    }
}
