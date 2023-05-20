package com.price_tracker.client.objects;

import java.io.*;
import java.net.URL;
import org.jsoup.Jsoup;
import java.util.ArrayList;
import org.jsoup.nodes.Document;
import java.net.HttpURLConnection;

public class Product {
  private int id;
  private String url;
  private String name;
  private String image;
  private ArrayList<Double> prices;
  private int alarm_id = -1;
  private int watchlist_id;

  public Product(String url) throws Exception {
    this.url = url;
    this.prices = new ArrayList<>();
    extractData();
  }

  public Product(int id, String url, String name, String image, int watchlist_id) {
    this.id = id;
    this.url = url;
    this.name = name;
    this.image = image;
    this.watchlist_id = watchlist_id;
    this.prices = new ArrayList<Double>();
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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

  public int getAlarm_id() {
    return alarm_id;
  }

  public void setAlarm_id(int alarm_id) {
    this.alarm_id = alarm_id;
  }

  public void addPrice(double price) {
    this.prices.add(price);
  }

  public int getWatchlist_id() {
    return watchlist_id;
  }

  public void setWatchlist_id(int watchlist_id) {
    this.watchlist_id = watchlist_id;
  }

  public double getLastPrice() {
    if (this.getPrices().isEmpty() == false) {
      return this.getPrices().get(this.getPrices().size()-1);
    } else {
      return 0.0;
    }
  }

  public double getSecondPrice() {
    if (this.getPrices().size() >= 2) {
      return this.getPrices().get(this.getPrices().size()-2);
    } else {
      return 0.0;
    }
  }

  private void checkAmazon(Document doc) {
    this.name = doc.select("span#productTitle").first().text();
    String imageUrl = doc.select("img[data-old-hires]").first().attr("src");
    this.image = imageUrl;

    int dpIndex = this.url.indexOf("dp/", 0);
    String productId = this.url.substring(dpIndex+3, dpIndex+13);

    String newUrl = "https://www.amazon.com.tr/dp/" + productId;
    this.url = newUrl;
  }

  private void checkHepsiBurada(Document doc) {
    this.name = doc.selectFirst("h1.product-name#product-name").text();

    String imageUrl = doc.selectFirst("img.product-image").attr("src");
    this.image = imageUrl;
  }

  private void checkTrendyol() throws Exception {
    String html = fetchHtml(getUrl());
    String imageUrl = findImageUrl(html);

    this.image = imageUrl;
    this.name = findProductName(html);
  }

  private static String fetchHtml(String url) throws Exception {
    URL obj = new URL(url);
    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
    con.setRequestMethod("GET");
    con.setRequestProperty("User-Agent", "Mozilla/5.0");
    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    String inputLine;
    StringBuilder response = new StringBuilder();
    while ((inputLine = in.readLine()) != null) {
      response.append(inputLine);
    }
    in.close();
    return response.toString();
  }

  private static String findImageUrl(String html) {
    String startMarker = "https://cdn.dsmcdn.com/ty";
    String endMarker = ".jpg";
    int startIndex = html.indexOf(startMarker);
    if (startIndex < 0) {
      return null;
    }
    int endIndex = html.indexOf(endMarker, startIndex + startMarker.length());
    if (endIndex < 0) {
      return null;
    }
    return html.substring(startIndex, endIndex + endMarker.length());
  }

  private static String findProductName(String html) {
    String startMarker = "<h3 class=\"detail-name\">";
    String endMarker = "</h3>";
    int startIndex = html.indexOf(startMarker);
    if (startIndex < 0) {
      return null;
    }
    int endIndex = html.indexOf(endMarker, startIndex + startMarker.length());
    if (endIndex < 0) {
      return null;
    }
    String productName = html.substring(startIndex + startMarker.length(), endIndex);
    return productName.trim();
  }

  private void extractData() throws Exception {
    if (this.url.contains("amazon.com.tr")) {
      Document doc = Jsoup.connect(this.url).userAgent("Mozilla/5.0 (iPad; CPU OS 12_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148").get();
      checkAmazon(doc);
    } else if (this.url.contains("hepsiburada.com")) {
      Document doc = Jsoup.connect(this.url).get();
      checkHepsiBurada(doc);
    } else if (this.url.contains("trendyol.com")) {
      checkTrendyol();
    } else {
      throw new IOException("Product must be in amazon.com.tr, hepsiburada.com or trendyol.com");
    }
  }
}