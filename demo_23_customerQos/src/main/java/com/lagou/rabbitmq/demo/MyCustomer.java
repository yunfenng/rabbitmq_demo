package com.lagou.rabbitmq.demo;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * @Author: Jaa
 * @Description:
 * @Date 2024/6/19
 */
public class MyCustomer {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://root:123456@47.103.155.255:5672/%2f");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare("queue.qos", false, false, false, null);

        // 使用basic做限流，仅对消息推送模式生效
        // 表示Qos是10个消息，最多有10个消息等待确认
        channel.basicQos(10);
        // 表示最多有10个消息确认。如果global设置为true，则表示只有使用当前的channel的Customer，该设置都生效
        // false表示仅限当前Customer
        channel.basicQos(10, false);
        // 第一个参数表示未确认消息的大小，RabbitMQ没有实现，不用管
        channel.basicQos(1000, 10, true);


        channel.basicConsume("queue.qos", false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                // 可以批量确认消息，减少每个消息都发送确认带来的网络流量负载
                channel.basicAck(envelope.getDeliveryTag(), true);
            }
        });

        channel.close();
        connection.close();
    }
}
