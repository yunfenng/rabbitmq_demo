package com.lagou.rabbitmq.demo;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


/**
 * @Author: Jaa
 * @Description:
 * @Date 2024/6/12
 */
public class PublisherConfirmProducer {

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

        // 发送消息
        channel.basicPublish("ex.pc", "key.pc", null, "hello world".getBytes());

        try {
            // 同步的方式等待RabbitMQ服务器返回确认结果
            channel.waitForConfirmsOrDie(5_000);
            System.out.println("发送的消息已得到确认");
        }catch (IOException e) {
            System.out.println("消息被拒收");
        } catch (IllegalStateException e) {
            System.out.println("发送消息的通道不是PublisherConfirms通道");
        } catch (TimeoutException e) {
            System.out.println("等待消息确认超时");
        }


        channel.close();
        connection.close();
    }
}
