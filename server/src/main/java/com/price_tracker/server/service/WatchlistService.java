package com.price_tracker.server.service;

import com.price_tracker.server.entity.Product;
import com.price_tracker.server.entity.User;
import com.price_tracker.server.entity.Watchlist;
import com.price_tracker.server.repository.WatchlistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WatchlistService {

  @Autowired
  private WatchlistRepo watchlistRepo;

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

  public Watchlist addProductToWatchlist(Watchlist watchlist, Product product) {
    watchlist.addProduct(product);
    return watchlistRepo.save(watchlist);
  }

  public Watchlist removeProductFromWatchlist(Watchlist watchlist, Product product) {
    watchlist.removeProduct(product);
    return watchlistRepo.save(watchlist);
  }

  public void deleteWatchlist(int id) {
    watchlistRepo.deleteById(id);
  }
}