package com.price_tracker.client.objects;

public class User {
  public static String name;
  String surname;
  public static String email;
  public static int id;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public User(String name, String surname, String email, int id) {
    this.name = name;
    this.surname = surname;
    this.email = email;
    this.id = id;
  }
}
