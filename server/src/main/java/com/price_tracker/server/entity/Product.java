package com.price_tracker.server.entity;

import jakarta.persistence.*;

@Entity
@Table
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

  public Product() {

  }

  public Product(String name, String image, String url) {
    this.name = name;
    this.image = image;
    this.url = url;
  }
}