package com.kacably.xcj.bean.user;

import lombok.Data;

@Data
public class UserModel {
    //用户登录信息
    private UserVerifyBean userVerifyBean;
    //用户基本信息
    private UserBaseInfoBean userBaseInfoBean;
}
