package com.lagou.rabbitmq.demo.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Jaa
 * @Description:
 * @Date 2024/6/9
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Queue queue() {
        return QueueBuilder.nonDurable("queue.boot").build();
    }


}
