package org.example.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MyController {

    @GetMapping("/hello")
    public String hello(@RequestParam String name, @RequestParam(defaultValue = "18") Integer age) {
        System.out.println("name = " + name + ", age = " + age);
        return "hello, " + name + ", age: " + age;
    }
}