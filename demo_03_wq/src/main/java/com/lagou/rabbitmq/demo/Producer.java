package com.lagou.rabbitmq.demo;

import com.rabbitmq.client.BuiltinExchangeType;
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
        factory.setUri("amqp://root:123456@服务器地址:5672/%2f");

        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        // 声明一个消息队列
        channel.queueDeclare("queue.wq", true, false, false, null);
        // 声明 direct 交换器
        channel.exchangeDeclare("ex.wq", BuiltinExchangeType.DIRECT, true, false, null);
        // 将消息队列绑定到指定的交换器，并指定路由键
        channel.queueBind("queue.wq", "ex.wq", "key.wq");
        for (int i = 0; i < 15; i++) {
            channel.basicPublish(
                    "ex.wq",
                    "key.wq",
                    null,
                    ("工作队列：" + i).getBytes("utf-8"));
        }

        channel.close();
        connection.close();

    }
}
