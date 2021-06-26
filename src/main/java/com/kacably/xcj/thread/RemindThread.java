package com.kacably.xcj.thread;

import com.kacably.xcj.bean.message.RegistRemindBean;
import org.springframework.data.redis.core.RedisTemplate;

public class RemindThread implements Runnable {

    public static final String REMINDKEY = "email.remind";

    private RedisTemplate redisTemplate;

    private Object message;

    public RemindThread(RedisTemplate redisTemplate, RegistRemindBean registRemindBean){
        super();
        this.redisTemplate = redisTemplate;
        this.message = registRemindBean;
    }

    @Override
    public void run() {
        redisTemplate.opsForList().rightPush(REMINDKEY,message);
        System.out.println("发送缓存线程" + Thread.currentThread().getName() + "时间：" + System.currentTimeMillis());
    }
}
