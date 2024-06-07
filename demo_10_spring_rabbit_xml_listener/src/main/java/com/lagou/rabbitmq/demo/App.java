package com.lagou.rabbitmq.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: Jaa
 * @Description:
 * @Date 2024/6/7
 */
public class App {

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("classpath:spring-rabbit.xml");
    }
}
