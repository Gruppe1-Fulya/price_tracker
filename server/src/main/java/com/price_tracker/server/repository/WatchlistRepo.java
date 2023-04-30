package com.price_tracker.server.repository;

import com.price_tracker.server.entity.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WatchlistRepo extends JpaRepository<Watchlist, Integer> {
  List<Watchlist> findByUserId(int user_id);
  Watchlist findById(int id);
  Watchlist findByAlarmId(int alarm_id);
}