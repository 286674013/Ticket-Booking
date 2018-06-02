package com.olticketsbooking.ticketsbooking.controller;


import com.olticketsbooking.ticketsbooking.config.MessageInfo;
import com.olticketsbooking.ticketsbooking.model.Vuene;
import com.olticketsbooking.ticketsbooking.service.PerformService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class PerformController {
    @Resource
    PerformService performService;


    @RequestMapping("/vuene/addPerform")
    @ResponseBody
    public MessageInfo addPerform(HttpSession session,String begintime,String endtime,
                                  double seatprice,String performtype,String performname,
                                  String performkeys,String performdescription,int playroomname){

        Vuene vuene=(Vuene) session.getAttribute("vuene");
        if (vuene == null){
            return new MessageInfo(false,"登录信息获取失败,请重新登录");
        }

        return performService.addPerform(vuene,playroomname,begintime,endtime,seatprice,performtype,performname,performkeys,performdescription);
    }
    @RequestMapping("/vuene/updatePerform")
    @ResponseBody
    public MessageInfo updatePerform(HttpSession session,int performid,String begintime,String endtime,
                                     double seatprice,String performtype,String performname,
                                     String performkeys,String performdescription,int playroomname){

        Vuene vuene=(Vuene) session.getAttribute("vuene");
        if (vuene == null){
            return new MessageInfo(false,"登录信息获取失败,请重新登录");
        }

        return performService.updatePerform(vuene,performid,playroomname,begintime,endtime,seatprice,performtype,performname,performkeys,performdescription);
    }
    @RequestMapping("/findPerformById")
    @ResponseBody
    public MessageInfo findPerformById(String performid){
        System.out.println(performid);

        return performService.findPerformById(Integer.parseInt(performid));
    }
    @RequestMapping("/vuene/findPerformByVuene")
    @ResponseBody
    public MessageInfo findPerformByVuene(HttpSession session){

        Vuene vuene=(Vuene) session.getAttribute("vuene");
        if (vuene == null){
            return new MessageInfo(false,"登录信息获取失败,请重新登录");
        }
        return performService.findPerformByVuene(vuene.getVueneid());
    }
    @RequestMapping("/findAllPerform")
    @ResponseBody
    public  MessageInfo findAllPerform(){
        return performService.findAllPerform();
    }

    @RequestMapping("/search")
    @ResponseBody
    public  MessageInfo search(String begintime,String endtime,String keywords){

        return performService.searchPerform(begintime,endtime,keywords);
    }
    
    
}
