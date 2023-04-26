package com.price_tracker.server.repository;

import com.price_tracker.server.entity.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchlistRepo extends JpaRepository<Watchlist, Integer> {
}