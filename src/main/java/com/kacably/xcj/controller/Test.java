package com.kacably.xcj.controller;


import com.kacably.xcj.bean.counttable.Tuser;
import com.kacably.xcj.learn.Test3;
import com.kacably.xcj.mapper.xcj.TuserMapper;
import com.kacably.xcj.tools.WordTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
public class Test {

    @Autowired
    private TuserMapper tuserMapper;

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("many")
    public void test(){
        for (int i = 0; i < 8 ; i++) {
            long start = System.currentTimeMillis();
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Tuser tuser = new Tuser();
                    tuser.setCreatetime(new Date());
                    tuser.setUsername("user");
                    tuser.setUniqueid("user");
                    tuser.setPassword("user");
                    tuser.setHavelogined("1");
                    tuser.setAvailable("0");
                    for (int j = 0; j < 66600; j++) {
                        tuserMapper.insert(tuser);
                    }

                }
            });
            thread.start();
            System.out.println("耗时:" + (System.currentTimeMillis() - start));
        }
    }

    @RequestMapping("down")
    public void down(HttpServletResponse response) {
        Map map = new HashMap();
        map.put("mytitle", "张三");
        map.put("nian", "2000");
        map.put("yue", "1");
        map.put("ri", "1");
        map.put("shenheren", "我啊");
        WordTool.createWord(map, "xintest.ftl", "/static", "测试文件", response,"1");
    }
    @RequestMapping("testT")
    public void Test(){
        Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
        for (Map.Entry<Thread, StackTraceElement[]> stack:allStackTraces.entrySet()){
            Thread key = stack.getKey();
            if (key.equals(Thread.currentThread())) continue;
            StackTraceElement[] value = stack.getValue();
            System.out.println("当前线层：" + key.getName());
            for (StackTraceElement val:value){
                System.out.println(val);
            }
        }
    }

    @RequestMapping("testOauth")
    @ResponseBody
    public Map TtestOauthest() {
        String url = "http://localhost:8899/getUser?access_token=1c7d8bd9-331b-4977-a7a8-38a119a43046";
        Map forObject = restTemplate.getForObject(url, Map.class);
        return forObject;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            Random random = new Random();
            System.out.println(random.nextInt(2));
        }
        Test3 t = new Test3();
    }
}
