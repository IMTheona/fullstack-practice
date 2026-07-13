package com.theona.controller;

import com.theona.pojo.Result;
import com.theona.pojo.User;
import com.theona.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    // 查询
    @PostMapping("/register")
    public Result register(String username,String password){
        User user = userService.findByUserName(username);
        // 没有被占用
        if(user==null){
            // 注册
            userService.register(username,password);
            return Result.success();
        }else {
            // 占用
            return Result.error("用户名已被占用");
        }
    }
}
