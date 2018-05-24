package com.olticketsbooking.ticketsbooking.controller;

import com.olticketsbooking.ticketsbooking.config.MessageInfo;
import com.olticketsbooking.ticketsbooking.model.Vuene;
import com.olticketsbooking.ticketsbooking.service.VueneService;
import com.olticketsbooking.ticketsbooking.vo.VueneVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Controller
public class VueneController {


    @Resource
    VueneService vueneService;


    @RequestMapping("/vuene/info")
    @ResponseBody
    public MessageInfo vueneInfo(HttpSession session){
      Object object =  session.getAttribute("vuene");
        if (object == null)
            return new MessageInfo(false,"请登录");
        Vuene vuene = (Vuene) object;
        return vueneService.vueneInfo(vuene.getVueneid());
    }

    @RequestMapping("/vuene/updateinfo")
    @ResponseBody
    public MessageInfo getVueneUpdateInfo(HttpSession session){
        Object object =  session.getAttribute("vuene");
        if (object == null)
            return new MessageInfo(false,"请登录");
        Vuene vuene = (Vuene) object;
        return vueneService.getVueneUpdateInfo(vuene.getVueneid());
    }

    @RequestMapping("/vuene/update/password")
    @ResponseBody
    public MessageInfo vueneUpdatePassword(HttpSession session, String oldPassword, String newPassword1, String newPassword2){
        Object object =  session.getAttribute("vuene");
        if (object == null)
            return new MessageInfo(false,"请登录");
        Vuene vuene = (Vuene) object;
        return vueneService.updatePassword(vuene.getVueneid(),oldPassword,newPassword1,newPassword2);
    }

    @RequestMapping("/vuene/update/info")
    @ResponseBody
    public MessageInfo vueneUpdateInfo(HttpSession session, String vuenename,  String vueneaddress, String vuenephone, String vuenedescription){
        Object object =  session.getAttribute("vuene");
        if (object == null)
            return new MessageInfo(false,"请登录");
        Vuene vuene = (Vuene) object;
        return vueneService.updateInfo(vuene.getVueneid(),vuenename,vueneaddress,vuenephone,vuenedescription);
    }

    @RequestMapping("/vuene/count")
    @ResponseBody
    public Object vueneOrderCount(HttpSession session){
        Object object =  session.getAttribute("manager");
        if (object == null)
            return new MessageInfo(false,"请登录");
        return vueneService.vueneOrderCount().getObject();
    }


    @RequestMapping("/vuene/getVueneApplication")
    @ResponseBody
    public MessageInfo getVueneApplication(HttpSession session){
        Object object =  session.getAttribute("manager");
        if (object == null)
            return new MessageInfo(false,"请登录");
        return vueneService.getVueneApplication();
    }


    @RequestMapping("/vuene/approve")
    @ResponseBody
    public MessageInfo getVueneApplicationApprove(HttpSession session,String[] vueneid){
        Object object =  session.getAttribute("manager");
        if (object == null)
            return new MessageInfo(false,"请登录");
//        System.out.println(vueneid);
        boolean alldone=true;
        for (String tem:vueneid){
            if (!vueneService.getVueneApplicationApprove(Integer.parseInt(tem)).isResult()) {
                alldone=false;
            }
        }
        if(!alldone){
            return new MessageInfo(true,"部分审批失败,请确认该场馆状态是否可以通过审批");
        }

        return new MessageInfo(true,"审批已经全部完成");
    }

    @RequestMapping("/vuene/disapprove")
    @ResponseBody
    public MessageInfo getVueneApplicationDisapprove(HttpSession session,String[] vueneid){
        Object object =  session.getAttribute("manager");
        if (object == null)
            return new MessageInfo(false,"请登录");
//        System.out.println(vueneid);
        boolean alldone=true;
        for (String tem:vueneid){
            if (!vueneService.getVueneApplicationDisapprove(Integer.parseInt(tem)).isResult()) {
                alldone=false;
            }
        }
        if(!alldone){
            return new MessageInfo(true,"部分审批失败,请确认该场馆状态是否可以通过审批");
        }

        return new MessageInfo(true,"审批已经全部完成");
    }

    @RequestMapping("/vuene/getAll")
    @ResponseBody
    public MessageInfo getVueneAll(HttpSession session){
        Object object =  session.getAttribute("manager");
        if (object == null)
            return new MessageInfo(false,"请登录");
        MessageInfo messageInfo=vueneService.getVueneAll();
        return new MessageInfo(messageInfo.isResult(),translateIntoVo((List<Vuene>)messageInfo.getObject()),messageInfo.getReason());
    }
    private List<VueneVo> translateIntoVo(List<Vuene> list ){
        List<VueneVo> result = new ArrayList<VueneVo>();
        for (Vuene o : list) {
            result.add(new VueneVo(o));
        }
        return result;

    }


    @RequestMapping("/vuene/financeinfo")
    @ResponseBody
    public MessageInfo vueneFinanceInfo(HttpSession session){
        Object object =  session.getAttribute("vuene");
        if (object == null)
            return new MessageInfo(false,"请登录");
        return vueneService.getVueneFinanceinfo((Vuene)object);
    }
    @RequestMapping("/vuene/getmoney")
    @ResponseBody
    public MessageInfo getmoney(HttpSession session,String bankcardid,String password,double money){
        Vuene object =  (Vuene) session.getAttribute("vuene");
        if (object == null)
            return new MessageInfo(false,"请登录");

        return vueneService.getmoney(object.getVueneid(),bankcardid,password,money);
    }


}
