package com.lf.backstage.controller;


import com.lf.backstage.entity.User;
import com.lf.backstage.entity.dto.UserLoginRequests;
import com.lf.backstage.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 小肚
 * @since 2025-02-11
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping
    public List<User> login() {
        return userService.list();
    }

    @PostMapping("/login")
    public User login(@RequestBody UserLoginRequests requests) {
        return null;
    }
}

