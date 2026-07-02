package org.example.controller;

import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AopController {
    @Autowired
    UserService userService;
    @GetMapping("/getUserById")
    public String  getUserById(Integer id){
        return userService.getUserById(id);

    }
    @GetMapping("/deleteUserById")
    public void  deleteUserById(Integer id){
         userService.deleteUserById(id);

    }

}
