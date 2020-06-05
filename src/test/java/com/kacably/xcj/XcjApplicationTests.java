package com.kacably.xcj;

import com.kacably.xcj.bean.user.UserBaseInfoBean;
import com.kacably.xcj.bean.user.UserVerifyBean;
import com.kacably.xcj.redis.RedisTool;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
class XcjApplicationTests {
    @Autowired
    DataSource dataSource;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RedisTool redisTool;
    @Test
    void contextLoads() {
    }

    @Test
    void datasource() throws SQLException {
        System.out.println(dataSource.getClass());
        Connection connection = dataSource.getConnection();

        System.out.println(connection);
        connection.close();
    }

    @Test
    void email() throws SQLException {
      /*  RabbitSender rabbitSender = new RabbitSender();
        Map map = new HashMap();
        map.put("a1","hhh");
        rabbitSender.send("你好啊 hello", map, "email_remind","email.login");
        */
        rabbitTemplate.convertAndSend("email","login.test","ndd");
    }

    @Test
    void setKey(){
        redisTool.set("mfl","kacably");
        UserBaseInfoBean userBaseInfoBean = new UserBaseInfoBean();
        userBaseInfoBean.setSexDesc("男");
        userBaseInfoBean.setEmail("23213");
        userBaseInfoBean.setRealname("dfsf呼呼");
        redisTool.set("mflmac",userBaseInfoBean,60);
    }

    @Test
    void getKey(){
        long mflmac = redisTool.getExpire("email.remind");
        System.out.println(mflmac);
        UserBaseInfoBean userBaseInfoBean = (UserBaseInfoBean)redisTool.get("email.remind");
        System.out.println(userBaseInfoBean);
        long mfl = redisTool.getExpire("mfl");
        System.out.println(mfl);
        Object mfl1 = redisTool.get("mfl");
        System.out.println(mfl1);
    }

    @Test
    void noS(){
        UserVerifyBean userVerifyBean = new UserVerifyBean();
        userVerifyBean.setAvailable("21");
        userVerifyBean.setId(112333);
        redisTemplate.opsForValue().set("hhh",userVerifyBean);
        UserVerifyBean userBaseInfoBean = (UserVerifyBean)redisTool.get("hhh");
        System.out.println(userBaseInfoBean);

    }


    @Test
    void getKeys(){
        //redisTemplate.opsForList().remove("email.remind",0,"dl9j92lbbvr4nlff");
        //redisTemplate.opsForList().leftPop("email.remind");
        //Long size = redisTemplate.opsForList().size("email.remind");
        Set keys = redisTemplate.keys("*");
        for (Object key:keys
             ) {
            System.out.println(key);
        }
    }

    @Test
    void getVersion(){
        System.out.println(SpringBootVersion.getVersion());
    }

}
