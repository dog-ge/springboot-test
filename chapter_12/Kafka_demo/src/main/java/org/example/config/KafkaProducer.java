package org.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message).addCallback(
                success -> System.out.println("发送成功: " + message),
                failure -> System.out.println("发送失败: " + failure.getMessage())
        );
    }
}