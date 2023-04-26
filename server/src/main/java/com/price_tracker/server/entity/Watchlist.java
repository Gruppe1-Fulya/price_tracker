package com.price_tracker.server.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "watchlists")
public class Watchlist {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "watchlists_products",
      joinColumns = @JoinColumn(name = "watchlist_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id")
  )
  private List<Product> products = new ArrayList<>();

  public Watchlist() {
  }

  public Watchlist(User user) {
    this.user = user;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public List<Product> getProducts() {
    return products;
  }

  public void setProducts(List<Product> products) {
    this.products = products;
  }

  public void addProduct(Product product) {
    products.add(product);
    product.getWatchlists().add(this);
  }

  public void removeProduct(Product product) {
    products.remove(product);
    product.getWatchlists().remove(this);
  }
}