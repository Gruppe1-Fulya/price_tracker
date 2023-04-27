package com.price_tracker.server.service;

import com.price_tracker.server.entity.User;
import org.springframework.stereotype.Service;
import com.price_tracker.server.entity.Product;
import com.price_tracker.server.entity.Watchlist;
import com.price_tracker.server.repository.AlarmRepo;
import com.price_tracker.server.repository.ProductRepo;
import com.price_tracker.server.repository.WatchlistRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service
public class WatchlistService {

  private final WatchlistRepo watchlistRepo;
  private final ProductRepo productRepo;
  private final AlarmRepo alarmRepo;

  @Autowired
  public WatchlistService(WatchlistRepo watchlistRepo, ProductRepo productRepo, AlarmRepo alarmRepo) {
    this.watchlistRepo = watchlistRepo;
    this.productRepo = productRepo;
    this.alarmRepo = alarmRepo;
  }

  public List<Watchlist> getAllWatchlists() {
    return watchlistRepo.findAll();
  }

  public Optional<Watchlist> getWatchlistById(int id) {
    return watchlistRepo.findById(id);
  }

  public Watchlist createWatchlist(User user) {
    Watchlist watchlist = new Watchlist(user);
    return watchlistRepo.save(watchlist);
  }

  public void addProductToWatchlist(int userId, int productId) {
    Product product = productRepo.findById(productId);
    Watchlist watchlist = new Watchlist();
    watchlist.setProduct(product);
    watchlistRepo.save(watchlist);
  }

  public void deleteWatchlist(int id) {
    watchlistRepo.deleteById(id);
  }
}