package com.kacably.xcj.service;


import com.kacably.xcj.bean.user.UserBaseInfoBean;
import com.kacably.xcj.bean.user.UserVerifyBean;

import java.util.List;

public interface LoginService {

    int save(UserVerifyBean userVerifyBean,UserBaseInfoBean userBaseInfoBean);


    int findByUserName(String username);

    int update(UserVerifyBean userVerifyBean);

    List<UserBaseInfoBean> getList();

    int deleteByUsername(String username);

    int checkUsername(String username);

    int toLogin(UserVerifyBean userVerifyBean);
}
