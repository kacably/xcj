package com.kacably.xcj.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kacably.xcj.bean.message.RegistRemindBean;
import com.kacably.xcj.bean.user.UserBaseInfoBean;
import com.kacably.xcj.bean.user.UserVerifyBean;
import com.kacably.xcj.mapper.LoginMapper;
import com.kacably.xcj.redis.RedisTool;
import com.kacably.xcj.service.LoginService;
import com.kacably.xcj.tools.DateTool;
import com.kacably.xcj.tools.RabbitSender;
import com.kacably.xcj.tools.SecretTools;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    private RabbitSender rabbitSender;
    @Autowired
    private RedisTool redisTool;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    @Transactional
    public int save(UserVerifyBean userVerifyBean,UserBaseInfoBean userBaseInfoBean) {
        int save= 0;
        String username = userVerifyBean.getUsername();
        String password = userVerifyBean.getPassword();
        //唯一id
        String uniqueId = UUID.randomUUID().toString().replace("-","").substring(3,16);
        if (username.isEmpty() || password.isEmpty()) return save;
        try {
            //查询用户名是否存在
            int haveRegisiter = findByUserName(userVerifyBean.getUsername());
            //为0 不存在，保存
            if(haveRegisiter == 0){
                //SHA加密
                userBaseInfoBean.setSexDesc("1".equals(userBaseInfoBean.getSex()) ? "男" : "女");
                String xcjPassword = SecretTools.encrtption(password);
                //SHA加密后的密码
                userVerifyBean.setPassword(xcjPassword);
                //唯一id
                userVerifyBean.setUniqueId(uniqueId);
                userBaseInfoBean.setUniqueId(uniqueId);
                //保存用户注册信息表，因为使用了@transactioal注解所以如果有一个失败就会全部失败
                int saveVerify = loginMapper.saveVerify(userVerifyBean);
                //保存用户基本信息表
                int savBase = loginMapper.saveBaseInfo(userBaseInfoBean);
                if (savBase == saveVerify && savBase == 1) save = 1;
                //入库之后将消息放入mq
                RegistRemindBean registRemindBean = new RegistRemindBean();
                //定义效益唯一id
                String oldMessageId = DateTool.dataToString("yyyyMMdd",new Date()) + UUID.randomUUID().toString().substring(3,8);
                String xcjMessageId = SecretTools.encrtption(oldMessageId);
                registRemindBean.setMessageId(xcjMessageId);
                String content = "你好" + userBaseInfoBean.getRealname() + ",欢迎注册xcJ，welCome u";
                registRemindBean.setContent(content);
                Map properties = new HashMap();
                properties.put("messageId",xcjMessageId);
                String[] destinations = {userBaseInfoBean.getEmail()};
                registRemindBean.setDestinations(destinations); //接收人邮箱
                registRemindBean.setSubject("注册成功");
                rabbitSender.send(registRemindBean, properties, "email", "login.remind");
                //将消息放入缓存中
                redisTemplate.opsForList().rightPush("email.remind", registRemindBean);

            }else{
                //存在
                save = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return save;
    }

    @Override
    public int findByUserName(String username) {
        return loginMapper.findByUserName(username);
    }

    @Override
    public int update(UserVerifyBean userVerifyBean) {
        return loginMapper.update(userVerifyBean);
    }

    @Override
    public List<UserBaseInfoBean> getList() {
        return loginMapper.getList();
    }

    @Override
    public int deleteByUsername(String username) {
        return loginMapper.deleteByUsername(username);
    }

    @Override
    public int checkUsername(String username) {
        return loginMapper.checkUsername(username);
    }

    /**
     * 登录校验
     * @param userVerifyBean
     * @return 0 未注册，1 成功，2 未激活
     */
    @Override
    public int toLogin(UserVerifyBean userVerifyBean) {
        int status = 0;
        if (userVerifyBean.getPassword() == null || userVerifyBean.getPassword().equals("")) return status;
        String xcjPassword = SecretTools.encrtption(userVerifyBean.getPassword());
        userVerifyBean.setPassword(xcjPassword);
        userVerifyBean.setAvailable("1");//是否激活状态
        UserVerifyBean reUserVerifyBean = loginMapper.toLogin(userVerifyBean);
        if (reUserVerifyBean == null) return status;
        if (reUserVerifyBean.getAvailable() != null && reUserVerifyBean.getAvailable().equals("1")){
            status = 1;
        }else {
            status = 2;
        }
        return status;
    }
}
