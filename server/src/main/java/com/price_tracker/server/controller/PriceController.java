package com.price_tracker.server.controller;

import com.price_tracker.server.entity.Price;
import com.price_tracker.server.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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
    return ResponseEntity.ok().body("Started");
  }

  @GetMapping("/price-list/{product_id}")
  public ResponseEntity<List<Price>> listPrices(@PathVariable int product_id) {
    return ResponseEntity.status(HttpStatus.FOUND).body(priceService.getPricesForLast7Days(product_id));
  }
}
