package com.kacably.xcj.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.kacably.xcj.bean.message.RegistRemindBean;
import com.kacably.xcj.bean.user.UserBaseInfoBean;
import com.kacably.xcj.bean.user.UserModel;
import com.kacably.xcj.bean.user.UserVerifyBean;
import com.kacably.xcj.service.LoginService;
import com.kacably.xcj.tools.RabbitSender;
import com.kacably.xcj.tools.SecretTools;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("userlogin")
public class LoginController {

    @Autowired
    RabbitSender rabbitSender;

    @Autowired
    private LoginService loginService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 注册方法
     * @param userModel
     * @return 0 失败 1 成功
     */
    @PostMapping
    @ResponseBody
    public int save(@RequestBody UserModel userModel){
        //为空校验
        int save = 0;
        //获取用户注册信息对象和基本信息对象
        if (userModel == null) return save;
        UserVerifyBean userVerifyBean = userModel.getUserVerifyBean();
        UserBaseInfoBean userBaseInfoBean = userModel.getUserBaseInfoBean();
        if (userVerifyBean  == null || userBaseInfoBean == null) return save;
        return loginService.save(userVerifyBean,userBaseInfoBean);
    }

    /**
     * 用户名是否存在
     * @return 0 不存在 其他存在
     */
    @GetMapping("{username}")
    @ResponseBody
    public int checkUsername(@PathVariable("username") String username){
        int status = 0;
        status = loginService.checkUsername(username);
        return status;
    }

    /**
     * 获得用户列表
     * @return
     */
    @GetMapping
    @ResponseBody
    public Map getListGet(HttpServletRequest request){
        Map map = new HashMap();
        /*String token = "";
        //通过cookies获取
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                }
            }
        }
        if (StringUtils.isEmpty(token)) {
            token = request.getHeader("api_gateway_auth_token");
            if (StringUtils.isEmpty(token)) {
                token = request.getParameter("api_gateway_auth_token");
            }
        }
        if (token == null || !token.equals(SecretTools.encrtption("xcJ"))){
            return map;
        }*/
        String status = "0";
        List<UserBaseInfoBean> list = loginService.getList();
        if (list != null){
            status = "1";
        }
        map.put("status",status);
        map.put("data",list);
        return map;
    }

    /**
     * 获得用户列表
     * @return
     */
    @PostMapping("postTest")
    @ResponseBody
    public Map getListPost(@RequestBody RegistRemindBean registRemindBean){
        System.out.println(registRemindBean.toString());
        Map map = new HashMap();
        String status = "0";
        List<UserBaseInfoBean> list = loginService.getList();
        if (list != null){
            status = "1";
        }
        map.put("status",status);
        map.put("data",list);
        return map;
    }

    /**
     * 删除用户
     * @param username
     * @return
     */
    @DeleteMapping("{username}")
    @ResponseBody
    public int deleteByUsername(@PathVariable("username") String username){
        return loginService.deleteByUsername(username);
    }

    /**
     * 登录校验
     * @param userVerifyBean
     * @return 0 未注册，1 成功，2 未激活
     */
    @PostMapping("login")
    @ResponseBody
    public int toLogin(@RequestBody UserVerifyBean userVerifyBean){
        int ststus = 0;
        if (userVerifyBean == null) return ststus;
        ststus = loginService.toLogin(userVerifyBean);
        return ststus;
    }


    @RequestMapping("email")
    public void email() throws JsonProcessingException {
        Map map = new HashMap();
        map.put("a1","hhh");
        UserBaseInfoBean userBaseInfoBean = new UserBaseInfoBean();
        userBaseInfoBean.setUniqueId("21");
        userBaseInfoBean.setSexDesc("ds哈");
        userBaseInfoBean.setEmail("2132432");
        rabbitSender.send(userBaseInfoBean, map, "email","login.test");
    }

    @PostMapping("token")
    @ResponseBody
    public String getToken(HttpServletRequest request){
        //针对请求是form表单提交的时候post请求自己用 request getParameter取不到参数所以用 request.reader
        String line = "";
        BufferedReader reader = null;
        String applyId = null;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            reader = request.getReader();
            while ((line = reader.readLine()) != null)
            stringBuffer.append(line);
            applyId = stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //String applyId = request.getParameter("applyId");
        if (applyId != null && applyId.equals("xcJ")){
            return SecretTools.encrtption(applyId);
        }
        return null;
    }
}
