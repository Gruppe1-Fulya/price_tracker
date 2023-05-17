package com.price_tracker.server.repository;

import com.price_tracker.server.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
  Product findByUrl(String url);
}