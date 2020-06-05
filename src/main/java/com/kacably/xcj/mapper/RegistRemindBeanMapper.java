package com.kacably.xcj.mapper;


import com.kacably.xcj.bean.message.RegistRemindBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RegistRemindBeanMapper {

    //检查是否消息收到
    @Select("select count(*) from remind where messageid = #{messageId}")
    int checkMessage(String messageId);
}
