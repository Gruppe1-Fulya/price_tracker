package com.price_tracker.server.controller;

import com.price_tracker.server.entity.User;
import com.price_tracker.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable int id) {
    User user = userService.findById(id);
    if (user == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(user, HttpStatus.OK);
    }
  }

  @PostMapping
  public ResponseEntity<User> createUser(@RequestBody User user) {
    User savedUser = userService.save(user);
    return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
  }

  @PostMapping("/{id}/check-password")
  public ResponseEntity<Boolean> checkPassword(@PathVariable int id, @RequestParam String password) {
    User user = userService.findById(id);
    if (user == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      boolean passwordMatches = userService.checkPassword(user, password);
      return new ResponseEntity<>(passwordMatches, HttpStatus.OK);
    }
  }

}