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
 * @Date 2024/6/15
 */
public class MyCustomer {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://root:123456@47.103.155.255:5672/%2f");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("queue.ca", false, false, false, null);

        channel.basicConsume("queue.ca", false, "MyCustomer", new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                System.out.println(new String(body));

                // 确认消息
                // channel.basicAck(envelope.getDeliveryTag(), false);
                // 第一个参数是消息的标签，第二个参数是不确认是多个消息还是单个消息
                // 第三个参数表示不确认的消息是否需要重新入队列，然后重发
                // channel.basicNack(envelope.getDeliveryTag(), false, true);

                // 用于拒收一条消息
                // 对于不确认的消息，是否重新入列，然后重发
                // channel.basicReject(envelope.getDeliveryTag(), true);
                channel.basicReject(envelope.getDeliveryTag(), false);


            }
        });

        // channel.close();
        // connection.close();
    }

}
