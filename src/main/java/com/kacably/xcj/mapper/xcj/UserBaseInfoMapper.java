package com.kacably.xcj.mapper.xcj;

import com.kacably.xcj.bean.Page;
import com.kacably.xcj.bean.user.UserBaseInfoBean;
import com.kacably.xcj.bean.user.UserVerifyBean;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.ui.Model;

import java.util.List;

@Mapper
public interface UserBaseInfoMapper {


    @Insert("insert into userbaseinfo(uniqueid, realname, sex, sexdesc, email, birth, tel) " +
            "values(#{uniqueId}, #{realname}, #{sex}, #{sexDesc}, #{email}, #{birth}, #{tel}) ")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    int saveBaseInfo(UserBaseInfoBean userBaseInfoBean);



    @Update("update userlogin set password = #{password} where username = #{username}")
    int update(UserVerifyBean userVerifyBean);


    @Select("select id,realname,uniqueid,tel,email,sex,sexdesc as sexDesc,birth from userbaseinfo ")
    List<UserBaseInfoBean> getList();

    int deleteByUsername(String username);

    @Select("select id,realname,uniqueid,tel,email,sex,sexdesc as sexDesc,birth from userbaseinfo where realname=#{username} limit 1")
    UserBaseInfoBean findUserInfoByUserName(String username);

    @Select("select id,realname,uniqueid,tel,email,sex,sexdesc as sexDesc,birth from userbaseinfo ")
    Page<UserBaseInfoBean> getListRowBounds(RowBounds rowBounds);
}
