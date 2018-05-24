package com.olticketsbooking.ticketsbooking.controller;


import com.olticketsbooking.ticketsbooking.config.MessageInfo;
import com.olticketsbooking.ticketsbooking.model.Vuene;
import com.olticketsbooking.ticketsbooking.service.PlayroomService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class PlayroomController {

    @Resource
    PlayroomService playroomservice;
    
    @RequestMapping("/vuene/addRoom")
    @ResponseBody
    public MessageInfo addRoom(HttpSession session, String playroomname, String totalseats, String description) {
        Vuene vuene=(Vuene) session.getAttribute("vuene");
        if (vuene == null){
            return new MessageInfo(false,"登录信息获取失败,请重新登录");
        }
        System.out.println(playroomname+" 1 "+totalseats+" 2 "+description);
        return playroomservice.addRoom(vuene, playroomname,Integer.parseInt(totalseats), description);


    }
    @RequestMapping("/vuene/updateRoom")
    @ResponseBody
    public MessageInfo update(HttpSession session,int playroomid, String playroomname, int totalseats, String description) {

        Vuene vuene=(Vuene) session.getAttribute("vuene");
        if (vuene == null){
            return new MessageInfo(false,"登录信息获取失败,请重新登录");
        }
        return playroomservice.update(vuene,playroomid,playroomname,totalseats,description);
    }


    @RequestMapping("/vuene/findRoomById")
    @ResponseBody
    public MessageInfo findRoomById(HttpSession session,int playroomid) {
        Vuene vuene=(Vuene) session.getAttribute("vuene");
        if (vuene == null){
            return new MessageInfo(false,"登录信息获取失败,请重新登录");
        }
        return playroomservice.findRoomById(playroomid);
    }
    @RequestMapping("/vuene/deleteRoom")
    @ResponseBody
    public MessageInfo deleteRoom(HttpSession session,int playroomid) {
        Vuene vuene=(Vuene) session.getAttribute("vuene");
        if (vuene == null){
            return new MessageInfo(false,"登录信息获取失败,请重新登录");
        }
        return playroomservice.deleteRoom(playroomid);

    }
    @RequestMapping("/vuene/setRoomDeleted")
    @ResponseBody
    public MessageInfo setRoomDeleted(HttpSession session,int playroomid) {

        Vuene vuene=(Vuene) session.getAttribute("vuene");
        if (vuene == null){
            return new MessageInfo(false,"登录信息获取失败,请重新登录");
        }
        return playroomservice.setRoomDeleted(playroomid);
    }
    @RequestMapping("/vuene/findRoomByVuene")
    @ResponseBody
    public MessageInfo findRoomByVuene(HttpSession session) {
        Vuene vuene=(Vuene) session.getAttribute("vuene");
        if (vuene == null){
            return new MessageInfo(false,"登录信息获取失败,请重新登录");
        }
        return playroomservice.findRoomByVuene(vuene.getVueneid());
    }
    

}
