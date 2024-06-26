package com.lagou.rabbitmq.demo;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.io.UnsupportedEncodingException;

/**
 * @Author: Jaa
 * @Description:
 * @Date 2024/6/7
 */
public class ProducerApp {

    public static void main(String[] args) throws UnsupportedEncodingException {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(RabbitConfig.class);

        RabbitTemplate template = context.getBean(RabbitTemplate.class);

        MessageProperties messageProperties = MessagePropertiesBuilder
                .newInstance()
                .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                .setContentEncoding("gbk")
                .setHeader("myKey", "myValue")
                .build();

        /*Message message = MessageBuilder
                .withBody("你好, 世界".getBytes("gbk"))
                .andProperties(messageProperties)
                .build();*/

        for (int i = 0; i < 1000; i++) {
            Message message = MessageBuilder
                    .withBody(("你好, 世界" + i).getBytes("gbk"))
                    .andProperties(messageProperties)
                    .build();
            template.send("ex.anno.fanout", "key.anno", message);
        }


        context.close();

    }
}
