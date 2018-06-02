package com.olticketsbooking.ticketsbooking.utils;

import com.olticketsbooking.ticketsbooking.model.Orders;
import com.olticketsbooking.ticketsbooking.model.Playroom;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;

public class ClassificationUtil {
    private static DecimalFormat df = new DecimalFormat( "0.0"); //设置double类型小数点后位数格式
    private static DecimalFormat df1 = new DecimalFormat( "0.00"); //设置double类型小数点后位数格式
    /************************           userPart          **************************/
    /***************   该部分为用户消费数据统计   *****************/

    //0-4 常规用户分级,根据个人消费额度(即等级制度)
    //0-白板用户,1-普通用户,2-初级爱好者,3-中级爱好者,4-重度爱好者;
    //5-团体票购买者,有多笔团体购票(大于5张)记录者,暂定为10笔以上或有20笔以上购买记录且团体票比例大于25%;
    //6-疑似黄牛购买者,大量(>100)的团体购票,极多笔团体购票(大于五张),暂定为团购票总数大于3000张者,或有大量退票记录者
    // (大于100次且大于25%或大于40次且大于30%或大于20次且大于35%);


    //输入结果X10:ountAllOrder,countAllTicket,countSuccOrder,countFailOrder,countSuccGroupOrder
    // ,countFailGroupOrder, sumSuccTicket,sumFailTicket,sumSuccGroupTicket,sumFailGroupTicket
    //输出 0-按等级制来定用户类型   输出1,2 对应 5,6
    public static int getUserType(int[] info){
        int res=0;
        int groupOrders=info[4]+info[5];
        int groupTickets=info[8]+info[9];
        double grouporderPercentage=(groupOrders+0.0)/(info[0]+0.0);
        double grouporderFailPercentage=(info[5]+0.0)/(groupOrders+0.0);
        if(groupOrders>10||(info[0]>20&&grouporderPercentage>0.25)){
            res=1;
            if(groupOrders>100||groupTickets>3000||(info[0]>100&&grouporderFailPercentage>0.25)||(info[0]>40&&grouporderFailPercentage>0.3)||(info[0]>20&&grouporderFailPercentage>0.35)){
                res=2;
            }
        }
        return res;
    }

    //输入格式o.performid,o.performstart,sum(o.countseats),o.orderstate
    //获取完成(线下购买)/取消订单票务数据----ALL,两类订单数,团购票次数(>5张),
    //'0:已下单\n1:已支付\n2:取消\n3:完成',4:实体购买(已取票,无法网络退票)
    //输出结果X10:ountAllOrder,countAllTicket,countSuccOrder,countFailOrder,countSuccGroupOrder
    // ,countFailGroupOrder, sumSuccTicket,sumFailTicket,sumSuccGroupTicket,sumFailGroupTicket
    public static int[] getCountUserOrders(List<Object[]> list){
        int countAllOrder=0,countAllTicket=0,countSuccOrder=0,countFailOrder=0,countSuccGroupOrder=0,countFailGroupOrder=0,
                sumSuccTicket=0,sumFailTicket=0,sumSuccGroupTicket=0,sumFailGroupTicket=0;
        for(int i=0;i<list.size();i++){
            int seats=Integer.parseInt(list.get(i)[2].toString());
            int state=Integer.parseInt(list.get(i)[3].toString());
            countAllOrder=countAllOrder+1;
            countAllTicket=countAllTicket+seats;
            if(state==3){
                countSuccOrder=countSuccOrder+1;
                sumSuccTicket=sumSuccTicket+seats;
                if(seats>5){
                    countSuccGroupOrder=countSuccGroupOrder+1;
                    sumSuccGroupTicket=sumSuccGroupTicket+seats;
                }
            }else if(state==2){
                countFailOrder=countFailOrder+1;
                sumFailTicket=sumFailTicket+seats;
                if(seats>5){
                    countFailGroupOrder=countFailGroupOrder+1;
                    sumFailGroupTicket=sumFailGroupTicket+seats;
                }
            }
        }
        int[] res={countAllOrder,countAllTicket,countSuccOrder,countFailOrder,countSuccGroupOrder,countFailGroupOrder,
                sumSuccTicket,sumFailTicket,sumSuccGroupTicket,sumFailGroupTicket};
        return res;
    }
    /*****************   该部分为用户爱好统计   *******************/
    //关键词爱好分级 like "电影<>1,周杰伦<>2"
    public static String getPreferencCombine(String[][] prefer){
        String str="";
        if(prefer.length<1){
            return "";
        }
        if(prefer.length==1){
            return prefer[0][0]+"<>"+getPreferenceLevel(prefer[0][1]);
        }
        for(int i=0;i<prefer.length-1;i++){
            str=str+prefer[i][0]+"<>"+getPreferenceLevel(prefer[i][1])+",";
        }
        str=str+prefer[prefer.length-1][0]+"<>"+getPreferenceLevel(prefer[prefer.length-1][1]);
        return str;
    }
    public static int getPreferenceLevel(String str){
        if(Double.parseDouble(str)>0.9){
            return 4;
        }else if(Double.parseDouble(str)>0.6){
            return 3;
        }else if(Double.parseDouble(str)>0.25){
            return 2;
        }else {
            return 1;
        }

    }
    //求关键词百分比
    public static String[][] getPercentageOfPreference(List<Object[]> list){

        String[][] oriresult=getCountOfPreference(list);
        String[][] result=new String[oriresult.length][2];
        for(int i=0;i<oriresult.length;i++){
            result[i]=new String[]{oriresult[i][0], df.format((Integer.parseInt(oriresult[i][1])+0.0)/(list.size()+0.0))+""};
        }

        return result;

    }
    //求关键词统计表
    public static String[][] getCountOfPreference(List<Object[]> list){

        ArrayList<Object> prelist=getPreferenceList(list);

        String[][] result=new String[prelist.size()][2];

        for(int i=0;i<prelist.size();i++){
            result[i]=new String[]{prelist.get(i).toString(),"0"};
        }
        for(int i=0;i<list.size();i++){
            Object[] objs=getKeysFromTypeString(list.get(i)[2]);
            for(int j=0;j<objs.length;j++) {
                int index = prelist.indexOf(objs[j]);
                System.out.println("list is:" + prelist.toString() + "  ,Obj is:"+objs[j]+"  ,index is:" + index);
                result[index] = new String[]{prelist.get(index).toString(), Integer.parseInt(result[index][1]) + 1 + ""};
            }
        }
        return result;

    }
    //获取关键词列表
    public static ArrayList<Object>  getPreferenceList(List<Object[]> list){
        ArrayList<Object> arrayList=new ArrayList<Object>();
        for(int i=0;i<list.size();i++){
            for(Object obj:getKeysFromTypeString(list.get(i)[2])) {
                if (arrayList.contains(obj) || obj.toString().equals("")) {
                    continue;
                } else {
                    arrayList.add(obj);
                }
            }
        }

        return arrayList;

    }
    //获取关键词序列
    private static Object[] getKeysFromTypeString(Object obj){
        if(obj==null){
            return new Object[0];
        }
        String strings=obj.toString().replace(" ","");//去除空格
//        System.out.println("Str:=="+getFormatString(strings));
        String[] strs=getFormatString(strings).split(",");
//        printObj(strs);
        if(strs.length<=0){
            return new Object[0];
        }
        Object[] objs=new Object[strs.length];
        for(int i=0;i<strs.length;i++){
            objs[i]=strs[i];
        }
//        printObj(objs);
        return objs;

    }
    //获取关键词序列-消除字符串最后的多余','
    public static String getFormatString(String string){
        String resstr=string;
        if(string==null||string.length()<=0){
            return "";
        }
        if(string.charAt(string.length()-1)==','){
            resstr=string.substring(0,string.length()-2);
        }
        return  resstr;
    }
    private static void printObj(Object[] objs){
        String sr="";
        for(Object obj:objs){
            sr=sr+"+"+obj.toString();
        }
        System.out.println(sr);
    }

