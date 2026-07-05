package org.example;

import org.example.bean.Message;
import org.example.config.JmsComponent;
import org.junit.jupiter.api.Test;        // ✅ 注意：junit.jupiter.api
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class DemoApplicationTests {
    @Autowired
    JmsComponent jmsComponent;

    @Test
    void contextLoads() {
        Message msg = new Message();
        msg.setContent("hello jms!");
        msg.setDate(new Date());
        jmsComponent.send(msg);
    }
}