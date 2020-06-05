package com.kacably.xcj.bean.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserBaseInfoBean implements Serializable {

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
}
