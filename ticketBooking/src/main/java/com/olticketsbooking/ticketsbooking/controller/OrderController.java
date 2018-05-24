package com.olticketsbooking.ticketsbooking.controller;

import com.olticketsbooking.ticketsbooking.config.MessageInfo;
import com.olticketsbooking.ticketsbooking.model.*;
import com.olticketsbooking.ticketsbooking.service.OrderService;
import com.olticketsbooking.ticketsbooking.utils.DateUtil;
import com.olticketsbooking.ticketsbooking.utils.IDFormatUtil;
import com.olticketsbooking.ticketsbooking.vo.OrderVo;
import com.olticketsbooking.ticketsbooking.vo.VueneVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller

public class OrderController {


    @Resource
    OrderService orderService;


    @RequestMapping("order/user/add")
    @ResponseBody
    public MessageInfo addOrder(HttpSession session,int performid,String seats,int buytype,String discounttype,String remark) {
        User user = (User) session.getAttribute("user");
        if (user == null){
            return new MessageInfo(false,"登录信息获取失败,请重新登录");
        }
        return orderService.addOrder(user,performid,seats,buytype,discounttype,remark);

    }

    @RequestMapping("order/user/list")
    @ResponseBody
    public MessageInfo getUserOrders(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null){
            return new MessageInfo(false,"登录信息获取失败,请重新登录");
        }
        return orderService.getUserOrders(user.getId());
    }

    @RequestMapping("order/pay/balance")
    @ResponseBody
    public MessageInfo orderPayBalance(HttpSession session, int orderid) {
        User user = (User) session.getAttribute("user");
        if (user == null){
            return new MessageInfo(false,"登录信息获取失败,请重新登录");
        }
        return orderService.orderPayBalance(user.getId(), orderid);
    }

    @RequestMapping("order/pay/bank")
    @ResponseBody
    public MessageInfo orderPayBank(HttpSession session, int orderid, String cardid, String password) {
        User user = (User) session.getAttribute("user");
        if (user == null){
            return new MessageInfo(false,"登录信息获取失败,请重新登录");
        }
        return orderService.orderPayBank(user.getId(), orderid, cardid, password);
    }

    @RequestMapping("order/pay/cash")
    @ResponseBody
    public MessageInfo orderPayCash(HttpSession session, int orderid) {
        User user = (User) session.getAttribute("user");
        if (user == null){
            return new MessageInfo(false,"登录信息获取失败,请重新登录");
        }
        return orderService.orderPayCash(user.getId(), orderid);

    }

    @RequestMapping("order/user/cancle")
    @ResponseBody
    public MessageInfo orderCancle(HttpSession session, int orderid) {
        User user = (User) session.getAttribute("user");
        if (user == null){
            return new MessageInfo(false,"登录信息获取失败,请重新登录");
        }
        return orderService.orderCancle(user.getId(), orderid);
    }

    @RequestMapping("order/user/confirm")
    @ResponseBody
    public MessageInfo orderConfirm(HttpSession session,int orderid){
        User user = (User) session.getAttribute("user");


        if(user==null){
            return new MessageInfo(false,"登录信息获取失败,请重新登录") ;
        }
        return orderService.orderConfirmByUser(user.getId(),orderid);
    }

    @RequestMapping("order/vuene/add")
    @ResponseBody
    public MessageInfo addOrderByVuene(HttpSession session,int performid,int userid,String seats,double discount) {
        Vuene user = (Vuene) session.getAttribute("vuene");
        if (user == null){
            return new MessageInfo(false,"登录信息获取失败,请重新登录");
        }
        return orderService.addOrderVuene(user,performid,userid,seats,discount);
    }


    @RequestMapping("order/vuene/list")
    @ResponseBody
    public MessageInfo getVueneOrders(HttpSession session) {
        Vuene user = (Vuene) session.getAttribute("vuene");
        if (user == null){
            return new MessageInfo(false,"登录信息获取失败,请重新登录");
        }
        return orderService.getVueneOrders(user.getVueneid());
    }


    @RequestMapping("order/vuene/cancle")
    @ResponseBody
    public MessageInfo orderCancleByVuene(HttpSession session,String[] orderid){
        Vuene user=(Vuene) session.getAttribute("vuene");
        int id=0;
        boolean alldone=true;

        if(user==null){
                return new MessageInfo(false,"登录信息获取失败,请重新登录");
        }

        for (String tem:orderid){
            if (!orderService.orderCancleByVuene(id, Integer.parseInt(tem)).isResult()) {
                alldone=false;
            }
        }
        if(!alldone){
            return new MessageInfo(true,"部分订单取消失败取消,请确认所选订单是否可取消");
        }


        return new MessageInfo(true,"订单已经全部取消");

    }

    @RequestMapping("order/vuene/confirm")
    @ResponseBody
    public MessageInfo orderVueneConfirm(HttpSession session,String[] orderid){

        Vuene vuene = (Vuene) session.getAttribute("vuene");
        boolean alldone=true;
        if (vuene==null){
            return new MessageInfo(false,"登录信息获取失败,请重新登录");
        }
        for (String tem:orderid){
            if (!orderService.orderConfirmByVuene(vuene.getVueneid(), Integer.parseInt(tem)).isResult()) {
                alldone=false;
            }
        }
        if(!alldone){
            return new MessageInfo(true,"部分订单确认失败,请确认该订单状态是否可以确认");
        }


        return new MessageInfo(true,"订单已经全部确认");

    }

    @RequestMapping("order/vuene/getCountState")
    @ResponseBody
    public MessageInfo getCountStateByVuene(HttpSession session){
        Vuene vuene = (Vuene) session.getAttribute("vuene");
        boolean alldone=true;
        if (vuene==null){
            return new MessageInfo(false,"登录信息获取失败,请重新登录");
        }
        return orderService.getCountOrderState(DateUtil.getAfterLastMonthdate(1),DateUtil.getBeforeNextMonthdate(1),vuene.getVueneid());

    }
    @RequestMapping("order/vuene/getCountPay")
    @ResponseBody
    public MessageInfo getCountPayByVuene(HttpSession session){
        Vuene vuene = (Vuene) session.getAttribute("vuene");
        boolean alldone=true;
        if (vuene==null){
            return new MessageInfo(false,"登录信息获取失败,请重新登录");
        }
        return orderService.getCountOrderPay(DateUtil.getAfterLastMonthdate(1),DateUtil.getBeforeNextMonthdate(1),vuene.getVueneid());

    }



    @RequestMapping("order/manager/list")
    @ResponseBody
    public Object getManagerOrders(HttpSession session){
        Manager manager = (Manager) session.getAttribute("manager");
        if (manager == null){
            return new MessageInfo(false,"登录信息获取失败,请重新登录");
        }
        MessageInfo messageInfo=orderService.getAllOrders();
        return new MessageInfo(messageInfo.isResult(),translateIntoVo((List<Orders>)messageInfo.getObject()),messageInfo.getReason());
    }
    private List<OrderVo> translateIntoVo(List<Orders> list ){
        List<OrderVo> result = new ArrayList<OrderVo>();
        for (Orders o : list) {
            result.add(new OrderVo(o));
        }
        return result;

    }

    @RequestMapping("order/manager/cancle")
    @ResponseBody
    public  MessageInfo orderManagerCancle(HttpSession session,String[] orderid){
        Manager manager = (Manager) session.getAttribute("manager");
        boolean alldone=true;
        if (manager == null){
            return new MessageInfo(false,"登录信息获取失败,请重新登录");
        }
        for (String tem:orderid){
            if (!orderService.orderCancleByManager(Integer.parseInt(tem)).isResult()) {
                alldone=false;
            }
        }
        if(!alldone){
            return new MessageInfo(true,"部分订单取消失败,请确认该订单状态是否可以取消");
        }


        return new MessageInfo(true,"订单已经全部取消");
    }


}