    /************************          vuenePart         **************************/

    //根据订单统计及场馆放映室信息来划分
//    单月售票数少于1000(暂定)and总座位规模小于300的场馆为-1-小型场馆
//    单月售票数大于5000(暂定)或总座位规模大于500的场馆为-2-热门场馆
//    单月售票数大于10000(暂定)或总座位规模大于1000的场馆为-3-顶尖场馆
//    余下的为-0-普通场馆
    public static int getVueneType(List<Orders> orders, List<Playroom> playrooms){
        int sumOrders=0;
        int sumTickes=0;
        int sumSeats=0;
        for(Orders order:orders){
            sumOrders++;
            sumTickes+=order.getCountseats();
        }
        for(Playroom playroom:playrooms){
            sumSeats+=playroom.getTotalseats();
        }
        if(sumTickes>10000||sumSeats>1000){
            return 3;
        }else if(sumTickes>5000||sumSeats>500){
            return 2;
        }else if(sumTickes<1000&&sumSeats<300){
            return 1;
        }else {
            return 0;
        }

    }


    /************************        platformPart        **************************/









    /************************         commonToolsForDataProcess       **************************/
    //对两个二维string数组的值加权求和_要求第一个数组的内容包含第二个数组的内容,(不做第一个相对第二个的缺少校验)
    public static String[][] getWeightedAverageValueOfUserPreference(String[][] str1,double percentage1,String[][] str2,double percentage2){
        String[][] result=str1;
        ArrayList<String> factorList=new ArrayList<String>();

        if(str1.length==0){
            return str1;
        }
        for(int i=0;i<str1.length;i++){
            factorList.add(str1[i][0]);

        }
        for(int i=0;i<str2.length;i++){
            int index=factorList.indexOf(str2[i][0]);
            result[index][1]=df.format(Double.parseDouble(str1[index][1])*percentage1+Double.parseDouble(str2[i][1])*percentage2)+"";
        }
        return result;

    }

    //获取object的属性值
    public static Object getSingleValueOfObject(String fieldName, Object o){
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //获取list Object的属性值
    public static List<Object> getSingleValueOfObjectList(String fieldName, List<Object> o){
        try {
            List<Object> result=new ArrayList<Object>();
            for(Object obj : o){
                result.add(getSingleValueOfObject(fieldName,obj));
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //obj转int计算性能测试,1000万次运算,虽有差别,但可以忽略
    public static void getObjSum(){
        long t1=System.currentTimeMillis();
        for(int i=0;i<10000000;i++) {
            Object a = 0;
            Object b = 1;
            int c = Integer.parseInt(a.toString()) + Integer.parseInt(b.toString());
        }
        long t2=System.currentTimeMillis();
        System.out.println("test obj sum"+(t2-t1));
    }

    public static void getSum(){
        long t1=System.currentTimeMillis();
        for(int i=0;i<10000000;i++) {
            int a = 0+i;
            int b = 1+i;
            b = a+b;

        }
        long t2=System.currentTimeMillis();
        System.out.println("test int sum"+(t2-t1));
    }






}
