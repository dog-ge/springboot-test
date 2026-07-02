package org.example.controller;

import org.example.bean.Book;
import org.example.bean.UserPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class bookTestController {


    @Autowired
    Book book ;

    @Autowired
    UserPassword userPassword;
    @GetMapping("/book")
    public Book test() {

        return book;
    }
    @GetMapping("/user")
    public UserPassword test2(){
        return userPassword;
    }

}
