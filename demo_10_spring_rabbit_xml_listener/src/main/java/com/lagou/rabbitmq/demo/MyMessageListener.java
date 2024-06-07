package com.lagou.rabbitmq.demo;


import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

/**
 * @Author: Jaa
 * @Description:
 * @Date 2024/6/7
 */
public class MyMessageListener implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        System.out.println(new String(message.getBody(), message.getMessageProperties().getContentEncoding()));
    }
}

// public class MyMessageListener implements MessageListener {
//
//     @Override
//     public void onMessage(Message message) {
//
//     }
// }
