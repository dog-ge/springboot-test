package org.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class restController {
    @GetMapping("/test")
    public String test(){
        return "hello world";
    }
}
