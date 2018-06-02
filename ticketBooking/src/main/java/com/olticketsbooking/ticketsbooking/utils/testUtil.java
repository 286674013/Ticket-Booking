package com.olticketsbooking.ticketsbooking.utils;

public class testUtil {
    public static void main(String args[]){
        long time= DateUtil.getTimeDateOnlyLong("2018-3-27");
        System.out.println(time);
        System.out.println(DateUtil.getLongToDateString(time));
        String seats=SeatsUtil.getSeatsAll(100);

        System.out.println(seats);
        System.out.println(SeatsUtil.getSeatString(SeatsUtil.getSeatInt(seats)));
        int[] seat=SeatsUtil.getSeatFromInput("1\\2\\3\\4\\5");
        int[] seat2=SeatsUtil.getSeatFromInput("1 2 3 4 5 6 7");
//        int[] seat3=SeatsUtil.getSeatFromInput("1\2\3\4\5");
        System.out.println(SeatsUtil.getSeatString(seat));
        System.out.println(SeatsUtil.updateSeats("1\\3\\4\\5","111111111"));
        System.out.println(DateUtil.getPresentTimeLong());
        System.out.println( MathUtil.roundNum(11.244,2));
        System.out.println( MathUtil.roundNum(11.255,2));
        System.out.println( MathUtil.roundNum(11.2456,2));
        System.out.println( MathUtil.roundNum(11.5445,2));
        System.out.println(SeatsUtil.getVacantSeats("000111000111",4));
        System.out.println(SeatsUtil.updateSeats("1,2,3,5,6","111111111111111"));
        System.out.println(DateUtil.getDistanceOfTwoDate(1502391912340L, DateUtil.getPresentTimeLong()));
        System.out.println(DateUtil.getLongToDateString(DateUtil.getPresentTimeLong()+ DateUtil.getPassedDateLong(1)));

        System.out.println(DateUtil.getAfterLastMonthdate(6)+""+DateUtil.getBeforeNextMonthdate(6));

        System.out.println(24*60*60*1000);

        System.out.println(DateUtil.getLongToDateString(DateUtil.getAfterLastMonthdate(2))+DateUtil.getLongToDateString(DateUtil.getBeforeNextMonthdate(0)));
        ClassificationUtil.getSum();
        ClassificationUtil.getObjSum();
//        System.out.println(4-3.6==0.4);

    }
}
