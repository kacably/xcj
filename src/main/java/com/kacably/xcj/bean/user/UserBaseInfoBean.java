package com.kacably.xcj.bean.user;

import com.fasterxml.jackson.databind.ser.std.SerializableSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserBaseInfoBean implements Serializable,Cloneable {

    //主键
    private int id;
    //姓名
    private String realname;
    //唯一id
    private String uniqueId;
    //电话
    private String tel;
    //邮箱
    private String email;
    //性别
    private String sex;
    //性别描述
    private String sexDesc;
    //生日
    private Date birth;
    //身高
    private String height;
    //yes
    private String yes;
    //帅
    private String goodlike;
    //钱
    private String money;
    //头像
    private String iamgeBase64;
    //学历
    private String xveli;
    //学号
    private String sid;


    @Override
    protected Object clone() throws CloneNotSupportedException {
         return super.clone();
    }
}
