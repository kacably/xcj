package com.kacably.xcj.mapper;

import com.kacably.xcj.bean.user.UserBaseInfoBean;
import com.kacably.xcj.bean.user.UserVerifyBean;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;


public interface LoginMapper {

    @Insert("insert into userlogin(username, password, uniqueid) values(#{username}, #{password}, #{uniqueId})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    int saveVerify(UserVerifyBean userVerifyBean);

    @Insert("insert into userbaseinfo(uniqueid, realname, sex, sexdesc, email, birth, tel) " +
            "values(#{uniqueId}, #{realname}, #{sex}, #{sexDesc}, #{email}, #{birth}, #{tel}) ")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    int saveBaseInfo(UserBaseInfoBean userBaseInfoBean);

    @Select({"<script>",
            "select count(*) from userlogin",
            "where 1=1",
            "<if test = 'username != null'>",
            "AND username = #{username}",
            "</if>",
            "</script>"})
    int findByUserName(String username);

    @Update("update userlogin set password = #{password} where username = #{username}")
    int update(UserVerifyBean userVerifyBean);


    @Select("select id,realname,uniqueid,tel,email,sex,sexdesc as sexDesc,birth from userbaseinfo")
    List<UserBaseInfoBean> getList();

    int deleteByUsername(String username);

    @Select("select count(*) from userlogin where username = #{username}")
    int checkUsername(String username);

    @Select("select available from userlogin where username = #{username} and password = #{password}")
    UserVerifyBean toLogin(UserVerifyBean userVerifyBean);
}
