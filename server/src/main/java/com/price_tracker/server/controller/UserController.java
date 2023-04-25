package com.price_tracker.server.controller;

import com.price_tracker.server.entity.User;
import com.price_tracker.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
  private final UserService userService;

  @Autowired
  public  UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/addUser")
  public User postUser(@RequestBody User user) {
    return  userService.saveUser(user);
  }
}
