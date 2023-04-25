package com.price_tracker.server.service;

import com.price_tracker.server.entity.User;
import com.price_tracker.server.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepo userRepo;

  @Autowired
  public UserService(UserRepo userRepo) {
    this.userRepo = userRepo;
  }

  public User saveUser(User user) {
    return userRepo.save(user);
  }
}
