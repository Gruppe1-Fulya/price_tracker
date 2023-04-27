package com.price_tracker.server.service;

import com.price_tracker.server.entity.Product;
import com.price_tracker.server.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

  private final ProductRepo productRepo;

  @Autowired
  public ProductService(ProductRepo productRepo) {
    this.productRepo = productRepo;
  }

  public List<Product> getAllProducts() {
    return productRepo.findAll();
  }

  public Product getProductById(int productId) {
    return productRepo.findById(productId);
  }

  //public Product findByURL(String URL) {
  //  return productRepo.findByURL(URL);
  //}

  public Product saveProduct(Product product) {
    return productRepo.save(product);
  }

  public void deleteProductById(int productId) {
    productRepo.deleteById(productId);
  }
}