package price_tracker;

import java.net.URL;
import org.jsoup.Jsoup;
import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedReader;
import org.jsoup.nodes.Document;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class Product {
  private String url;
  private String name;
  private String image;
  private ArrayList<Double> prices;
  
  public Product(String url) throws Exception {
    this.url = url;
    this.prices = new ArrayList<>();
    extractData();
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

  public void addPrice(double price) {
    this.prices.add(price);
  }

  private void checkAmazon(Document doc) {
    this.name = doc.select("span#productTitle").first().text();

    String imageUrl = doc.select("img[data-old-hires]").first().attr("src");
    this.image = imageUrl;

    int dpIndex = this.url.indexOf("dp/", 0);
    String productId = this.url.substring(dpIndex+3, dpIndex+13);

    String newUrl = "https://www.amazon.com.tr/dp/" + productId;
    this.url = newUrl;
    
    double price = Double.parseDouble(doc.select("span.a-price-whole").first().text().replaceAll("[,.]", ""));
    
    this.prices.add(price);
  }

  private void checkHepsiBurada(Document doc) {
    this.name = doc.selectFirst("h1.product-name#product-name").text();

    String imageUrl = doc.selectFirst("img.product-image").attr("src");
    this.image = imageUrl;

    double price = Double.parseDouble(doc.selectFirst("span[data-bind*=currentPriceBeforePoint]").text().replace(",", "."));
    this.prices.add(price);
  }

  private void checkTrendyol() throws Exception {
    String html = fetchHtml(getUrl());
    String imageUrl = findImageUrl(html);
    
    this.image = imageUrl;
    this.name = findProductName(html);

    this.prices.add(findPrice(html));
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

  private static Double findPrice(String html) {
    String startMarker = "<span class=\"prc-dsc\">";
    String endMarker = "</span>";
    int startIndex = html.indexOf(startMarker);
    if (startIndex < 0) {
      return null;
    }
    int endIndex = html.indexOf(endMarker, startIndex + startMarker.length());
    if (endIndex < 0) {
      return null;
    }
    String priceString = html.substring(startIndex + startMarker.length(), endIndex);
    priceString = priceString.trim().replace(",", ".").replace("TL", "");
    try {
      Double price = Double.parseDouble(priceString);
      return price;
    } catch (NumberFormatException e) {
      return null;
    }
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