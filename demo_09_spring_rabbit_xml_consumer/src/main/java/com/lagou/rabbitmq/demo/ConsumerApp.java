package com.lagou.rabbitmq.demo;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.UnsupportedEncodingException;

/**
 * @Author: Jaa
 * @Description:
 * @Date 2024/6/7
 */
public class ConsumerApp {

    public static void main(String[] args) throws UnsupportedEncodingException {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("spring-rabbit.xml");
        RabbitTemplate template = context.getBean(RabbitTemplate.class);

        Message message = template.receive("queue.q1");

        System.out.println(new String(message.getBody(), message.getMessageProperties().getContentEncoding()));
        // System.out.println(new String(message.getBody(), "utf-8"));

        context.close();
    }
}
