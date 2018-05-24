package com.olticketsbooking.ticketsbooking.dao;

import com.olticketsbooking.ticketsbooking.config.MessageInfo;
import com.olticketsbooking.ticketsbooking.model.BankCard;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;


@Repository
public class BankCardDao {

    @Resource
    BaseDao baseDao;

    public MessageInfo findCard(String cardid){
        return baseDao.findByPropertySingle(BankCard.class,"cardid",cardid);
    }

    public MessageInfo update(BankCard bankCard){
        return baseDao.update(bankCard);
    }
}
