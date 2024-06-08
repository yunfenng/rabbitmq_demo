package com.lagou.rabbitmq.demo;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * @Author: Jaa
 * @Description:
 * @Date 2024/6/8
 */
@Component
public class MyMessageListener {

    /**
     * com.rabbitmq.client.Channel to get access to the Channel 对象
     * org.springframework.amqp.core.Message message对象，可以直接操作原生的AMQP消息
     * org.springframework.messaging.Message to use the messaging abstraction counterpart
     *
     * @Payload 注解方法参数，该参数的值就是消息体
     * @Header 注解方法参数，访问指定的消息头字段的值
     * @Headers 该注解的方法参数获取该消息的消息头的所有字段，参数类型对应Map集合
     * MessageHeaders 参数类型，访问所有消息头字段
     * MessageHeaderAccessor or AmqpMessageHeaderAccessor 访问所有消息头字段
     */
    @RabbitListener(queues = "queue.anno")
    public void whenMessageCome(Message message) throws UnsupportedEncodingException {
        System.out.println(new String(message.getBody(), message.getMessageProperties().getContentEncoding()));
    }
}
