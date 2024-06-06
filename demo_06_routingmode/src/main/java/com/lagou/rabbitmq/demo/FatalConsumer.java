package com.lagou.rabbitmq.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @Author: Jaa
 * @Description:
 * @Date 2024/6/6
 */
public class FatalConsumer {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://root:123456@47.103.155.255:5672/%2f");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare("ex.routing", "direct", false, false, null);
        channel.queueDeclare("queue.fatal", false, false, false, null);

        channel.queueBind("queue.fatal", "ex.routing", "FATAL");

        channel.basicConsume("queue.fatal", (consumerTag, message) -> {
            System.out.println("FatalConsumer收到的消息：" + new String(message.getBody(), "utf-8"));
        }, consumerTag -> {

        });

    }
}
