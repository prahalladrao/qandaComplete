package com.stackoverflow.qanda.controller;

import com.stackoverflow.qanda.model.User;
import com.stackoverflow.qanda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController{
    @Autowired
    private UserService userService;
    @PostMapping
    public String saveUser(@RequestBody User user)
    {
        System.out.println(user);
          if(userService.saveUser(user))
              return user.getUserName();
          return null;
    }
}
