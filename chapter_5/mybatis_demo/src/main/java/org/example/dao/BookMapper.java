package org.example.dao;

import org.apache.ibatis.annotations.Mapper;
import org.example.bean.Book;

import java.util.List;

@Mapper
public interface BookMapper {
     int addBook(Book book);

     int  deleteBookById(Book book);
     int  updateBookById(Book book);

     Book getBookById(Integer id);

     List<Book> getAllBooks();
}
