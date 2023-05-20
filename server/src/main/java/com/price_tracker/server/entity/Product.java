package com.price_tracker.server.entity;

import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "product")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String image;

  @Column(nullable = false)
  private String url;

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Watchlist> watchlists = new ArrayList<>();

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private List<Price> prices = new ArrayList<>();

  public String getName() { return name; }

  public void setName(String name) {
    this.name = name;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public int getId() { return id; }

  public List<Price> getPrices() {
    return prices;
  }

  public void setPrices(List<Price> prices) {
    this.prices = prices;
  }

  public Product() {

  }

  public Product(String name, String image, String url) {
    this.name = name;
    this.image = image;
    this.url = url;
  }
}