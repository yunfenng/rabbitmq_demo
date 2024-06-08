package com.lagou.rabbitmq.demo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author: Jaa
 * @Description:
 * @Date 2024/6/8
 */
public class ConsumerListenerApp {

    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(RabbitConfig.class);
    }
}
