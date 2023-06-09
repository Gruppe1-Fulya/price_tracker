package com.price_tracker.server.entity;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "alarm")
public class Alarm {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(nullable = false)
  private int productId;

  @Column(nullable = false)
  private int watchlist_id;

  @Column(nullable = false)
  private LocalDate date_created;

  @Column(nullable = false, name = "Condit")
  private String condition;

  @Column(nullable = false)
  private double target_price;

  @Column()
  private LocalDate date_triggered = null;

  public int getProductId() {
    return productId;
  }

  public void setProductId(int product_id) {
    this.productId = product_id;
  }

  public LocalDate getDate_created() {
    return date_created;
  }

  public void setDate_created(LocalDate date_created) {
    this.date_created = date_created;
  }

  public String getCondition() {
    return condition;
  }

  public void setCondition(String condition) {
    this.condition = condition;
  }

  public double getTarget_price() {
    return target_price;
  }

  public void setTarget_price(double target_price) {
    this.target_price = target_price;
  }

  public LocalDate getDate_triggered() {
    return date_triggered;
  }

  public void setDate_triggered(LocalDate date_triggered) {
    this.date_triggered = date_triggered;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getWatchlist_id() { return watchlist_id; }

  public void setWatchlist_id(int watchlist_id) { this.watchlist_id = watchlist_id; }

  public Alarm(int product_id, int watchlist_id, LocalDate date_created, String condition, double target_price) {
    this.productId = product_id;
    this.watchlist_id = watchlist_id;
    this.date_created = date_created;
    this.condition = condition;
    this.target_price = target_price;
  }

  public Alarm() {
  }
}
