package org.example.controller;



import org.example.bean.Result;
import org.example.bean.UserContext;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BusinessController {

    @GetMapping("/user/info")
    public Result<Map<String, String>> getUserInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("msg", "这是需要登录才能看到的数据");
        map.put("currentUser", UserContext.getUsername());
        map.put("userId", UserContext.getUserId());
        map.put("roles", String.join(",", UserContext.getRoles()));
        return Result.ok(map);
    }

    @GetMapping("/admin/dashboard")
    public Result<String> adminDashboard() {
        List<String> roles = UserContext.getRoles();
        if (roles == null || !roles.contains("ROLE_ADMIN")) {
            return Result.error(403, "无权访问，需要管理员角色");
        }
        return Result.ok("管理员专属数据，当前用户: " + UserContext.getUsername());
    }
}