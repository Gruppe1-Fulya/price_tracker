package com.price_tracker.server.service;

import org.springframework.stereotype.Service;
import com.price_tracker.server.entity.Product;
import com.price_tracker.server.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ProductService {
  private final ProductRepo productRepo;

  @Autowired
  public ProductService(ProductRepo productRepo) {
    this.productRepo = productRepo;
  }

  public Product findByURL(String URL) {
    return productRepo.findByUrl(URL);
  }

  public Product saveProduct(Product product) {
    return productRepo.save(product);
  }

  public void deleteProductById(int productId) {
    productRepo.deleteById(productId);
  }
}