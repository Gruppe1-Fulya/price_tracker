package com.price_tracker.server.service;

import java.util.List;
import com.price_tracker.server.entity.Alarm;
import com.price_tracker.server.entity.User;
import org.springframework.stereotype.Service;
import com.price_tracker.server.entity.Product;
import com.price_tracker.server.entity.Watchlist;
import com.price_tracker.server.repository.AlarmRepo;
import com.price_tracker.server.repository.ProductRepo;
import com.price_tracker.server.repository.WatchlistRepo;
import org.springframework.beans.factory.annotation.Autowired;

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

  public Watchlist createWatchlist(User user, Product product) {
    product = productRepo.save(product); // persist the product entity first
    Watchlist watchlist = new Watchlist(user, product);
    return watchlistRepo.save(watchlist);
  }

  public void deleteWatchlist(Watchlist watchlist) {
    watchlistRepo.delete(watchlist);
  }

  public void setAlarm(Watchlist watchlist, Alarm alarm) {
    if (watchlist.getAlarm() == null) {
      alarmRepo.save(alarm);
      watchlist.setAlarm(alarm);
      watchlistRepo.save(watchlist);
    } else {
      deleteAlarm(watchlist);
      setAlarm(watchlist, alarm);
    }
  }

  public void deleteAlarm(Watchlist watchlist) {
    int alarm_id = watchlist.getAlarm().getId();
    watchlist.setAlarm(null);
    watchlistRepo.save(watchlist);
    alarmRepo.deleteById(alarm_id);
  }

  public List<Watchlist> getWatchlistsForUser(int user_id) {
    return watchlistRepo.findByUserId(user_id);
  }

  public Watchlist findById(int id) {
    return watchlistRepo.findById(id);
  }
}