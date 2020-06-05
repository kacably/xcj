package com.kacably.xcj.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Date;

/**
 * 发送邮件工具类
 */
public class MailTool {

    @Autowired  public static JavaMailSender javaMailSender;

    public static void sendMail(){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("注册成功"); //设置主题
        simpleMailMessage.setFrom("1016161806@qq.com"); //发送者
        simpleMailMessage.setTo("15036502441@163.com","17701345418@163.com"); //接收者
        simpleMailMessage.setCc(); //抄送人
        simpleMailMessage.setBcc(); //隐秘抄送人
        simpleMailMessage.setSentDate(new Date());
        simpleMailMessage.setText("你好，你注册的xcJ已经成功，");

    }


}
