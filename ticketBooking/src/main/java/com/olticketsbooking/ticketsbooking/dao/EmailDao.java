package com.olticketsbooking.ticketsbooking.dao;

import com.olticketsbooking.ticketsbooking.config.MessageInfo;
import com.olticketsbooking.ticketsbooking.model.EmailActivation;
import com.olticketsbooking.ticketsbooking.vo.EmailActivationVo;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Repository
public class EmailDao {
    @Resource
    BaseDao baseDao;

    public MessageInfo addEmailActivation(EmailActivation emailActivation) {
        return baseDao.save(emailActivation);
    }

    public MessageInfo update(EmailActivation emailActivation) {
        return baseDao.update(emailActivation);
    }

    public MessageInfo save(EmailActivation emailActivation) {
        return baseDao.save(emailActivation);
    }


    public MessageInfo findEmailActivationById(int id) {
        return baseDao.findById(EmailActivation.class, id);
    }

    public MessageInfo findEmailActivationByEmail(String email) {
        return baseDao.findByPropertySingle(EmailActivation.class,"email",email);
    }
    public MessageInfo findEmailActivationByUserid(int userid) {
        return baseDao.findByPropertySingle(EmailActivation.class,"userid",userid);
    }
    public MessageInfo findEmailActivationByToken(String token) {
        return baseDao.findByPropertySingle(EmailActivation.class,"token",token);
    }


    public MessageInfo deleteEmailActivation(EmailActivation emailActivation) {
        return baseDao.delete(emailActivation);
    }


    public MessageInfo findAllEmailActivation() {
        return baseDao.getAll(EmailActivation.class);
    }


    private static final String MAIL_SMTP_HOST = "smtp.qq.com";
    private static final Integer MAIL_SMTP_PORT = 587;
    private static final Boolean MAIL_SMTP_AUTH = true;
    private static final String MAIL_SMTP_USER = "286674013@qq.com";
    //    private static final String MAIL_SMTP_PASSWORD = "nbrlqholkpkgbjig";
    private static final String MAIL_SMTP_PASSWORD = "blpbtehectexbifc";

    private static Properties props = new Properties();

    static {
        props.put("mail.smtp.host", MAIL_SMTP_HOST);
        props.put("mail.smtp.auth", MAIL_SMTP_AUTH);
        props.put("mail.smtp.user", MAIL_SMTP_USER);
        props.put("mail.smtp.password", MAIL_SMTP_PASSWORD);
        props.put("mail.smtp.starttls.enable", true);
    }


    /**
     * 发送邮件
     */
    public static void send(String to, String title, String content) {
        try {
            javax.mail.Session session = javax.mail.Session.getInstance(props);//创建邮件会话
            MimeMessage message = new MimeMessage(session);//由邮件会话新建一个消息对象

            message.setFrom(new InternetAddress(MAIL_SMTP_USER));//设置发件人的地址
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));//设置收件人,并设置其接收类型为TO
            //设置信件内容
            //message.setText(mailContent); //发送 纯文本 邮件 TODO
            message.setSubject(title);//设置标题
            message.setContent(content, "text/html;charset=gbk"); //发送HTML邮件，内容样式比较丰富
            message.setSentDate(new Date());//设置发信时间
            message.saveChanges();//存储邮件信息

            //发送邮件
            Transport transport = session.getTransport("smtp");
            transport.connect(MAIL_SMTP_USER, MAIL_SMTP_PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());//发送邮件,其中第二个参数是所有已设好的收件人地址
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
