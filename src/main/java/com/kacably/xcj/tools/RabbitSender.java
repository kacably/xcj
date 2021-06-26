package com.kacably.xcj.tools;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kacably.xcj.redis.RedisTool;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Component
public class RabbitSender {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    RedisTool redisTool;

    @Autowired
    ObjectMapper objectMapper;


    RabbitTemplate.ConfirmCallback confirmCallback = (correlationData, ack, cause) -> {
        //消息进入broker失败
        if (!ack){
            System.out.println(cause);
        }
    };

    RabbitTemplate.ReturnCallback returnCallback = (message, replyCode, replyText, exchange, routingKey) -> {
        //启动消息失败返回，比如路由不到队列时触发回调
        System.out.println(message);
        System.err.println(replyCode); //原因code
        System.err.println(replyText); //原因描述
        System.out.println(exchange); //交换器
        System.out.println(routingKey); //路由key
        System.out.println("=========================");
    };

    /**
     *
     * @param message    消息
     * @param properties 请求头设置
     * @param exchange   交换器
     * @param routingKey 路由
     */
    public void send(Object message, Map<String,Object> properties, String exchange,String routingKey) throws JsonProcessingException {
        System.out.println((String) properties.get("messageId") + "即将发送消息");
        //设置确认机制
        rabbitTemplate.setConfirmCallback(confirmCallback);
        //设置返回机制
        rabbitTemplate.setReturnCallback(returnCallback);
        Message msg = MessageBuilder.withBody(objectMapper.writeValueAsBytes(message)).setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
        msg.getMessageProperties().setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, MessageProperties.CONTENT_TYPE_JSON);
        msg.getMessageProperties().setMessageId((String) properties.get("messageId"));
        //定义 CorrelationData 通过时间戳 + UUID 设置唯一id
        CorrelationData correlationData = new CorrelationData();
        String nowString = DateTool.dataToString("yyyyMMdd", new Date());
        String cId = nowString + UUID.randomUUID().toString().substring(6,18);
        correlationData.setId(cId);
        //发送消息
        rabbitTemplate.convertAndSend(exchange,routingKey,msg,correlationData);
    }

}
