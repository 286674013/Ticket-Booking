package com.olticketsbooking.ticketsbooking.service;

import com.olticketsbooking.ticketsbooking.config.MessageInfo;
import com.olticketsbooking.ticketsbooking.dao.OrderDao;
import com.olticketsbooking.ticketsbooking.dao.VueneDao;
import com.olticketsbooking.ticketsbooking.model.Vuene;
import com.olticketsbooking.ticketsbooking.utils.DataAnalysisForVuene;
import com.olticketsbooking.ticketsbooking.utils.DateUtil;
import com.olticketsbooking.ticketsbooking.vo.VueneFinaceCountVo;
import com.olticketsbooking.ticketsbooking.vo.VueneFinaceInfoVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

@Service
public class VueneService {

    @Resource
    VueneDao vueneDao;

    @Resource
    OrderDao orderDao;

    public MessageInfo findVuene(String username) {
        return vueneDao.findVuene(username);
    }

    public MessageInfo vueneInfo(int vueneid) {
        MessageInfo messageInfo = vueneDao.findVuenebyid(vueneid);
        if (messageInfo.getObject() == null)
            return new MessageInfo(false, "没有该账号信息");
        if (messageInfo.isResult()) {
            Vuene vuene = (Vuene) messageInfo.getObject();
//            messageInfo = orderDao.findOrderByVueneAllInfo(vueneid);
//            if (messageInfo.isResult()) {
//                List<Orders> orders = (List<Orders>) messageInfo.getObject();
//                double grossIncome = 0;
//                double profit = 0;
//                int appointmentNum = orders.size();
//                int checkinNum = 0;
//                int balanceNum = 0;
//                int cardNum = 0;
//                int cashNum = 0;
//
//                for (Orders order:orders){
//                    grossIncome+=order.getPrice();
//                    //现金支付,不征收税
//                    if (order.getMethod() ==2){
//                        cashNum+=1;
//                    }else {
//                        if (order.getMethod()==0)
//                            balanceNum+=1;
//                        else
//                            cardNum+=1;
//                    }
//                    if (order.getState() == 2){
//                        if (order.getIspay()==1) {
//                            if (order.getMethod() == 2)
//                                profit += order.getPrice();
//                            else
//                                profit += order.getPrice() * (1 - order.getTaxpercent());
//                        }
//                        checkinNum+=1;
//                    }
//                }

//                return new MessageInfo(true,new VueneFinaceInfoVo(vuene.getOwnername(), MathUtil.roundNum(grossIncome,1),MathUtil.roundNum(profit,1),appointmentNum,checkinNum,balanceNum,cardNum,cashNum
//                        ,(List<VueneFinaceCountVo>) orderDao.countIncome(vueneid).getObject()),"数据获取成功");
//
//            } else {
//                return messageInfo;
//            }
//        } else {
//            return messageInfo;
//        }
            return messageInfo;
        }
        return messageInfo;

    }


    public MessageInfo updateInfo(int vueneid, String vuenename,  String vueneaddress, String vuenephone, String vuenedescription){
        if (vuenename == null || vueneaddress == null || vuenephone == null || vuenedescription == null)
            return new MessageInfo(false,"信息填写错误,修改失败");
        MessageInfo messageInfo = vueneDao.findVuenebyid(vueneid);
        if (messageInfo.getObject() == null)
            return new MessageInfo(false, "没有该账号信息");
        if (messageInfo.isResult()){
            Vuene vuene =(Vuene) messageInfo.getObject();
            vuene.setVueneaddress(vueneaddress);
            vuene.setVuenename(vuenename);
            vuene.setVuenephone(vuenephone);
            vuene.setVuenedescription(vuenedescription);
            return vueneDao.update(vuene);
        }else {
            return messageInfo;
        }
    }



