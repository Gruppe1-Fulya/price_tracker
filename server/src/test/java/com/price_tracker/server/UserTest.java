package com.price_tracker.server;

import com.price_tracker.server.controller.UserController;
import com.price_tracker.server.entity.User;
import com.price_tracker.server.repository.UserRepo;
import com.price_tracker.server.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserTest {
  private UserService userService;
  private UserRepo userRepo;
  private UserController userController;

  @BeforeEach
  void setUp() {
    userRepo = mock(UserRepo.class);
    userService = new UserService(userRepo);
    userController = new UserController(userService, mock(BCryptPasswordEncoder.class));
  }

  @Test
  void testGetUserByEmail() {
    // Mocking the behavior of UserRepo.findByEmail()
    String email = "patrick-bateman@gmail.com";
    User user = new User("patrick-bateman@gmail.com", "Patrick", "Bateman", "sigma");
    when(userRepo.findByEmail(email)).thenReturn(user);

    ResponseEntity<?> response = userController.getUserByEmail(email);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    User responseUser = (User) response.getBody();
    assertEquals(user.getEmail(), responseUser.getEmail());
  }

  @Test
  void testGetUserByEmail_UserNotFound() {
    // Mocking the behavior of UserRepo.findByEmail()
    String email = "nonexistent@gmail.com";
    when(userRepo.findByEmail(email)).thenReturn(null);

    ResponseEntity<?> response = userController.getUserByEmail(email);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("User not found!", response.getBody());
  }

  @Test
  void testCreateUser() {
    User user = new User("patrick-bateman@gmail.com", "Patrick", "Bateman", "sigma");
    when(userRepo.findByEmail(user.getEmail())).thenReturn(null);
    when(userRepo.save(user)).thenReturn(user);

    ResponseEntity<?> response = userController.createUser(user);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(user, response.getBody());
  }

  @Test
  void testCreateUser_UserAlreadyExists() {
    User user = new User("patrick-bateman@gmail.com", "Patrick", "Bateman", "sigma");
    when(userRepo.findByEmail(user.getEmail())).thenReturn(user);

    ResponseEntity<?> response = userController.createUser(user);

    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    assertEquals("User already exists", response.getBody());
  }

  @Test
  void testLogin_UserNotFound() {
    String email = "nonexistent@gmail.com";
    when(userRepo.findByEmail(email)).thenReturn(null);

    ResponseEntity<?> response = userController.login(Map.of("email", email, "password", "password"));

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }
}
