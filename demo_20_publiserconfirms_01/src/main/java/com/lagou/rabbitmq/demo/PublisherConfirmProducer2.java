package com.lagou.rabbitmq.demo;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @Author: Jaa
 * @Description:
 * @Date 2024/6/12
 */
public class PublisherConfirmProducer2 {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://root:123456@47.103.155.255:5672/%2f");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 向RabbitMQ服务器发送AMQP命令，将当前通道标记为发送方确认通道
        AMQP.Confirm.SelectOk selectOk = channel.confirmSelect();

        channel.queueDeclare("queue.pc", true, false, false, null);
        channel.exchangeDeclare("ex.pc", "direct", true, false, null);
        channel.queueBind("queue.pc", "ex.pc", "key.pc");

        String message = "hello-";
        // 批处理的大小
        int batchSize = 10;
        // 用于对需要等待确认消息的计数
        int outstandingConfirms = 0;

        for (int i = 0; i < 103; i++) {
            channel.basicPublish("ex.pc", "key.pc", null, (message + i).getBytes());
            outstandingConfirms++;
            if (outstandingConfirms == batchSize) {
                // 此时已经有一个批次的消息需要同步等待broker的确认消息
                // 同步等待
                channel.waitForConfirmsOrDie(5_000);
                System.out.println("消息已经被确认了");
                outstandingConfirms = 0;
            }
        }

        if (outstandingConfirms > 0) {
            channel.waitForConfirmsOrDie(5_000);
            System.out.println("剩余消息已经被确认了");
        }

        channel.close();
        connection.close();

    }

}
