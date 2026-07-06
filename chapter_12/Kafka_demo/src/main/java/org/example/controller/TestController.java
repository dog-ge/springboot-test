package org.example.controller;

import org.example.config.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    KafkaProducer kafkaProducer;

@GetMapping("/test")
public String test(){
    kafkaProducer.sendMessage("test-topic","hello world");
    return "ok";
}
}
