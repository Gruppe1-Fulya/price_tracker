package com.price_tracker.server.service;

import com.price_tracker.server.repository.ProductRepo;
import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import com.price_tracker.server.entity.Price;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.price_tracker.server.entity.Product;
import com.price_tracker.server.repository.PriceRepo;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class PriceService {
  private final PriceRepo priceRepo;
  private final ProductRepo productRepo;
  private final AlarmService alarmService;

  @Autowired
  public  PriceService(PriceRepo priceRepo,
                       ProductRepo productRepo,
                       AlarmService alarmService) {
    this.priceRepo = priceRepo;
    this.productRepo = productRepo;
    this.alarmService = alarmService;
  }

  public Price findByProductId(int id) {
    return priceRepo.findByProductId(id);
  }

  public double getPrice(Product product) throws IOException {
    String url = product.getUrl();

    if (url.contains("amazon.com.tr")) {
      Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (iPad; CPU OS 12_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148").get();
      return checkAmazon(doc);
    } else if (url.contains("hepsiburada.com")) {
      Document doc = Jsoup.connect(url).get();
      return checkHepsiBurada(doc);
    } else if (url.contains("trendyol.com")) {
      return checkTrendyol(url);
    }
    return 0.0;
  }

  private double checkAmazon(Document doc) {
    return Double.parseDouble(doc.select("span.a-price-whole").first().text().replaceAll("[,.]", ""));
  }

  private double checkHepsiBurada(Document doc) {
    return Double.parseDouble(doc.selectFirst("span[data-bind*=currentPriceBeforePoint]").text().replace(".", ""));
  }

  private double checkTrendyol(String url) throws IOException {
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
    String html = response.toString();

    String startMarker = "<span class=\"prc-dsc\">";
    String endMarker = "</span>";

    int startIndex = html.indexOf(startMarker);
    int endIndex = html.indexOf(endMarker, startIndex + startMarker.length());

    String priceString = html.substring(startIndex + startMarker.length(), endIndex);
    priceString = priceString.trim().replace(",", ".").replace("TL", "");
    Double price = Double.parseDouble(priceString);
    return price;
  }

  @Scheduled(fixedRate = 60 * 60 * 1000) // run every 1 hour
  public void updatePrices() throws IOException {
    List<Product> products = getProductsWithPrices();

    for (Product product : products) {
      Price currentPrice = priceRepo.findTopByProductIdOrderByDateDesc(product.getId());
      double newPrice = getPrice(product);
      if (currentPrice == null ) {
        // create a new price entity if there is no data for the product
        currentPrice = new Price();
        currentPrice.setDate(LocalDate.now());
        currentPrice.setProduct(product);
        currentPrice.setPrice(newPrice);
        priceRepo.save(currentPrice);
      } else if (currentPrice != null && currentPrice.getPrice() != newPrice && currentPrice.getDate().isEqual(LocalDate.now())) {
        // if price has changed but date is same
        currentPrice.setDate(LocalDate.now());
        currentPrice.setProduct(product);
        currentPrice.setPrice(newPrice);
        priceRepo.save(currentPrice);
        alarmService.checkAllAlarmsForProduct(product.getId(), newPrice, currentPrice.getPrice());
      } else if (currentPrice != null && currentPrice.getPrice() != newPrice && currentPrice.getDate().isBefore(LocalDate.now())) {
        // if price has changed and date is not same
        currentPrice = new Price();
        currentPrice.setDate(LocalDate.now());
        currentPrice.setProduct(product);
        currentPrice.setPrice(newPrice);
        priceRepo.save(currentPrice);
        alarmService.checkAllAlarmsForProduct(product.getId(), newPrice, currentPrice.getPrice());
      } else if (currentPrice.getPrice() == newPrice && currentPrice.getDate().isBefore(LocalDate.now())) {
        // add a new day even though the price hasn't changed
        currentPrice = new Price();
        currentPrice.setDate(LocalDate.now());
        currentPrice.setProduct(product);
        currentPrice.setPrice(newPrice);
        priceRepo.save(currentPrice);
      }
    }
  }

  private List<Product> getProductsWithPrices() {
    return (ArrayList) productRepo.findAll();
  }

  public List<Price> getPricesForLast7Days(int productId) {
    LocalDate today = LocalDate.now();
    LocalDate lastWeek = today.minusDays(7);

    List<Price> prices = priceRepo.findByProductIdAndDateBetweenOrderByDateDesc(productId, lastWeek, today);

    return prices;
  }
}
