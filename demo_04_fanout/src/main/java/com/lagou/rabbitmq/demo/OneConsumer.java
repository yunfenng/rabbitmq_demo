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
public class OneConsumer {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://root:123456@xxxx:5672/%2f");
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        final String queueName = channel.queueDeclare().getQueue();
        System.out.println("生成的临时队列名字为:" + queueName);

        channel.exchangeDeclare(
                "ex.myfan",
                BuiltinExchangeType.FANOUT,
                true,
                false,
                null);

        channel.queueBind(queueName, "ex.myfan", "");

        channel.basicConsume(queueName, (consumerTag, message) -> {
            System.out.println("One " + new String(message.getBody(), "utf-8"));
        }, consumerTag -> {

        });
    }
}
