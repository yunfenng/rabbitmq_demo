package com.lagou.rabbitmq.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @Author: Jaa
 * @Description:
 * @Date 2024/6/20
 */
public class MyProduct {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://root:123456@47.103.155.255:5672/%2f");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare("queue.tc.demo", false, false, false, null);
        channel.exchangeDeclare("ex.tc.demo", "direct", false);
        channel.queueBind("queue.tc.demo", "ex.tc.demo", "key.tc");

        for (int i = 0; i < 100; i++) {
            channel.basicPublish("ex.tc.demo", "key.tc", null, ("hello rabbitmq" + i).getBytes());
        }

        channel.close();
        connection.close();
    }
}
