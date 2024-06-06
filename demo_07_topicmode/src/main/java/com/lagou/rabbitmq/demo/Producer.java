package com.lagou.rabbitmq.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Random;

/**
 * @Author: Jaa
 * @Description:
 * @Date 2024/6/6
 */
public class Producer {

    private static final String[] LOG_LEVEL = {"info", "error", "warn"};
    private static final String[] LOG_AREA = {"beijing", "shanghai", "shenzhen"};
    private static final String[] LOG_BIZ = {"edu-online", "biz-online", "emp-online"};

    private static final Random random = new Random();

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://root:123456@47.103.155.255:5672/%2f");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare("ex.topic", "topic", true, false, null);

        String area, biz, level;
        String routingKey, message;

        for (int i = 0; i < 100; i++) {
            area = LOG_AREA[random.nextInt(LOG_AREA.length)];
            biz = LOG_BIZ[random.nextInt(LOG_BIZ.length)];
            level = LOG_LEVEL[random.nextInt(LOG_LEVEL.length)];
            // routingKey中包含了三个维度
            routingKey = area + "." + biz + "." + level;
            message = "LOG: [" + level + "] :这是 [" + area + "] 地区 [" + biz + "] 服务器发来的消息，MSG_SEQ = " + i;
            channel.basicPublish("ex.topic", routingKey, null, message.getBytes("utf-8"));
        }

        channel.close();
        connection.close();
    }


}
