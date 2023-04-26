package com.price_tracker.server.controller;

import com.price_tracker.server.entity.Product;
import com.price_tracker.server.entity.User;
import com.price_tracker.server.entity.Watchlist;
import com.price_tracker.server.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/watchlists")
public class WatchlistController {

  @Autowired
  private WatchlistService watchlistService;

  @GetMapping
  public ResponseEntity<List<Watchlist>> getAllWatchlists() {
    List<Watchlist> watchlists = watchlistService.getAllWatchlists();
    return new ResponseEntity<>(watchlists, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Watchlist> createWatchlist(@RequestBody User user) {
    Watchlist watchlist = watchlistService.createWatchlist(user);
    return new ResponseEntity<>(watchlist, HttpStatus.CREATED);
  }

  @PostMapping("/{watchlistId}/addProduct")
  public ResponseEntity<Watchlist> addProductToWatchlist(@PathVariable int watchlistId, @RequestBody Product product) {
    Optional<Watchlist> optionalWatchlist = watchlistService.getWatchlistById(watchlistId);
    if (optionalWatchlist.isPresent()) {
      Watchlist watchlist = optionalWatchlist.get();
      watchlistService.addProductToWatchlist(watchlist, product);
      return new ResponseEntity<>(watchlist, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}