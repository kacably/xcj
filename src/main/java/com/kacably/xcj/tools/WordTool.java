package com.kacably.xcj.tools;


import com.kacably.xcj.bean.user.UserBaseInfoBean;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WordTool {
    /**
     * 生成 word 文件
     *
     * @param dataMap 待填充数据
     * @param templateName 模板文件名称
     * @param filePath 模板文件路径
     * @param fileName 生成的 word 文件名称
     * @param response 响应流
     */
    public static void createWord(Map dataMap, String templateName, String filePath, String fileName, HttpServletResponse response,String type){
        // 创建配置实例
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
        // 设置编码
        configuration.setDefaultEncoding(StandardCharsets.UTF_8.name());
        // ftl模板文件
        configuration.setClassForTemplateLoading(WordTool.class, filePath);
        Writer out = null;
        try {
            // 获取模板
            Template template = configuration.getTemplate(templateName);
            if("1".equals(type)){
                response.setHeader("Content-disposition",
                        "attachment;filename=" + URLEncoder.encode(fileName + ".doc", StandardCharsets.UTF_8.name()));
                // 定义输出类型
                response.setContentType("application/msword");
                out = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
            }else if ("2".equals(type)){
                out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("/Users/kacably/Desktop/工作/测试/" + fileName + ".doc"))));
            }
            // 生成文件
            template.process(dataMap, out);

            out.flush();
            out.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String imageToBase64(String path) {
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            data = new byte[in.available()];
            //注：FileInputStream.available()方法可以从输入流中阻断由下一个方法调用这个输入流中读取的剩余字节数
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Base64.Encoder encoder = Base64.getEncoder();
        return new String(encoder.encode(data));

    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Map map = new HashMap();

        UserBaseInfoBean userBaseInfoBean = new UserBaseInfoBean();
        userBaseInfoBean.setRealname("郭建飞");
        userBaseInfoBean.setSid("888666");
        userBaseInfoBean.setTel("123456");
        userBaseInfoBean.setGoodlike("是");
        userBaseInfoBean.setMoney("en");
        userBaseInfoBean.setYes("yes");
        userBaseInfoBean.setHeight("180");
        userBaseInfoBean.setXveli("本科");
        String imagePath = "/Users/kacably/Downloads/b28931db7374f60b2434e76b300ff63e.jpg";
        String s = imageToBase64(imagePath);
        userBaseInfoBean.setIamgeBase64(s);
        userBaseInfoBean.setBirth(new Date());

        map.put("userInfo",userBaseInfoBean);
        UserBaseInfoBean userBaseInfoBean1 = new UserBaseInfoBean();
        userBaseInfoBean1.setRealname("甘青");
        userBaseInfoBean1.setId(888888);
        userBaseInfoBean1.setTel("123456");

        List list = new ArrayList();
        UserBaseInfoBean userBaseInfoBean2 = new UserBaseInfoBean();
        userBaseInfoBean2.setRealname("死信");
        userBaseInfoBean2.setId(666666);
        userBaseInfoBean2.setTel("qwerty");
        list.add(userBaseInfoBean1);
        list.add(userBaseInfoBean2);

        map.put("members",list);
        map.put("nian",DateTool.getYear(new Date()));
        map.put("yue",DateTool.getMonth(new Date()));
        map.put("ri",DateTool.getDay(new Date()));
        WordTool.createWord(map, "hold.ftl", "/template/imageword", "holddto", null,"2");
        System.out.println(System.currentTimeMillis()-start);
    }
}
