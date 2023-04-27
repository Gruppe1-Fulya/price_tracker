package com.price_tracker.server.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "alarm")
public class Alarm {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(nullable = false)
  private int product_id;

  @Column(nullable = false)
  private LocalDate date_created;

  @Column(nullable = false, name = "Condit")
  private String condition;

  @Column(nullable = false)
  private double target_price;

  @Column()
  private LocalDate date_triggered = null;

  public int getProduct_id() {
    return product_id;
  }

  public void setProduct_id(int product_id) {
    this.product_id = product_id;
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

  public Alarm(int product_id, LocalDate date_created, String condition, double target_price) {
    this.product_id = product_id;
    this.date_created = date_created;
    this.condition = condition;
    this.target_price = target_price;
  }

  public Alarm() {
  }
}
