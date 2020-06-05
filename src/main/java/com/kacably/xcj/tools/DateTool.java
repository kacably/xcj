package com.kacably.xcj.tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class DateTool {


    public static String dataToString(String format, Date date){
        //格式定义
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        //时间转换
        String reDateString = simpleDateFormat.format(date);
        //返回string
        return reDateString;
    }
}
