package com.sunday.friends.foundation.controller;

import com.sunday.friends.foundation.model.User;
import com.sunday.friends.foundation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@RestController
//@RequestMapping("/rest-service")
public class UserController {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> list(){
        return userService.listAll();
    }


    @GetMapping("/testUser")
    public User getUser(){
        return new User(10, "manasm@gmail.com", 12, "user", "Manas");
    }
}
