package com.price_tracker.server.service;

import com.price_tracker.server.entity.User;
import com.price_tracker.server.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

  private final UserRepo userRepo;

  @Autowired
  public UserService(UserRepo userRepo) {
    this.userRepo = userRepo;
  }

  public User save(User user) {
    return userRepo.save(user);
  }

  public User findById(int id) {
    return userRepo.findById(id).orElse(null);
  }

  public User findByEmail(String email) {
    return userRepo.findByEmail(email);
  }

  public void delete(User user) {
    userRepo.delete(user);
  }

  public boolean checkPassword(User user, String password) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    return encoder.matches(password, user.getPassword());
  }

}