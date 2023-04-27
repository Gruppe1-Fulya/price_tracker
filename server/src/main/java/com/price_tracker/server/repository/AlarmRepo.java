package com.price_tracker.server.repository;

import com.price_tracker.server.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepo extends JpaRepository<Alarm, Integer> {
}
