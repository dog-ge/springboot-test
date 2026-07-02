package org.example.service;

import org.example.bean.Book;
import org.example.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    BookDao bookDao;


    public int addBook(Book book){
       return bookDao.addBook(book);
    }


    public int  updateBook(Book book){
       return bookDao.updateBook(book);
    }

    public Book getBookById(Integer id){
//        RowMapper将查询出来的列和实体一一对应
       return bookDao.getBookById(id);

    }

    public List<Book> getAllBooks(){
       return bookDao.getAllBooks();
    }
}
