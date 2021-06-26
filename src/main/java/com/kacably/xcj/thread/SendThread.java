package com.kacably.xcj.thread;

import com.kacably.xcj.bean.message.RegistRemindBean;
import com.kacably.xcj.tools.RabbitSender;
import lombok.SneakyThrows;

import java.util.Map;

public class SendThread extends Thread {

    private Object message;

    private Map properties;

    private RabbitSender rabbitSender;

    public static final String REMINDKEY = "login.remind";

    public static final String EXCHANGE = "email";

    public SendThread(RegistRemindBean registRemindBean, Map properties,RabbitSender rabbitSender){
        super();
        this.message = registRemindBean;
        this.properties = properties;
        this.rabbitSender = rabbitSender;

    }

    @SneakyThrows
    @Override
    public void run() {
        super.run();
        rabbitSender.send(message,properties,EXCHANGE,REMINDKEY);
        System.out.println("发送消息线程" + Thread.currentThread().getName() + "时间：" + System.currentTimeMillis());
    }
}
