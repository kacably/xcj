package com.kacably.xcj.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.kacably.xcj.bean.message.RegistRemindBean;
import com.kacably.xcj.bean.user.UserBaseInfoBean;
import com.kacably.xcj.bean.user.UserModel;
import com.kacably.xcj.bean.user.UserVerifyBean;
import com.kacably.xcj.bean.user.XcjData;
import com.kacably.xcj.mapper.test.TestMapper;
import com.kacably.xcj.nio.properties.TestMark;
import com.kacably.xcj.service.LoginService;
import com.kacably.xcj.tools.ExcelTool;
import com.kacably.xcj.tools.RabbitSender;
import com.kacably.xcj.tools.SecretTools;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

@Controller
@CrossOrigin(allowCredentials="true")
@RequestMapping("userlogin")
public class LoginController {

    @Autowired
    RabbitSender rabbitSender;

    @Autowired
    private LoginService loginService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    TestMapper testMapper;


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
    @GetMapping("{username}/{type}")
    @ResponseBody
    public int checkUsername(@PathVariable String username,@PathVariable String type){
        int status = 0;
        System.out.println(type);
        System.out.println(username);
        status = loginService.checkUsername(username);
        return status;
    }

    /**
     * 获得用户列表
     * @return
     */
    @GetMapping
    @ResponseBody
    public Map getListGet(HttpServletRequest request, HttpSession session){
        Map map = new HashMap();
        UserVerifyBean userVerifyBean = new UserVerifyBean();
        Cookie[] cookies = request.getCookies();
        if (cookies == null){
            return null;
        }
        System.err.println(session.getId());
        for (Cookie cookie:cookies){
            String name = cookie.getName();
            System.err.println(name);
            if (name.equals("JSESSIONID")){
                userVerifyBean = loginService.getAccountUser(cookie.getValue());
                if (userVerifyBean == null) return map;

            }
        }
        System.out.println(userVerifyBean);
        System.out.println(request.getSession().getId());
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
    public XcjData toLogin(@RequestBody UserVerifyBean userVerifyBean, HttpServletRequest request, HttpServletResponse response){
        System.out.println(request.getSession().getId());
        XcjData xcjData = new XcjData();
        if (userVerifyBean == null) return xcjData;
        Map<String, String> loginCheck = loginService.toLogin(userVerifyBean);
        if (loginCheck == null){
            return xcjData;
        }
        xcjData.setResCode(Integer.parseInt(loginCheck.get("status")));
        xcjData.setResData(loginCheck.get("token"));
        return xcjData;
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



    @GetMapping("/excel/{id}")
    public void excel(HttpServletResponse httpServletResponse, @PathVariable("id") String id) throws IOException {
        List list = new ArrayList();
        list.add("123");
        list.add("extel");
        list.add("fds");
        List relist = new ArrayList();
        relist.add(list);
        ExcelTool.exportExcel(httpServletResponse,relist,"哈哈哈","测试",10);

    }
    @GetMapping("/test2")
    @ResponseBody
    public List<Map> getTest2(){
        return testMapper.getList();
    }

    /**
     * Decode the value using Base64.
     * @param base64Value the Base64 String to decode
     * @return the Base64 decoded value
     * @since 1.2.2
     */
    private String base64Decode(String base64Value) {
        try {
            byte[] decodedCookieBytes = Base64.getDecoder().decode(base64Value);
            return new String(decodedCookieBytes);
        }
        catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/test3")
    @ResponseBody
    @Transactional
    public void findByUserName(){
        UserBaseInfoBean userBaseInfoBean = loginService.findUserInfoByUserName("李小白");
        System.err.println(userBaseInfoBean.toString());
        userBaseInfoBean.setHeight("188");
        UserBaseInfoBean userBaseInfoBean2 = loginService.findUserInfoByUserName("李小白");
        System.err.println(userBaseInfoBean2.toString());

    }
}
