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
import org.w3c.dom.ls.LSOutput;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.FutureTask;
import java.util.function.Consumer;
import java.util.stream.Collector;
import java.util.stream.Stream;

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
        rabbitTemplate.convertAndSend("email", "login.test", "ndd");
    }

    @Test
    void setKey() {
        redisTool.set("mfl", "kacably");
        UserBaseInfoBean userBaseInfoBean = new UserBaseInfoBean();
        userBaseInfoBean.setSexDesc("男");
        userBaseInfoBean.setEmail("23213");
        userBaseInfoBean.setRealname("dfsf呼呼");
        redisTool.set("mflmac", userBaseInfoBean, 60);
    }

    @Test
    void getKey() {
        long mflmac = redisTool.getExpire("email.remind");
        System.out.println(mflmac);
        UserBaseInfoBean userBaseInfoBean = (UserBaseInfoBean) redisTool.get("email.remind");
        System.out.println(userBaseInfoBean);
        long mfl = redisTool.getExpire("mfl");
        System.out.println(mfl);
        Object mfl1 = redisTool.get("mfl");
        System.out.println(mfl1);
    }

    @Test
    void noS() {
        UserVerifyBean userVerifyBean = new UserVerifyBean();
        userVerifyBean.setAvailable("21");
        userVerifyBean.setId(112333);
        redisTemplate.opsForValue().set("hhh", userVerifyBean);
        UserVerifyBean userBaseInfoBean = (UserVerifyBean) redisTool.get("hhh");
        System.out.println(userBaseInfoBean);

    }


    @Test
    void getKeys() {
        //redisTemplate.opsForList().remove("email.remind",0,"dl9j92lbbvr4nlff");
        //redisTemplate.opsForList().leftPop("email.remind");
        //Long size = redisTemplate.opsForList().size("email.remind");
        Set keys = redisTemplate.keys("*");
        for (Object key : keys
        ) {
            System.out.println(key);
        }
    }

    @Test
    void getVersion() {
        System.out.println(SpringBootVersion.getVersion());
    }

    @Test
    void ab() {
        String[] s = new String[20];
        for (int i = 0; i < s.length; i++) {
            System.out.println(s[i]);
        }

        int i = 1000;
        Integer j = new Integer(1000);
        System.out.println(i == j);
    }


    @Test
    public void myhashset() {
        HashSet hashSet = new HashSet();
        ArrayList list = new ArrayList();
        list.add("1");
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("1");


        hashSet.addAll(list);
        Iterator iterator = hashSet.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        hashSet.stream().forEach(System.out::println);
    }

    @Test
    public void abcbc() {
        Map<String, String> test = new LinkedHashMap<String, String>(9);

        test.put("化学", "93");
        test.put("数学", "98");
        test.put("生物", "92");
        test.put("英语", "97");
        test.put("物理", "94");
        test.put("历史", "96");
        test.put("语文", "99");
        test.put("地理", "95");

        for (Map.Entry entry : test.entrySet()) {
            System.out.println(entry.getKey().toString() + ":" + entry.getValue().toString());
        }
    }


    @Test
    public void abcabc() {
        Map<String, String> test = new LinkedHashMap<String, String>(9);

        test.put("化学", "93");
        test.put("数学", "98");
        test.put("生物", "92");
        test.put("英语", "97");
        test.put("物理", "94");
        test.put("历史", "96");
        test.put("语文", "99");
        test.put("地理", "95");

        for (Map.Entry entry : test.entrySet()) {
            System.out.println(entry.getKey().toString() + ":" + entry.getValue().toString());
        }
    }

    volatile UserBaseInfoBean userBaseInfoBean = new UserBaseInfoBean();

    @Test
    public void abda() throws ParseException {
        //UserBaseInfoBean userBaseInfoBean = new UserBaseInfoBean();
        userBaseInfoBean.setRealname("郭建飞");
        userBaseInfoBean.setEmail("123@163.com");
        System.out.println(new Date());
        userBaseInfoBean.setBirth(new Date());

        List list = new ArrayList();
        list.add(userBaseInfoBean);
        userBaseInfoBean.setSexDesc("男") ;
        list.add(userBaseInfoBean);

        redisTemplate.opsForList().leftPush("onlyone", userBaseInfoBean);
        redisTemplate.opsForValue().set("onlytwo",list);

        System.out.println(redisTemplate.opsForList().size("onlyone"));
        List onlyone = redisTemplate.opsForList().range("onlyone", 0, 1);
        System.out.println(onlyone.size());
        System.out.println(((UserBaseInfoBean) onlyone.get(0)));
        List reList = (List) redisTemplate.opsForValue().get("onlytwo");


        System.err.println(((UserBaseInfoBean) reList.get(0)));
        System.err.println(((UserBaseInfoBean) reList.get(1)));



    }


    @Test
    public void testStream(){
        List<UserBaseInfoBean> list = new ArrayList<>();

        UserBaseInfoBean userBaseInfoBean = new UserBaseInfoBean();
        userBaseInfoBean.setId(1);
        userBaseInfoBean.setRealname("zhangs");
        list.add(userBaseInfoBean);

        UserBaseInfoBean userBaseInfoBean1 = new UserBaseInfoBean();
        userBaseInfoBean1.setId(2);
        userBaseInfoBean1.setRealname("lis");
        list.add(userBaseInfoBean1);

        UserBaseInfoBean userBaseInfoBean2 = new UserBaseInfoBean();
        userBaseInfoBean2.setId(3);
        userBaseInfoBean2.setRealname("zhangs");
        list.add(userBaseInfoBean2);

        UserBaseInfoBean userBaseInfoBean3 = new UserBaseInfoBean();
        userBaseInfoBean3.setId(3);
        userBaseInfoBean3.setRealname("wangw");
        list.add(userBaseInfoBean3);

        Stream<UserBaseInfoBean> stream = list.stream();
        //stream.filter(a->a.getId() >= 3).forEach(a-> System.out.println(a.toString()));
        stream.sorted(Comparator.comparing(UserBaseInfoBean::getRealname).
                thenComparing(UserBaseInfoBean::getId, Comparator.reverseOrder())).
                forEach(a-> System.out.println(a));
    }

    @Test
    public void testConsumer(){
        Consumer<String> consumer = m-> System.out.println(m+"哈哈哈");

        Comparator<Integer> comparator = (x,y)-> x.compareTo(y);
        happy(10000.0,(m)-> System.out.println(m+"哈哈哈"));
    }
    public void happy(Double money, Consumer<Double> con){
        con.accept(money);
    }


}
