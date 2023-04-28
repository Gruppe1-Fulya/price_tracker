package com.price_tracker.server.controller;

import java.util.List;
import com.price_tracker.server.entity.Alarm;
import com.price_tracker.server.entity.Product;
import com.price_tracker.server.entity.User;
import com.price_tracker.server.entity.Watchlist;
import com.price_tracker.server.service.ProductService;
import com.price_tracker.server.service.UserService;
import com.price_tracker.server.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/watchlists")
public class WatchlistController {
  private final WatchlistService watchlistService;
  private final UserService userService;
  private final ProductService productService;

  @Autowired
  public WatchlistController(WatchlistService watchlistService,
                             UserService userService,
                             ProductService productService) {
    this.watchlistService = watchlistService;
    this.userService = userService;
    this.productService = productService;
  }

  @GetMapping("/load/{user_id}")
  public ResponseEntity<List<Watchlist>> getWatchlistsForUser(@PathVariable("user_id") int userId) {
    return ResponseEntity.status(HttpStatus.FOUND).body(watchlistService.getWatchlistsForUser(userId));
  }

  @PostMapping("/add-product")
  public ResponseEntity<Watchlist> createWatchlistForUser(@RequestParam String email,
                                          @RequestParam String name,
                                          @RequestParam String url,
                                          @RequestParam String image) {
    User user = userService.findByEmail(email);
    if (user != null) {
      if (productService.findByURL(url) == null) {
        Product product = new Product(name, image, url);
        return ResponseEntity.status(HttpStatus.CREATED).body(watchlistService.createWatchlist(user, product));
      } else {
        Product product = productService.findByURL(url);
        return ResponseEntity.status(HttpStatus.CREATED).body(watchlistService.createWatchlist(user, product));
      }
    }
    return  ResponseEntity.notFound().build();
  }

  @PostMapping("/remove/{watchlist_id}")
  public ResponseEntity<?> deleteWatchlist(@PathVariable("watchlist_id") int watchlistId) {
    Watchlist watchlist = new Watchlist();
    watchlist.setId(watchlistId);

    watchlistService.deleteWatchlist(watchlist);
    return ResponseEntity.status(HttpStatus.OK).body("Product deleted.");
  }

  @PostMapping("/set-alarm")
  public ResponseEntity<?> setAlarm(@RequestBody Alarm alarm) {
    int id = alarm.getWatchlist_id();
    watchlistService.setAlarm(watchlistService.findById(id), alarm);
    return ResponseEntity.status(HttpStatus.OK).body("Alarm set!");
  }

  @PostMapping("/remove-alarm/{watchlist_id}")
  public ResponseEntity<?> deleteAlarm(@PathVariable int watchlist_id) {
    Watchlist watchlist = watchlistService.findById(watchlist_id);
    watchlistService.deleteAlarm(watchlist);
    return ResponseEntity.status((HttpStatus.OK)).body("Alarm deleted!");
  }
}