package com.sunday.friends.foundation.controller;

import com.sunday.friends.foundation.model.User;
import com.sunday.friends.foundation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> list(){
        return userService.listAll();
    }
}
