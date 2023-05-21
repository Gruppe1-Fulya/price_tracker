package com.price_tracker.client.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Alarm {
  private int id;
  private int productId;
  @JsonProperty("watchlist_id")
  private int watchlistId;
  @JsonProperty("date_created")
  private String dateCreated;
  private String condition;
  @JsonProperty("target_price")
  private Double targetPrice;
  @JsonProperty("date_triggered")
  private String dateTriggered;

  public Alarm() {
  }

  public Alarm(int id, int productId, int watchlistId, String dateCreated, String condition, Double targetPrice, String dateTriggered) {
    this.id = id;
    this.productId = productId;
    this.watchlistId = watchlistId;
    this.dateCreated = dateCreated;
    this.condition = condition;
    this.targetPrice = targetPrice;
    this.dateTriggered = dateTriggered;
  }

  // Getter and setter methods here

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getProductId() {
    return productId;
  }

  public void setProductId(int productId) {
    this.productId = productId;
  }

  public int getWatchlistId() {
    return watchlistId;
  }

  public void setWatchlistId(int watchlistId) {
    this.watchlistId = watchlistId;
  }

  public String getDateCreated() {
    return dateCreated;
  }

  public void setDateCreated(String dateCreated) {
    this.dateCreated = dateCreated;
  }

  public String getCondition() {
    return condition;
  }

  public void setCondition(String condition) {
    this.condition = condition;
  }

  public Double getTargetPrice() {
    return targetPrice;
  }

  public void setTargetPrice(Double targetPrice) {
    this.targetPrice = targetPrice;
  }

  public String getDateTriggered() {
    return dateTriggered;
  }

  public void setDateTriggered(String dateTriggered) {
    this.dateTriggered = dateTriggered;
  }
}
