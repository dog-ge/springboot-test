package org.example.service;


import org.example.bean.Menu;
import org.example.dao.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    @Autowired
    MenuMapper menuMapper;
    public List<Menu> getAllMenus(){
        return menuMapper.getAllMenus();
    }

}
