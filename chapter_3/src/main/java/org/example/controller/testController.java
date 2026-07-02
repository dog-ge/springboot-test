package org.example.controller;

import org.example.bean.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RestController
public class testController {

@GetMapping("/books")
public ModelAndView  test(){
    List<Book> books = new ArrayList<>();
    Book b1 = new Book();
    b1.setId(1);
    b1.setAuthor("罗贯中");
    b1.setName("三国演义");

    Book b2 = new Book();
    b2.setId(2);
    b2.setAuthor("罗贯中2");
    b2.setName("三国演义2");

    books.add(b1);
    books.add(b2);
    ModelAndView mv =new ModelAndView();
    mv.addObject("books",books);
    mv.setViewName("books");
    return mv;
}
}
