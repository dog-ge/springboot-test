package org.example.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitDirectConfig {
    public final static String DIRECTNAME = "sang-direct";

    @Bean
    Queue queue() {
        return new Queue("hello-queue");
    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(DIRECTNAME, true, false); //第二个参数过期是否删除，第三个参数是重启是否删除，
    }

    @Bean
    Binding binding() {

        return BindingBuilder.bind(queue())
                .to(directExchange()).with("direct");
    }
}