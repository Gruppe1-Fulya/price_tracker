package com.price_tracker.server.service;

import java.io.*;
import java.net.URL;
import java.util.List;

import jakarta.annotation.PostConstruct;
import org.jsoup.Jsoup;
import java.time.LocalDate;
import java.util.ArrayList;
import org.jsoup.nodes.Document;
import java.net.HttpURLConnection;
import com.price_tracker.server.entity.Price;
import org.springframework.stereotype.Service;
import com.price_tracker.server.entity.Product;
import com.price_tracker.server.repository.PriceRepo;
import com.price_tracker.server.repository.ProductRepo;
import org.springframework.scheduling.annotation.Scheduled;
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

  @PostConstruct
  public void init() throws IOException {
    // Start the service here
    updatePrices();
  }
  public Price findByProductId(int id) {
    return priceRepo.findByProductId(id);
  }

  public double getPrice(Product product) throws IOException {
    String url = product.getUrl();

    if (url.contains("amazon.com.tr")) {
      String code = url.split("dp/")[1];
      return checkAmazon("https://www.amazon.com.tr/s?k=" + code);
    } else if (url.contains("hepsiburada.com")) {
      Document doc = Jsoup.connect(url).get();
      return checkHepsiBurada(doc);
    } else if (url.contains("trendyol.com")) {
      return checkTrendyol(url);
    }
    return 0.0;
  }

  private double checkAmazon(String url) {
    try {
      URL amazonUrl = new URL(url);
      BufferedReader in = new BufferedReader(new InputStreamReader(amazonUrl.openStream()));

      String line;
      while ((line = in.readLine()) != null) {
        int start = line.indexOf("<span class=\"a-price-whole\">");
        if (start != -1) {
          int end = line.indexOf("<", start + 1);
          if (end != -1) {
            String priceString = line.substring(start + "<span class=\"a-price-whole\">".length(), end);
            double price = Double.parseDouble(priceString.replace(".",""));
            return price;
          }
        }
      }

      in.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return -1.0; // price not found
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
    priceString = priceString.trim().replace(".", "").replace("TL", "").replace(",", ".");
    Double price = Double.parseDouble(priceString);
    return price;
  }

  @Scheduled(fixedRate = 60 * 60 * 1000) // run every 1 hour
  public void updatePrices() throws IOException {
    List<Product> products = getProductsWithPrices();

    for (Product product : products) {
      priceOps(product);
    }
  }

  public void priceOps(Product product) throws IOException {
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
      alarmService.checkAllAlarmsForProduct(product.getId(), newPrice, currentPrice.getPrice());
      currentPrice.setDate(LocalDate.now());
      currentPrice.setProduct(product);
      currentPrice.setPrice(newPrice);
      priceRepo.save(currentPrice);
    } else if (currentPrice != null && currentPrice.getPrice() != newPrice && currentPrice.getDate().isBefore(LocalDate.now())) {
      // if price has changed and date is not same
      alarmService.checkAllAlarmsForProduct(product.getId(), newPrice, currentPrice.getPrice());
      currentPrice = new Price();
      currentPrice.setDate(LocalDate.now());
      currentPrice.setProduct(product);
      currentPrice.setPrice(newPrice);
      priceRepo.save(currentPrice);
    } else if (currentPrice.getPrice() == newPrice && currentPrice.getDate().isBefore(LocalDate.now())) {
      // add a new day even though the price hasn't changed
      currentPrice = new Price();
      currentPrice.setDate(LocalDate.now());
      currentPrice.setProduct(product);
      currentPrice.setPrice(newPrice);
      priceRepo.save(currentPrice);
    }
  }

  public void checkIndividualPrice(int product_id) throws IOException {
    Product product = productRepo.findById(product_id);
    priceOps(product);
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
