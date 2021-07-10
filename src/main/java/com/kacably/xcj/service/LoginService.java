package com.kacably.xcj.service;


import com.kacably.xcj.bean.Page;
import com.kacably.xcj.bean.user.UserBaseInfoBean;
import com.kacably.xcj.bean.user.UserVerifyBean;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

public interface LoginService {

    int save(UserVerifyBean userVerifyBean,UserBaseInfoBean userBaseInfoBean);


    int findByUserName(String username);

    UserBaseInfoBean findUserInfoByUserName(String username);

    int update(UserVerifyBean userVerifyBean);

    List<UserBaseInfoBean> getList();

    int deleteByUsername(String username);

    int checkUsername(String username);

    Map<String, String> toLogin(UserVerifyBean userVerifyBean);

    void updateCount(List<UserVerifyBean> list);

    UserVerifyBean getAccountUser(String defaulsthash);

    Page<UserBaseInfoBean> getListRowBounds(RowBounds rowBounds);

}
