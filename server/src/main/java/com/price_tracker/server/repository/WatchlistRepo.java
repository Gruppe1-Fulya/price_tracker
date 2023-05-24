package com.price_tracker.server.repository;

import java.util.List;
import com.price_tracker.server.entity.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchlistRepo extends JpaRepository<Watchlist, Integer> {
  List<Watchlist> findByUserId(int user_id);
  Watchlist findById(int id);
}