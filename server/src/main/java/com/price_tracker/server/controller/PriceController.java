package com.price_tracker.server.controller;

import java.util.List;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import com.price_tracker.server.entity.Price;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.price_tracker.server.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/price")
public class PriceController {
  private final PriceService priceService;

  @Autowired
  public  PriceController(PriceService priceService) {
    this.priceService = priceService;
  }

  @PostMapping("/start")
  public ResponseEntity<String> startChecking() throws IOException {
    priceService.updatePrices();
    System.out.println("printed this start request");
    return ResponseEntity.ok().body("Started");
  }

  @GetMapping("/price-list/{product_id}")
  public ResponseEntity<List<Price>> listPrices(@PathVariable int product_id) {
    return ResponseEntity.status(HttpStatus.FOUND).body(priceService.getPricesForLast7Days(product_id));
  }

  @GetMapping("/update-price/{product_id}")
  public ResponseEntity<?> updatePrice(@PathVariable int product_id) throws IOException {
    priceService.checkIndividualPrice(product_id);
    return ResponseEntity.ok(HttpStatus.OK);
  }
}
