package com.kacably.xcj.bean.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserVerifyBean {
    //主键
    private int id;
    //唯一id
    private String uniqueId;
    //用户名
    private String username;
    //加密密码
    private String password;
    //是否可用（激活）
    private String available;
    //创建时间
    private Date createTime;
    //最后一次更新时间
    private Date lastUpdateTime;
    //是否已经登录
    private String haveLogined;
    //注册时长
    private String registerfordays;
}
