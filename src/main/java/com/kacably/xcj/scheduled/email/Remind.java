package com.kacably.xcj.scheduled.email;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.kacably.xcj.bean.message.RegistRemindBean;
import com.kacably.xcj.mapper.xcj.RegistRemindBeanMapper;
import com.kacably.xcj.tools.RabbitSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class Remind {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RegistRemindBeanMapper registRemindBeanMapper;
    @Autowired
    private RabbitSender rabbitSender;

    /**
     * 2分钟执行一次
     * @throws JsonProcessingException
     * @throws ParseException
     * @throws InterruptedException
     */
    @Scheduled(cron = "0 19 * * * ?")
    private void checkRemind() throws JsonProcessingException, ParseException, InterruptedException {
        //获取redis锁
        Object onlyone = redisTemplate.opsForValue().get("onlyone");
        if (onlyone == null){
            log.info("上锁了");
            redisTemplate.opsForValue().setIfAbsent("onlyone", "007",1, TimeUnit.MINUTES);
        }else{
            log.info("已经有锁了");
            return;
        }

        TimeUnit.MINUTES.sleep(2);

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
                    System.out.println(messageId + "没有入库再次入库");
                    rabbitSender.send(registRemindBean, properties, "email", "login.remind");
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
            //删除元素
            redisTemplate.opsForList().remove("email.remind",1,registRemindBean);
            redisTemplate.delete("onlyone");
        }
    }
}
