package com.lagou.rabbitmq.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

/**
 * @Author: Jaa
 * @Description:
 * @Date 2024/6/20
 */
public class MyCustomer {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://root:123456@47.103.155.255:5672/%2f");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare("queue.tc.demo", false, false, false, null);

        for (int i = 0; i < 25; i++) {
            GetResponse getResponse = channel.basicGet("queue.tc.demo", true);
            System.out.println(new String(getResponse.getBody()));
        }

        channel.close();
        connection.close();
    }
}
