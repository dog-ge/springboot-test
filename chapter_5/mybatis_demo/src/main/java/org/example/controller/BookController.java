package org.example.controller;

import org.example.bean.Book;
import org.example.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {
@Autowired
BookService bookService;
@GetMapping("/book")
public void test() {
    Book b1 = new Book("西厢记", "王石浦");
    Integer i = bookService.addBook(b1);
    System.out.println("add book >> " + i);

    Book b2 = new Book(1, "朝花夕拾", "鲁迅");
    int update_id = bookService.updateBook(b2);
    System.out.println("update >> " + update_id);

    Book b3 = bookService.getBookById(1);
    System.out.println("get book by id >>>"+b3);

    List<Book>  all= bookService.getAllBooks();
    System.out.println("all >>>>>: "+all);
}
}
