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
        factory.setUri("amqp://root:123456@xxxx:5672/%2f");
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();
        // 声明fanout类型的交换机
        channel.exchangeDeclare("ex.myfan", "fanout", true, false, null);
        for (int i = 0; i < 20; i++) {
            channel.basicPublish(
                    "ex.myfan",
                    "", // fanout类型的交换机不需要指定路由键
                    null,
                    ("hello world fan" + i).getBytes("utf-8"));
        }
        channel.close();
        connection.close();
    }

}
