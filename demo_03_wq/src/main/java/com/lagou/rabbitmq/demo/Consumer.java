package com.lagou.rabbitmq.demo;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Author: Jaa
 * @Description:
 * @Date 2024/6/5
 */
public class Consumer {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://root:123456@服务器地址:5672/%2f");

        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare("queue.wq", true, false, false, null);

        channel.basicConsume("queue.wq", new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                System.out.println("推送过来的消息：" + new String(message.getBody(), StandardCharsets.UTF_8));
            }
        }, new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {
                System.out.println("Cancel 消费者取消消费：" + consumerTag);
            }
        });
    }
}
