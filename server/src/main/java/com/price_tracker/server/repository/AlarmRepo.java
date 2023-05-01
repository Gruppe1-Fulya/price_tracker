package com.price_tracker.server.repository;

import com.price_tracker.server.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmRepo extends JpaRepository<Alarm, Integer> {
  Alarm findById(int id);
  List<Alarm> findByProductId(int productId);
}
