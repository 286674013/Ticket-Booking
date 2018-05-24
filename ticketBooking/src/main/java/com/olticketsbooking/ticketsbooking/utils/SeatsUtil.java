package com.olticketsbooking.ticketsbooking.utils;

import java.util.regex.Pattern;

public class SeatsUtil {
    //将含有 空格 空白字符 \\ + - * 的字符串分割成需要的座位的数组
    public static  int[] getSeatFromInput(String seats){
        String[] array = seats.split("\\+|-|\\*|/|\\b|\\t|\\|\0|,");
//        System.out.println(array[0].toString());
        int count=0;
        for(int i=0;i<array.length;i++){
            if(!array[i].equals("")&&!array[i].equals(null)&&!array[i].equals(" ")&&isInteger(array[i])){
//                System.out.println(array[i]);
                count++;
            }
        }
        int[] keys=new int[count];
        if(count>0) {
            count=0;
            for(int i=0;i<array.length;i++){
                if(!array[i].equals("")&&!array[i].equals(null)&&!array[i].equals(" ")&&isInteger(array[i])){
                    keys[count]=Integer.parseInt(array[i]);
                    if(keys[count]<1){
                        return new int[0];
                    }
                    count++;
                }
            }
        }
        return keys;

    }
    /*方法二：推荐，速度最快
  * 判断是否为整数
  * @param str 传入的字符串
  * @return 是整数返回true,否则返回false
*/
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    //input("1,2,3,4...","111111111....")
    public static boolean checkAllVacant(String inputSeats,String performseats){
        int[] seats=getSeatFromInput(inputSeats);
        if(seats.length<1||performseats.length()<1){//错误的输入将导致无法匹配正确的位置,直接返回false
            return false;
        }
        for(int i=0;i<seats.length;i++){
            if(seats[i]>performseats.length()){
                return false;
            }
            if(performseats.charAt(seats[i]-1)=='0'){
                return false;
            }
        }
        return true;
    }
    //
    public static String getVacantSeats(String performseats,int seats){
        String result="";
        int temp=seats;
        int[] allseats=getSeatInt(performseats);
        for(int i=0;i<allseats.length;i++){
            if(allseats[i]==1&&seats>0){
                allseats[i]=0;
                if(seats==temp){
                    result= result+(i+1);
                }else {
                    result = result + "," + (i + 1);
                }
                seats=seats-1;
            }
            if(seats<=0){
                return result;
            }

        }
        return result;
    }
    //input("1\\2\\3\\4\\5","111111....")
    public static String updateSeats(String inputSeats,String performseats){
        if(checkAllVacant(inputSeats,performseats)){//若无法通过验证则扣除座位失败,返回原座位
            StringBuffer buffer = new StringBuffer(performseats);
            int[] seats=getSeatFromInput(inputSeats);
            for(int i=0;i<seats.length;i++){
                buffer.setCharAt(seats[i]-1,'0');
            }
            return buffer.toString();
        }
        return performseats;
    }
    //input("1\\2\\3\\4\\5","000001....")
    public static String getSeatsBack(String inputSeats,String performseats){
            StringBuffer buffer = new StringBuffer(performseats);
            int[] seats=getSeatFromInput(inputSeats);
            for(int i=0;i<seats.length;i++){
                buffer.setCharAt(seats[i]-1,'1');
            }
            return buffer.toString();
    }
    //input [1,2,3...] output 1,2,3,...
    public static String getSeatStringForDisplay(int seats[]){
        String seat="";
        if(seats==null||seats.length<1){
            return "";
        }else if(seats.length==1) {
            return ""+seats[0];
        }else {
            seat=seat+seats[0];
            for(int i=1;i<seats.length;i++){
                seat=seat+","+seats[i];
            }

        }
        return seat;
    }
    //input [0,1,1,0...] output 0110...  1为空  0为占用
    public static String getSeatString(int seats[]){
        String seat="";
        if(seats==null||seats.length<1){
            return "";
        }else if(seats.length==1) {
            return ""+seats[0];
        }else {
            for(int i=0;i<seats.length;i++){
                seat=seat+seats[i];
            }
        }
        return seat;
    }
    //反之
    public static int[] getSeatInt(String seats){

        int count=0;
//        String[] strs=seats.split(",");
        for(String retval: seats.split("")){
            if(!retval.equals("")&&!retval.equals(null)){
                count++;
            }
        }
        if(count<1){
            return null;
        }
        int seat[]=new int[count];
        count=0;
        for(String retval: seats.split("")){
            if(!retval.equals("")&&!retval.equals(null)){
                seat[count]=Integer.parseInt(retval);
                count++;
            }
        }
        return seat;
    }
    public static String getSeatsAll(int count){
        String seat="";
        if(count<=0){
            return null;
        }else if(count==1){
            return  1+"";
        }else if(count>1){
            for(int i=0;i<count;i++){
                seat=seat+1;
            }
        }
        return seat;
    }



}
