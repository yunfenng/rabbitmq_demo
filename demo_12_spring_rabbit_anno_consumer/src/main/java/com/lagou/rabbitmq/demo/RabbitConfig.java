package com.lagou.rabbitmq.demo;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

/**
 * @Author: Jaa
 * @Description:
 * @Date 2024/6/8
 */
@Configuration
public class RabbitConfig {


    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactory factory = new CachingConnectionFactory(URI.create("amqp://root:123456@47.103.155.255:5672/%2f"));
        return factory;
    }

    @Bean
    @Autowired
    public RabbitTemplate rabbitTemplate(ConnectionFactory factory) {
        return new RabbitTemplate(factory);
    }

    @Bean
    @Autowired
    public RabbitAdmin rabbitAdmin(ConnectionFactory factory) {
        return new RabbitAdmin(factory);
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.nonDurable("queue.anno").build();
    }

}