    public MessageInfo updatePassword(int vueneid,String oldPassword,String newPassword1,String newPassword2){
        MessageInfo messageInfo = vueneDao.findVuenebyid(vueneid);
        if (messageInfo.getObject() == null)
            return new MessageInfo(false, "没有该账号信息");
        if (messageInfo.isResult()){
            Vuene vuene =   (Vuene) messageInfo.getObject();
            if (!vuene.getPassword().equals(oldPassword))
                return new MessageInfo(false,"原密码错误,请重新输入");
            if (!newPassword1.equals(newPassword2))
                return new MessageInfo(false,"两次密码不一致,请重新输入");
            vuene.setPassword(newPassword1);
            return vueneDao.update(vuene);
        }else {
            return messageInfo;
        }
    }

    public MessageInfo getVueneApplication(){
        return vueneDao.getUnapproveVuene();
    }
    public MessageInfo getVueneApplicationApprove(int vueneid){
        return vueneDao.approveVuene(vueneid);
    }
    public MessageInfo getVueneApplicationDisapprove(int vueneid){
        return vueneDao.disapproveVuene(vueneid);
    }
    public MessageInfo vueneOrderCount(){
        MessageInfo messageInfo = vueneDao.getAllVuene();

            return messageInfo;
    }

    public  MessageInfo getVueneAll(){
        return vueneDao.getAllVuene();
    }

    public MessageInfo getVueneUpdateInfo(int vueneid){
        MessageInfo messageInfo = vueneDao.findVuenebyid(vueneid);
        if (messageInfo.getObject() == null)
            return new MessageInfo(false, "没有该账号信息");
        if (messageInfo.isResult()){
            Vuene vuene =   (Vuene) messageInfo.getObject();
            return new MessageInfo(true,vuene,"获取成功");
        }else {
            return messageInfo;
        }

    }
    public MessageInfo codeUsing(String code){
        MessageInfo messageInfo=vueneDao.codeUsing(code);
//        System.out.println("object:"+messageInfo.getObject()+"istrue:"+messageInfo.isResult());
        if(messageInfo.isResult()&&(messageInfo.getObject().toString().equals("[]"))){
            messageInfo.setResult(false);
            messageInfo.setReason("未被使用");
//            System.out.println("not using");
            return messageInfo;
        }
        return messageInfo;

    }

    public String getRandomCode(int length){
        StringBuffer flag = new StringBuffer();
        for (int i = 0; i < length; i++)
        {
            String sources = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"; // 加上一些字母，就可以生成pc站的验证码了
            Random rand = new Random();


            flag.append(sources.charAt(rand.nextInt(48)) + "");


        }
        //            System.out.println(flag.toString());
        return flag.toString();
    }


    public MessageInfo getVueneFinanceinfo(Vuene vuene){
        Long ordernum=(Long)orderDao.getAllOrdersNumByVuene(vuene.getVueneid()).getObject();
        List<VueneFinaceCountVo> list=(List<VueneFinaceCountVo>)orderDao.countIncome(vuene.getVueneid()).getObject();
        return new MessageInfo(true,new VueneFinaceInfoVo(vuene.getVuenename(),ordernum,vuene.getBalance(),list),"");
    }
    public MessageInfo getmoney(int vueneid,String bankcardid,String password,double money){
        return vueneDao.getmoney(vueneid,bankcardid,password,money);
    }

    public MessageInfo updateVueneTypeInfo(Vuene vuene){
        return  vueneDao.updateVueneTypeInfo(vuene);
    }

    public MessageInfo analysisVuene(Vuene vuene,int timeRange){
        DataAnalysisForVuene analysis=new DataAnalysisForVuene();
        analysis.setVueneid(vuene.getVueneid());
        analysis.setTimeRange(timeRange);
        MessageInfo messageInfo=orderDao.vueneCalAllOrderBetween(DateUtil.getPresentTimeLong()-DateUtil.getPassedDateLong(timeRange),DateUtil.getPresentTimeLong(),vuene.getVueneid());
        if(messageInfo.isResult()){
            return new MessageInfo(true,messageInfo.getObject().toString(),"获取场馆统计数据成功");
        }

        return new MessageInfo(false,"");
    }
}
