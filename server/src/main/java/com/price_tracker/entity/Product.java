package com.price_tracker.entity;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "products")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 2048)
  private String name;
  
  @Column(length = 2048)
  private String image;
  
  @Column(nullable = false, length = 2048)
  private String url;

  @ElementCollection
  private ArrayList<Double> prices;

  public Product() {
    this.prices = new ArrayList<>();
  }

  public Product(String url) throws Exception {
    this.url = url;
    this.prices = new ArrayList<>();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public ArrayList<Double> getPrices() {
    return prices;
  }

  public void setPrices(ArrayList<Double> prices) {
    this.prices = prices;
  }

  public Product(String url, String name, String image, ArrayList<Double> prices) {
    this.url = url;
    this.name = name;
    this.image = image;
    this.prices = prices;
  }
  
  public void addPrice(Double price) {
    this.prices.add(price);
  }
}