package com.price_tracker.server.service;

import com.price_tracker.server.entity.Product;
import com.price_tracker.server.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
  private final ProductRepo productRepo;

  @Autowired
  public ProductService(ProductRepo productRepo) {
    this.productRepo = productRepo;
  }

  public Product saveProduct(Product product) {
    return productRepo.save(product);
  }
}