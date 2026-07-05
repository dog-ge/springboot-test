package org.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

@Component
public class JmsComponent {

    @Autowired
    private JmsMessagingTemplate messagingTemplate;

    @Autowired
    private Queue queue;

    public void send(org.example.bean.Message msg) {
        messagingTemplate.convertAndSend(this.queue, msg);
    }

    @JmsListener(destination = "amq")
    public void receive(org.example.bean.Message msg) {
        System.out.println("receive:" + msg);
    }
}