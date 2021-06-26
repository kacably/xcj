package com.kacably.xcj.service.impl;

import com.kacably.xcj.bean.message.RegistRemindBean;
import com.kacably.xcj.bean.user.UserBaseInfoBean;
import com.kacably.xcj.bean.user.UserVerifyBean;
import com.kacably.xcj.mapper.xcj.UserBaseInfoMapper;
import com.kacably.xcj.mapper.xcj.UserLoginMapper;
import com.kacably.xcj.redis.RedisTool;
import com.kacably.xcj.service.LoginService;
import com.kacably.xcj.thread.RemindThread;
import com.kacably.xcj.thread.SendThread;
import com.kacably.xcj.tools.DateTool;
import com.kacably.xcj.tools.RabbitSender;
import com.kacably.xcj.tools.SecretTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserBaseInfoMapper userBaseInfoMapper;
    @Autowired
    private UserLoginMapper userLoginMapper;
    @Autowired
    private RabbitSender rabbitSender;
    @Autowired
    private RedisTool redisTool;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public UserVerifyBean getAccountUser(String defaulsthash) {
        return (UserVerifyBean) redisTemplate.opsForValue().get(defaulsthash);
    }

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
                int saveVerify = userLoginMapper.saveVerify(userVerifyBean);
                //保存用户基本信息表
                int savBase = userBaseInfoMapper.saveBaseInfo(userBaseInfoBean);
                if (savBase == saveVerify && savBase == 1) save = 1;
                //入库之后将消息放入mq
                RegistRemindBean registRemindBean = new RegistRemindBean();
                //定义消息唯一id
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
                SendThread sendThread = new SendThread(registRemindBean,properties,rabbitSender);
                //rabbitSender.send(registRemindBean, properties, "email", "login.remind"); 修改为多线程
                sendThread.start();
                //将消息放入缓存中
                RemindThread remindThread = new RemindThread(redisTemplate,registRemindBean);
                Thread thread = new Thread(remindThread);
                //redisTemplate.opsForList().rightPush("email.remind", registRemindBean); 修改为多线程
                thread.start();

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
        return userLoginMapper.findByUserName(username);
    }

    @Override
    public UserBaseInfoBean findUserInfoByUserName(String username) {
        return userBaseInfoMapper.findUserInfoByUserName(username);
    }

    @Override
    public int update(UserVerifyBean userVerifyBean) {
        return userBaseInfoMapper.update(userVerifyBean);
    }

    @Override
    public List<UserBaseInfoBean> getList() {
        return userBaseInfoMapper.getList();
    }

    @Override
    public int deleteByUsername(String username) {
        return userBaseInfoMapper.deleteByUsername(username);
    }

    @Override
    public int checkUsername(String username) {
        return userLoginMapper.checkUsername(username);
    }

    /**
     * 登录校验
     * @param userVerifyBean
     * @return 0 未注册，1 成功，2 未激活
     */
    @Override
    public Map<String, String>  toLogin(UserVerifyBean userVerifyBean) {
        Map<String, String> reMap = new HashMap<>();
        int status = 0;
        if (userVerifyBean.getPassword() == null || userVerifyBean.getPassword().equals("")) return reMap;
        String xcjPassword = SecretTools.encrtption(userVerifyBean.getPassword());
        userVerifyBean.setPassword(xcjPassword);
        userVerifyBean.setAvailable("1");//是否激活状态
        UserVerifyBean reUserVerifyBean = userLoginMapper.toLogin(userVerifyBean);
        if (reUserVerifyBean == null) return reMap;
        if (reUserVerifyBean.getAvailable() != null && reUserVerifyBean.getAvailable().equals("1")){
            status = 1;
            String defaultHashCode = String.valueOf((reUserVerifyBean.getId() + "xcJDefaultHash").hashCode());
            redisTemplate.opsForValue().set(defaultHashCode, userVerifyBean, 30, TimeUnit.MINUTES);
            reMap.put("token", defaultHashCode);
        }else {
            status = 2;
        }
        reMap.put("status", String.valueOf(status));
        return reMap;
    }

    @Override
    public void updateCount(List<UserVerifyBean> list) {
        if (list == null || list.size() == 0) return;
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
        for (UserVerifyBean userVerifyBean:list){
            Date createTimeDB = userVerifyBean.getCreateTime();
            String format = sdf.format(createTimeDB);
            try {
                long l = DateTool.compareTo(new Date(), sdf.parse(format));
                int registerForDays = (int)(l / (1000 * 60 * 60 *24));
                //计算注册时长
                userVerifyBean.setRegisterfordays(Thread.currentThread().getName() + "_" + registerForDays);
                int i = userLoginMapper.updateAccountInfo(userVerifyBean);
                log.info(userVerifyBean.getId() + "-->更新成功:" + i);
            } catch (Exception e) {
                log.error("异常：" + e);
                //异常单独处理
                e.printStackTrace();
            }

        }
    }


}
