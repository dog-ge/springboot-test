package org.example.controller;

import org.example.bean.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {
    @MessageMapping("/hello")
    @SendTo("/topic/greetings") //发送给broker
    public Message greeting(Message message) throws Exception {
        return message;
    }
}

