package org.example.dao;


import org.apache.ibatis.annotations.Mapper;
import org.example.bean.Role;
import org.example.bean.User;

import java.util.List;
@Mapper
public interface UserMapper {
    User loadUserByUsername(String username);
    List<Role> getUserRolesByUid(Integer id);
}