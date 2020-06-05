package com.kacably.xcj.bean.message;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RegistRemindBean implements Serializable {

    //主键
    private int id;
    //消息唯一id
    private String messageId;
    //接收人
    private String[] destinations;
    //接收人拼接
    private String destinationsStr;
    //发送人
    private String sender;
    //内容
    private String content;
    //发送时间
    private Date createTime;
    //最后一次更新时间
    private Date lastUpdateTime;
    //标题
    private String subject;
    //抄送人
    private String[] ccPeoples;
    //抄送人拼接
    private String ccPeoplesStr;
    //密送人
    private String[] bccPeoples;
    //密送人拼接
    private String bccPeoplesStr;
    //是否HTML(true 1 or false 0 html)
    private String TOFHtml;
    //附件路径
    private String[] attachmentsPath;
    //附件路径拼接
    private String attachmentsPathStr;

}
