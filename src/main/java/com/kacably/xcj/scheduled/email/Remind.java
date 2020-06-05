package com.kacably.xcj.scheduled.email;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.kacably.xcj.bean.message.RegistRemindBean;
import com.kacably.xcj.mapper.RegistRemindBeanMapper;
import com.kacably.xcj.tools.RabbitSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class Remind {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RegistRemindBeanMapper registRemindBeanMapper;
    @Autowired
    private RabbitSender rabbitSender;
    @Scheduled(cron = "0 0/2 * * * ?")
    private void checkRemind() throws JsonProcessingException {
        //缓存中弹出10个消息提醒对象
        ArrayList registRemindList = (ArrayList) redisTemplate.opsForList().range("email.remind",0,10);
        //null 判断 长度判断
        if (registRemindList == null || registRemindList.size() == 0) return;
        //遍历
        for (Object registRemind:registRemindList){
            //转消息提醒对象
            RegistRemindBean registRemindBean = (RegistRemindBean) registRemind;
            //如果messageId为空直接返回
            if (registRemindBean == null) return;
            String messageId = registRemindBean.getMessageId();
            if (StringUtils.isEmpty(messageId)) return;
            //检查是否入库
            int msgCount = registRemindBeanMapper.checkMessage(messageId);
            Map properties = new HashMap();
            properties.put("messageId",messageId);
            //如果没有入库再发送一次
            if (msgCount != 1){
                try {
                    rabbitSender.send(registRemindBean, properties, "email", "login.remind");
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
            //删除元素
            redisTemplate.opsForList().remove("email.remind",1,registRemindBean);
        }
    }
}
