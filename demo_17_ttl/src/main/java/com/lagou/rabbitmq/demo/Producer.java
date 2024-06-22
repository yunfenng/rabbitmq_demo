package com.lagou.rabbitmq.demo;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Jaa
 * @Description:
 * @Date 2024/6/20
 */
public class Producer {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://root:123456@47.103.155.255:5672/%2f");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            Map<String, Object> arguments = new HashMap<>();
            // 消息队列中消息的过期时间：30s
            arguments.put("x-message-ttl", 10 * 1000);
            // 当消息队列没有消费者，10s后消息过期, 消息队列删除
            // arguments.put("x-expires", 10 * 1000);
            arguments.put("x-expires", 60 * 1000);

            channel.queueDeclare("queue.ttl.waiting", false, false, false, arguments);
            channel.exchangeDeclare("ex.ttl.waiting", "direct", false, false, null);
            channel.queueBind("queue.ttl.waiting", "ex.ttl.waiting", "key.ttl.waiting");

            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                    .contentEncoding("utf-8")
                    .deliveryMode(2).build();

            channel.basicPublish("ex.ttl.waiting", "key.ttl.waiting", properties, "等待的订单号".getBytes("utf-8"));

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
