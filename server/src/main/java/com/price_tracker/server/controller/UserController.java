package com.price_tracker.server.controller;

import com.price_tracker.server.entity.User;
import com.price_tracker.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @GetMapping("/details/{email}")
  public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
    User user = userService.findByEmail(email);
    if (user == null) {
      return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
    } else {
      User reponseUser = new User();
      reponseUser.setEmail(user.getEmail());
      reponseUser.setName(user.getName());
      reponseUser.setSurname(user.getSurname());
      return new ResponseEntity<>(reponseUser, HttpStatus.OK);
    }
  }

  @PostMapping("/create")
  public ResponseEntity<?> createUser(@RequestBody User user) {
    User existingUser = userService.findByEmail(user.getEmail());
    if (existingUser != null) {
      // User already exists, return a conflict response
      return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
    }

    // Encode the password before saving the user
    String encodedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(encodedPassword);

    User savedUser = userService.save(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
    String email = credentials.get("email");
    String password = credentials.get("password");

    User existingUser = userService.findByEmail(email);
    if (existingUser == null) {
      // User does not exist, return a 404 response
      return ResponseEntity.notFound().build();
    }

    boolean passwordMatches = userService.checkPassword(existingUser, password);
    if (!passwordMatches) {
      // Password does not match, return a 401 response
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
    }

    // Password matches, return a success response
    return ResponseEntity.ok(existingUser);
  }

}