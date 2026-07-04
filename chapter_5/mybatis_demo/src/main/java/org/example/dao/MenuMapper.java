package org.example.dao;

import org.apache.ibatis.annotations.Mapper;
import org.example.bean.Book;
import org.example.bean.Menu;

import java.util.List;

@Mapper
public interface MenuMapper {
     List<Menu> getAllMenus();
}
