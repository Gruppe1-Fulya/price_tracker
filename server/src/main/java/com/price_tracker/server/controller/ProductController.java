package com.price_tracker.server.controller;

import com.price_tracker.server.entity.Product;
import com.price_tracker.server.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
  private final ProductService productService;

  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @PostMapping("/addProduct")
  public Product postProduct(@RequestBody Product product) {
    return productService.saveProduct(product);
  }
}