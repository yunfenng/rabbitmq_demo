package com.lagou.rabbitmq.demo;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
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
 * @Date 2024/6/7
 */
@Configuration
public class RabbitConfig {

    // 连接工厂
    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactory factory = new CachingConnectionFactory(URI.create("amqp://root:123456@47.103.155.255:5672/%2f"));
        return factory;
    }

    // RabbitTemplate
    @Bean
    @Autowired
    public RabbitTemplate rabbitTemplate(ConnectionFactory factory) {
        RabbitTemplate template = new RabbitTemplate(factory);
        return template;
    }

    // RabbitAdmin
    @Bean
    @Autowired
    public RabbitAdmin rabbitAdmin(ConnectionFactory factory) {
        RabbitAdmin admin = new RabbitAdmin(factory);
        return admin;
    }

    // Queue
    @Bean
    public Queue queue() {
        Queue queue = QueueBuilder.nonDurable("queue.anno").build();
        return queue;
    }

    // Exchange
    @Bean
    public Exchange exchange() {
        FanoutExchange exchange = new FanoutExchange("ex.anno.fanout", false, false, null);
        return exchange;
    }

    // Binding
    @Bean
    @Autowired
    public Binding binding(Queue queue, Exchange exchange) {
        // 创建一个绑定, 不指定绑定的参数
        Binding binding = BindingBuilder.bind(queue).to(exchange).with("key.anno").noargs();
        return binding;
    }

}
