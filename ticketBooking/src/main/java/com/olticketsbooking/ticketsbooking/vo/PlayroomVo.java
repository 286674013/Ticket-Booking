package com.olticketsbooking.ticketsbooking.vo;


import com.olticketsbooking.ticketsbooking.model.Playroom;

public class PlayroomVo {
    int playroomid;
    int venueid;
    String playroomname;
    int totalseats;
    String description;//用于描述座位排布
    int state;

    public PlayroomVo() {
    }

    public PlayroomVo(Playroom playroom){
        this.playroomid=playroom.getPlayroomid();
        this.venueid=playroom.getVueneid();
        this.playroomname=playroom.getPlayroomname();
        this.totalseats=playroom.getTotalseats();
        this.description=playroom.getDescription();
        this.state=playroom.getState();

    }
    public int getPlayroomid() {
        return playroomid;
    }

    public void setPlayroomid(int playroomid) {
        this.playroomid = playroomid;
    }

    public int getVenueid() {
        return venueid;
    }

    public void setVenueid(int venueid) {
        this.venueid = venueid;
    }

    public String getPlayroomname() {
        return playroomname;
    }

    public void setPlayroomname(String playroomname) {
        this.playroomname = playroomname;
    }

    public int getTotalseats() {
        return totalseats;
    }

    public void setTotalseats(int totalseats) {
        this.totalseats = totalseats;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
