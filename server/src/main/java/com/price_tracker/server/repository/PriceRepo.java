package com.price_tracker.server.repository;

import com.price_tracker.server.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PriceRepo extends JpaRepository<Price, Integer> {
  Price findByProductId(int product_id);
  List<Price> findByProductIdAndDateBetweenOrderByDateDesc(int productId, LocalDate startDate, LocalDate endDate);

  Price findTopByProductIdOrderByDateDesc(int id);
}
