package com.kacably.xcj.mapper.xcj;


import com.kacably.xcj.bean.user.UserVerifyBean;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserLoginMapper {


    @Insert("insert into userlogin(username, password, uniqueid,createtime,lastupdatetime) values(#{username}, #{password}, #{uniqueId}), sysdate, sysdate")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    int saveVerify(UserVerifyBean userVerifyBean);

    @Select({"<script>",
            "select count(*) from userlogin",
            "where 1=1",
            "<if test = 'username != null'>",
            "AND username = #{username}",
            "</if>",
            "</script>"})
    int findByUserName(String username);


    @Select("select count(*) from userlogin where username = #{username}")
    int checkUsername(String username);

    @Select("select id,available from userlogin where username = #{username} and password = #{password}")
    UserVerifyBean toLogin(UserVerifyBean userVerifyBean);

    @Select({"<script>",
            "select id,createtime,registerfordays from userlogin ",
            "where 1=1",
            "<when test =' getCount != null and offsetCount != null '>",
            " limit #{getCount} offset #{offsetCount} ",
            "</when>",
            "</script>"})
    List<UserVerifyBean> getAccountInfos(Map<String, Integer> map);

    @Update("update userlogin set registerfordays = #{registerfordays} where id = #{id}")
    int updateAccountInfo(UserVerifyBean userVerifyBean);


}
