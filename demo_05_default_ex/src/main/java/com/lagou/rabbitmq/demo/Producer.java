package com.lagou.rabbitmq.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @Author: Jaa
 * @Description:
 * @Date 2024/6/5
 */
public class Producer {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("47.103.155.255");
        factory.setVirtualHost("/");
        factory.setUsername("root");
        factory.setPassword("123456");
        factory.setPort(5672);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(
                "queue.default.ex", // 队列名称
                false,   // 能否活到MQ重启
                false,  // 是否只能是自己的连接使用
                false, // 是否自动删除
                null); // 是否有属性

        // 在发送消息的时候没有指定交换器的名字, 此时使用默认的交换器, 默认交换器就没有名字
        // 路由器就是指目的地消息队列的名字
        channel.basicPublish("", "queue.default.ex", null, "hello rabbitmq".getBytes());

        channel.close();
        connection.close();
    }
}
