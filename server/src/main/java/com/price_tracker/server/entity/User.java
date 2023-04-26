package com.price_tracker.server.entity;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String surname;

  @Column(nullable = false)
  private String password;

  @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
  private ArrayList<Watchlist> watchlists = new ArrayList<>();

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) { this.surname = surname; }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getId() { return id; }

  public User() {

  }

  public User(String email, String name, String surname, String password) {
    this.email = email;
    this.name = name;
    this.surname = surname;
    this.password = password;
  }
}
