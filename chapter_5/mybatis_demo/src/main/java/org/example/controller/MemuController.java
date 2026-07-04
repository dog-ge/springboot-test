package org.example.controller;

import org.example.bean.Book;
import org.example.bean.Menu;
import org.example.service.BookService;
import org.example.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MemuController {
@Autowired
MenuService menuService;
@GetMapping("/menu")
public void test() {
   List<Menu> menus=menuService.getAllMenus();
    System.out.println("======> "+ menus);
}
}
