package com.price_tracker.server.repository;

import com.price_tracker.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
  User findByEmail(String email);

}