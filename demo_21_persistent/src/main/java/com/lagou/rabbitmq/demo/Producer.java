package com.lagou.rabbitmq.demo;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @Author: Jaa
 * @Description:
 * @Date 2024/6/13
 */
public class Producer {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://root:123456@47.103.155.255:5672/%2f");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // durable:true表示是持久化消息队列
        channel.queueDeclare("queue.persistent", true, false, false, null);
        channel.exchangeDeclare("ex.persistent", "direct", true, false, null);
        // 持久化的交换器
        channel.queueBind("queue.persistent", "ex.persistent", "key.persistent");

        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .deliveryMode(2) // 表示是持久化消息
                .build();

        channel.basicPublish(
                "ex.persistent",
                "key.persistent",
                properties, // 设置消息的属性，此时消息是持久化消息
                "Hello world".getBytes());

        channel.close();
        connection.close();
    }

}
