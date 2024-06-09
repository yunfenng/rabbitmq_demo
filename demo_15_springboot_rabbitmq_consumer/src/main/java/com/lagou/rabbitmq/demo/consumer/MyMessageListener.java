package com.lagou.rabbitmq.demo.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @Author: Jaa
 * @Description:
 * @Date 2024/6/9
 */
@Component
public class MyMessageListener {

    @RabbitListener(queues = "queue.boot")
    public void getMyMessage(@Payload String message, @Header(name = "hello") String value) {
        System.out.println(message);
        System.out.println("hello " + value);
    }
}
