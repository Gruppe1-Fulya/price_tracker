package com.price_tracker.server.repository;

import com.price_tracker.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
}
