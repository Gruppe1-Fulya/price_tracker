package com.price_tracker.server.service;

import com.price_tracker.server.entity.Alarm;
import com.price_tracker.server.entity.Watchlist;
import com.price_tracker.server.repository.AlarmRepo;
import com.price_tracker.server.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AlarmService {
  private final AlarmRepo alarmRepo;
  private final EmailService emailService;
  private final WatchlistService watchlistService;

  @Autowired
  public AlarmService(AlarmRepo alarmRepo,
                      EmailService emailService,
                      WatchlistService watchlistService) {
    this.alarmRepo = alarmRepo;
    this.emailService = emailService;
    this.watchlistService = watchlistService;
  }

  public Alarm findAlarmById(int id) {
    return alarmRepo.findById(id);
  }

  public List<Alarm> findAlarmByProductId(int product_id) {
    return alarmRepo.findByProductId(product_id);
  }

  public boolean checkAlarmCondition(Alarm alarm, double current_price, double previous_price) {
    String condition = alarm.getCondition();
    switch (condition) {
      case "ANY_CHANGE":
        // Check if current price is different from the last price
        if (previous_price != current_price) {
          Watchlist wl = watchlistService.findByAlarmId(alarm.getWatchlist_id());
          String email = wl.getUser().getEmail();
          emailService.sendEmail(email, condition);
          alarm.setDate_triggered(LocalDate.now());
          alarmRepo.save(alarm);
          return true;
        }
        break;
      case "EQUALS_TARGET":
        // Check if current price equals target price
        if (current_price == alarm.getTarget_price()) {
          Watchlist wl = watchlistService.findByAlarmId(alarm.getWatchlist_id());
          String email = wl.getUser().getEmail();
          emailService.sendEmail(email, condition);
          alarm.setDate_triggered(LocalDate.now());
          alarmRepo.save(alarm);
          return true;
        }
        break;
      case "BELOW_TARGET":
        // Check if current price is below target price
        if (current_price < alarm.getTarget_price()) {
          Watchlist wl = watchlistService.findByAlarmId(alarm.getWatchlist_id());
          String email = wl.getUser().getEmail();
          emailService.sendEmail(email, condition);
          alarm.setDate_triggered(LocalDate.now());
          alarmRepo.save(alarm);
          return true;
        }
        break;
      case "ABOVE_TARGET":
        // Check if current price is above target price
        if (current_price > alarm.getTarget_price()) {
          Watchlist wl = watchlistService.findByAlarmId(alarm.getWatchlist_id());
          String email = wl.getUser().getEmail();
          emailService.sendEmail(email, condition);
          alarm.setDate_triggered(LocalDate.now());
          alarmRepo.save(alarm);
          return true;
        }
        break;
      default:
        // Handle unrecognized condition
        throw new IllegalArgumentException("Unrecognized alarm condition: " + condition);
    }
    return false;
  }

  public void checkAllAlarmsForProduct(int product_id, double currentPrice, double previous_price) {
    for (Alarm alarm : findAlarmByProductId(product_id)) {
      if (alarm.getDate_triggered() == null) {
        checkAlarmCondition(alarm, currentPrice, previous_price);
      }
    }
  }
}
