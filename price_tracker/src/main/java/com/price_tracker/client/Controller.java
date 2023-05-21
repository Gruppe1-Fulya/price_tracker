package com.price_tracker.client;

import java.net.*;
import java.time.LocalDate;
import java.util.List;
import javafx.fxml.FXML;
import java.awt.Desktop;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.chart.LineChart;
import com.price_tracker.client.objects.User;
import com.price_tracker.client.objects.Alarm;
import com.fasterxml.jackson.databind.JsonNode;
import com.price_tracker.client.objects.Product;
import com.price_tracker.client.objects.Requests;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

public class Controller {
  @FXML
  private Label welcomeLabel;
  @FXML
  private TextField addProductTextField;
  @FXML
  private ListView watchlistListView;
  @FXML
  private LineChart productPriceLineChart;
  @FXML
  private Label productNameLabel;
  @FXML
  private Label productLinkLabel;
  @FXML
  private Label productPriceLabel;
  @FXML
  private ComboBox conditionComboBox;
  @FXML
  private ImageView productImageView;
  @FXML
  private TextField priceTextField;
  @FXML
  private Label conditionLabel;
  @FXML
  private Label setOnLabel;
  @FXML
  private Label triggeredOnLabel;
  @FXML
  private ImageView openBrowserImageView;
  @FXML
  private Label emptyWLWarningLabel;
  @FXML
  private Label productWarningLabel;
  @FXML
  private Label watchListLabel;
  @FXML
  private Label watchListLabel1;
  @FXML
  private Button previousButton;
  @FXML
  private Button nextButton;
  @FXML
  private Pane productPane1;
  @FXML
  private Pane productPane2;
  @FXML
  private Pane productPane3;
  @FXML
  private Pane productPane4;
  @FXML
  private Label productNameLabel1;
  @FXML
  private Label productNameLabel2;
  @FXML
  private Label productNameLabel3;
  @FXML
  private Label productNameLabel4;
  @FXML
  private Label platformLabel1;
  @FXML
  private Label platformLabel2;
  @FXML
  private Label platformLabel3;
  @FXML
  private Label platformLabel4;
  @FXML
  private ImageView productSmallImageView1;
  @FXML
  private ImageView productSmallImageView2;
  @FXML
  private ImageView productSmallImageView3;
  @FXML
  private ImageView productSmallImageView4;
  @FXML
  private Label productWLPriceLabel1;
  @FXML
  private Label productWLPriceLabel2;
  @FXML
  private Label productWLPriceLabel3;
  @FXML
  private Label productWLPriceLabel4;
  @FXML
  private Label preisChangeLabel1;
  @FXML
  private Label preisChangeLabel2;
  @FXML
  private Label preisChangeLabel3;
  @FXML
  private Label preisChangeLabel4;
  @FXML
  private Pane priceAlarmPane;

  private int currentPage = 0;
  private int lastPage = 0;
  private String activeURL;
  private Product activeProduct;
  private int activePane;

  private List<Product> watchList = new ArrayList<>();

  @FXML
  private void initialize() throws JsonProcessingException {
    conditionComboBox.getItems().addAll("Beliebige Änderung", "Unter dem Zielwert", "Über dem Zielwert", "Gleich");
    welcomeLabel.setText("Willkommen, " + User.name);

    String wlResponse = Requests.sendLoadWLRequest(User.id);

    if (wlResponse.equals("empty")) {
      emptyWLWarningLabel.setVisible(true);
      watchListLabel1.setVisible(true);
      watchListLabel.setVisible(false);
    } else {
      emptyWLWarningLabel.setVisible(false);
      watchListLabel1.setVisible(false);
      watchListLabel.setVisible(true);
      loadWatchList(wlResponse);
      productPaneClick1();
    }
  }

  @FXML
  private void handleAddProductTextFieldEnter() throws Exception {
    String text = addProductTextField.getText();
    addProductTextField.clear();

    if (text.contains("hepsiburada.com") || text.contains("trendyol.com") || text.contains("amazon.com.tr")) {
      productWarningLabel.setVisible(false);
      try {
        Product product = new Product(text);

        String name = product.getName();
        String url = product.getUrl();
        String image = product.getImage();

        Requests.sendAddProductRequest(User.email, name, url, image);
      } catch (MalformedURLException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      productWarningLabel.setVisible(true);
    }

    int lastPageTemp = lastPage;
    initialize();
  }

  @FXML
  public void deleteProduct1() throws JsonProcessingException {
    int watchlistId = watchList.get(((currentPage) * 4)).getWatchlist_id();
    Requests.sendDeleteProductRequest(watchlistId);
    int lastPageTemp = lastPage;
    watchList.remove(watchList.get(((currentPage) * 4)));
    if (lastPageTemp > checkLastPage() && currentPage != 0) previousButtonClick();
    initialize();
  }

  @FXML
  public void deleteProduct2() throws JsonProcessingException {
    int watchlistId = watchList.get(((currentPage) * 4) + 1).getWatchlist_id();
    Requests.sendDeleteProductRequest(watchlistId);
    int lastPageTemp = lastPage;
    watchList.remove(watchList.get(((currentPage) * 4) + 1));
    if (lastPageTemp > checkLastPage() && currentPage != 0) previousButtonClick();
    initialize();
  }

  @FXML
  public void deleteProduct3() throws JsonProcessingException {
    int watchlistId = watchList.get(((currentPage) * 4) + 2).getWatchlist_id();
    Requests.sendDeleteProductRequest(watchlistId);
    int lastPageTemp = lastPage;
    watchList.remove(watchList.get(((currentPage) * 4) + 2));
    if (lastPageTemp > checkLastPage() && currentPage != 0) previousButtonClick();
    initialize();
  }

  @FXML
  public void deleteProduct4() throws JsonProcessingException {
    int watchlistId = watchList.get(((currentPage) * 4) + 3).getWatchlist_id();
    Requests.sendDeleteProductRequest(watchlistId);
    int lastPageTemp = lastPage;
    watchList.remove(watchList.get(((currentPage) * 4) + 3));
    if (lastPageTemp > checkLastPage() && currentPage != 0) previousButtonClick();
    initialize();
  }

  @FXML
  public void productPaneClick1() throws JsonProcessingException {
    if (watchList.size() != currentPage * 4) {
      productPane1.setStyle("-fx-border-color: black; -fx-background-color: #DEDEDE; -fx-border-radius: 7; -fx-border-width: 0 1 1 1;");
      productPane2.setStyle("-fx-border-color: black; -fx-background-color: #F1F1F1; -fx-border-radius: 7; -fx-border-width: 0 1 1 1;");
      productPane3.setStyle("-fx-border-color: black; -fx-background-color: #F1F1F1; -fx-border-radius: 7; -fx-border-width: 0 1 1 1;");
      productPane4.setStyle("-fx-border-color: black; -fx-background-color: #F1F1F1; -fx-border-radius: 7; -fx-border-width: 0 1 1 1;");
      Product product = watchList.get(((currentPage) * 4));
      activeProduct = product;
      Image productImage = new Image(product.getImage());
      productImageView.setImage(productImage);
      String name = product.getName();
      String url = product.getUrl();
      activeURL = url;
      String price = Double.toString(product.getLastPrice());
      productPriceLabel.setText(price + "₺");

      if (product.getAlarm_id() != -1) {
        priceAlarmPane.setVisible(true);
        String json = Requests.sendLoadAlarmRequest(product.getAlarm_id());
        Alarm alarm = loadAlarm(json);
        conditionLabel.setText(createConditionSentence(alarm.getCondition(), alarm.getTargetPrice()));
        setOnLabel.setText(alarm.getDateCreated());
        if (alarm.getDateTriggered() != null) {
          triggeredOnLabel.setText(alarm.getDateTriggered());
        } else {
          triggeredOnLabel.setText("-");
        }
      } else {
        priceAlarmPane.setVisible(false);
      }

      if (url.length() > 53) {
        url = url.substring(0, 50) + "...";
      }

      if (name.length() > 53) {
        name = name.substring(0, 50) + "...";
      }

      productLinkLabel.setText(url);
      productNameLabel.setText(toTitleCase(name));

      activePane = 1;
    }
  }

  @FXML
  public void productPaneClick2() throws JsonProcessingException {
    if (watchList.size() != (currentPage * 4) + 1) {
      productPane1.setStyle("-fx-border-color: black; -fx-background-color: #F1F1F1; -fx-border-radius: 7; -fx-border-width: 0 1 1 1;");
      productPane2.setStyle("-fx-border-color: black; -fx-background-color: #DEDEDE; -fx-border-radius: 7; -fx-border-width: 0 1 1 1;");
      productPane3.setStyle("-fx-border-color: black; -fx-background-color: #F1F1F1; -fx-border-radius: 7; -fx-border-width: 0 1 1 1;");
      productPane4.setStyle("-fx-border-color: black; -fx-background-color: #F1F1F1; -fx-border-radius: 7; -fx-border-width: 0 1 1 1;");
      Product product = watchList.get(((currentPage) * 4) + 1);
      activeProduct = product;
      Image productImage = new Image(product.getImage());
      productImageView.setImage(productImage);
      String name = product.getName();
      String url = product.getUrl();
      activeURL = url;
      String price = Double.toString(product.getLastPrice());
      productPriceLabel.setText(price + "₺");

      if (product.getAlarm_id() != -1) {
        priceAlarmPane.setVisible(true);
        String json = Requests.sendLoadAlarmRequest(product.getAlarm_id());
        Alarm alarm = loadAlarm(json);
        conditionLabel.setText(createConditionSentence(alarm.getCondition(), alarm.getTargetPrice()));
        setOnLabel.setText(alarm.getDateCreated());
        if (alarm.getDateTriggered() != null) {
          triggeredOnLabel.setText(alarm.getDateTriggered());
        } else {
          triggeredOnLabel.setText("-");
        }
      } else {
        priceAlarmPane.setVisible(false);
      }

      if (url.length() > 53) {
        url = url.substring(0, 50) + "...";
      }

      if (name.length() > 53) {
        name = name.substring(0, 50) + "...";
      }

      productLinkLabel.setText(url);
      productNameLabel.setText(toTitleCase(name));

      activePane = 2;
    }
  }

  @FXML
  public void productPaneClick3() throws JsonProcessingException {
    if (watchList.size() != (currentPage * 4) + 2) {
      productPane1.setStyle("-fx-border-color: black; -fx-background-color: #F1F1F1; -fx-border-radius: 7; -fx-border-width: 0 1 1 1;");
      productPane2.setStyle("-fx-border-color: black; -fx-background-color: #F1F1F1; -fx-border-radius: 7; -fx-border-width: 0 1 1 1;");
      productPane3.setStyle("-fx-border-color: black; -fx-background-color: #DEDEDE; -fx-border-radius: 7; -fx-border-width: 0 1 1 1;");
      productPane4.setStyle("-fx-border-color: black; -fx-background-color: #F1F1F1; -fx-border-radius: 7; -fx-border-width: 0 1 1 1;");
      Product product = watchList.get(((currentPage) * 4) + 2);
      activeProduct = product;
      Image productImage = new Image(product.getImage());
      productImageView.setImage(productImage);
      String name = product.getName();
      String url = product.getUrl();
      activeURL = url;
      String price = Double.toString(product.getLastPrice());
      productPriceLabel.setText(price + "₺");

      if (product.getAlarm_id() != -1) {
        priceAlarmPane.setVisible(true);
        String json = Requests.sendLoadAlarmRequest(product.getAlarm_id());
        Alarm alarm = loadAlarm(json);
        conditionLabel.setText(createConditionSentence(alarm.getCondition(), alarm.getTargetPrice()));
        setOnLabel.setText(alarm.getDateCreated());
        if (alarm.getDateTriggered() != null) {
          triggeredOnLabel.setText(alarm.getDateTriggered());
        } else {
          triggeredOnLabel.setText("-");
        }
      } else {
        priceAlarmPane.setVisible(false);
      }

      if (url.length() > 53) {
        url = url.substring(0, 50) + "...";
      }

      if (name.length() > 53) {
        name = name.substring(0, 50) + "...";
      }

      productLinkLabel.setText(url);
      productNameLabel.setText(toTitleCase(name));

      activePane = 3;
    }
  }

  @FXML
  public void productPaneClick4() throws JsonProcessingException {
    if (watchList.size() != (currentPage * 4) + 3) {
      productPane1.setStyle("-fx-border-color: black; -fx-background-color: #F1F1F1; -fx-border-radius: 7; -fx-border-width: 0 1 1 1;");
      productPane2.setStyle("-fx-border-color: black; -fx-background-color: #F1F1F1; -fx-border-radius: 7; -fx-border-width: 0 1 1 1;");
      productPane3.setStyle("-fx-border-color: black; -fx-background-color: #F1F1F1; -fx-border-radius: 7; -fx-border-width: 0 1 1 1;");
      productPane4.setStyle("-fx-border-color: black; -fx-background-color: #DEDEDE; -fx-border-radius: 7; -fx-border-width: 0 1 1 1;");
      Product product = watchList.get(((currentPage) * 4) + 3);
      activeProduct = product;
      Image productImage = new Image(product.getImage());
      productImageView.setImage(productImage);
      String name = product.getName();
      String url = product.getUrl();
      activeURL = url;
      String price = Double.toString(product.getLastPrice());
      productPriceLabel.setText(price + "₺");

      if (product.getAlarm_id() != -1) {
        priceAlarmPane.setVisible(true);
        String json = Requests.sendLoadAlarmRequest(product.getAlarm_id());
        Alarm alarm = loadAlarm(json);
        conditionLabel.setText(createConditionSentence(alarm.getCondition(), alarm.getTargetPrice()));
        setOnLabel.setText(alarm.getDateCreated());
        if (alarm.getDateTriggered() != null) {
          triggeredOnLabel.setText(alarm.getDateTriggered());
        } else {
          triggeredOnLabel.setText("-");
        }
      } else {
        priceAlarmPane.setVisible(false);
      }

      if (url.length() > 53) {
        url = url.substring(0, 50) + "...";
      }

      if (name.length() > 53) {
        name = name.substring(0, 50) + "...";
      }

      productLinkLabel.setText(url);
      productNameLabel.setText(toTitleCase(name));

      activePane = 4;
    }
  }

  private Alarm loadAlarm(String json) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    Alarm alarm = mapper.readValue(json, Alarm.class);

    String condition = alarm.getCondition();
    Double targetPrice = alarm.getTargetPrice();
    String dateCreated = alarm.getDateCreated();
    String dateTriggered = alarm.getDateTriggered();

    return alarm;
  }

  @FXML
  public void openBrowserClicked() {
    if (Desktop.isDesktopSupported()) {
      Desktop desktop = Desktop.getDesktop();
      try {
        desktop.browse(new URI(activeURL));
      } catch (IOException | URISyntaxException e) {
        e.printStackTrace();
      }
    }
  }

  @FXML
  public void nextButtonClick() {
    currentPage++;
    if (currentPage != 0) {
      previousButton.setVisible(true);
    } else {
      previousButton.setVisible(false);
    }

    if (currentPage >= lastPage) {
      nextButton.setVisible(false);
    }
    showProducts();
  }

  @FXML
  public void previousButtonClick() {
    currentPage--;
    if (currentPage == 0) {
      previousButton.setVisible(false);
    } else {
      previousButton.setVisible(true);
    }
    if (currentPage == lastPage) {
      nextButton.setVisible(false);
    } else {
      nextButton.setVisible(true);
    }
    showProducts();
  }

  public String createConditionSentence(String cond, double target) {
    if (cond.equals("ANY_CHANGE")) return "Falls sich etwas ändert";
    if (cond.equals("BELOW_TARGET")) return "Wenn der Preis unter " + target + " ₺ fällt";
    if (cond.equals("ABOVE_TARGET")) return "Wenn der Preis über " + target + " ₺ liegt";
    if (cond.equals("EQUALS_TARGET")) return "Wenn der Preis " + target + " ₺ beträgt";
    return "";
  }

  @FXML
  public void handleSetAlarmTextFieldEnter() throws JsonProcessingException {
    String condition;
    if (conditionComboBox.getValue() == "Beliebige Änderung") {
      condition = "ANY_CHANGE";
    } else if (conditionComboBox.getValue() == "Unter dem Zielwert") {
      condition = "BELOW_TARGET";
    } else if (conditionComboBox.getValue() == "Über dem Zielwert") {
      condition = "ABOVE_TARGET";
    } else {
      condition = "EQUALS_TARGET";
    }
    int alarmId = Integer.parseInt(Requests.sendSetAlarmRequest(activeProduct.getId(), activeProduct.getWatchlist_id(), LocalDate.now().toString(), condition, Double.parseDouble(priceTextField.getText())));
    priceTextField.clear();
    activeProduct.setAlarm_id(alarmId);

    if (activePane == 1) {
      productPaneClick1();
    } else if (activePane == 2) {
      productPaneClick2();
    } else if (activePane == 3) {
      productPaneClick3();
    } else if (activePane == 4) {
      productPaneClick4();
    }
  }

  @FXML
  public void deleteAlarm() throws JsonProcessingException {
    String response = Requests.sendRemoveAlarmRequest(activeProduct.getWatchlist_id());
    if (response.equals("Alarm deleted!")) {
      activeProduct.setAlarm_id(-1);
      priceAlarmPane.setVisible(false);
    }
  }

  private void loadWatchList(String json) throws JsonProcessingException {
    watchList.clear();
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode rootNode = objectMapper.readTree(json);

    for (JsonNode node : rootNode) {
      JsonNode productNode = node.get("product");
      int id = productNode.get("id").asInt();
      String url = productNode.get("url").asText();
      String name = productNode.get("name").asText();
      String image = productNode.get("image").asText();

      int watchlistId = node.get("id").asInt();
      Product product = new Product(id, url, name, image, watchlistId);

      JsonNode alarmNode = node.get("alarm");
      if (alarmNode != null && !alarmNode.isNull()) {
        int alarmId = alarmNode.get("id").asInt();
        product.setAlarm_id(alarmId);
      }

      setPrices(product);
      watchList.add(product);
    }

    checkLastPage();
    if (lastPage == 0) {
      nextButton.setVisible(false);
    } else if (currentPage != lastPage) {
      nextButton.setVisible(true);
    }
    showProducts();
  }

  private void showProducts() {
    int n = (watchList.size() - (currentPage) * 4) >= 4 ? 4 : (watchList.size() - (currentPage) * 4);

    productPane1.setVisible(false);
    productPane2.setVisible(false);
    productPane3.setVisible(false);
    productPane4.setVisible(false);

    for (int i = 0; i < n; i++) {
      Product product = watchList.get(((currentPage) * 4) + i);
      String platform = getPlatform(product.getUrl());
      Image productImage = new Image(product.getImage());
      String name = product.getName();
      name = toTitleCase(name);
      if (name.length() > 24) {
        name = name.substring(0, 21) + "...";
      }

      if (i == 0) {
        productPane1.setVisible(true);
        productNameLabel1.setText(name);
        platformLabel1.setText(platform);
        productSmallImageView1.setImage(productImage);
        productWLPriceLabel1.setText(Double.toString(product.getLastPrice()) + "₺");
        if (product.getPrices().size() < 2) {
          preisChangeLabel1.setStyle(("-fx-text-fill: #342e2e;"));
          preisChangeLabel1.setText("% -");
        } else {
          if (product.getLastPrice() > product.getSecondPrice()) {
            preisChangeLabel1.setStyle(("-fx-text-fill: #a80707;"));
            preisChangeLabel1.setText("% " + String.format("%.2f", ((product.getLastPrice() - product.getSecondPrice()) / product.getSecondPrice()) * 100) + " ▲");
          } else if (product.getLastPrice() < product.getSecondPrice()) {
            preisChangeLabel1.setStyle(("-fx-text-fill: #31590d;"));
            preisChangeLabel1.setText("% " + String.format("%.2f", ((product.getLastPrice() - product.getSecondPrice()) / product.getSecondPrice()) * 100) + " ▼");
          } else {
            preisChangeLabel1.setStyle(("-fx-text-fill: #342e2e;"));
            preisChangeLabel1.setText("% -");
          }
        }
      } else if (i == 1) {
        productPane2.setVisible(true);
        productNameLabel2.setText(name);
        platformLabel2.setText(platform);
        productSmallImageView2.setImage(productImage);
        productWLPriceLabel2.setText(Double.toString(product.getLastPrice()) + "₺");
        if (product.getPrices().size() < 2) {
          preisChangeLabel2.setStyle(("-fx-text-fill: #342e2e;"));
          preisChangeLabel2.setText("% -");
        } else {
          if (product.getLastPrice() > product.getSecondPrice()) {
            preisChangeLabel2.setStyle(("-fx-text-fill: #a80707;"));
            preisChangeLabel2.setText("% " + String.format("%.2f", ((product.getLastPrice() - product.getSecondPrice()) / product.getSecondPrice()) * 100) + " ▲");
          } else if (product.getLastPrice() < product.getSecondPrice()) {
            preisChangeLabel2.setStyle(("-fx-text-fill: #31590d;"));
            preisChangeLabel2.setText("% " + String.format("%.2f", ((product.getLastPrice() - product.getSecondPrice()) / product.getSecondPrice()) * 100) + " ▼");
          } else {
            preisChangeLabel2.setStyle(("-fx-text-fill: #342e2e;"));
            preisChangeLabel2.setText("% -");
          }
        }
      } else if (i == 2) {
        productPane3.setVisible(true);
        productNameLabel3.setText(name);
        platformLabel3.setText(platform);
        productSmallImageView3.setImage(productImage);
        productWLPriceLabel3.setText(Double.toString(product.getLastPrice()) + "₺");
        if (product.getPrices().size() < 2) {
          preisChangeLabel3.setStyle(("-fx-text-fill: #342e2e;"));
          preisChangeLabel3.setText("% -");
        } else {
          if (product.getLastPrice() > product.getSecondPrice()) {
            preisChangeLabel3.setStyle(("-fx-text-fill: #a80707;"));
            preisChangeLabel3.setText("% " + String.format("%.2f", ((product.getLastPrice() - product.getSecondPrice()) / product.getSecondPrice()) * 100) + " ▲");
          } else if (product.getLastPrice() < product.getSecondPrice()) {
            preisChangeLabel3.setStyle(("-fx-text-fill: #31590d;"));
            preisChangeLabel3.setText("% " + String.format("%.2f", ((product.getLastPrice() - product.getSecondPrice()) / product.getSecondPrice()) * 100) + " ▼");
          } else {
            preisChangeLabel3.setStyle(("-fx-text-fill: #342e2e;"));
            preisChangeLabel3.setText("% -");
          }
        }
      } else if (i == 3) {
        productPane4.setVisible(true);
        productNameLabel4.setText(name);
        platformLabel4.setText(platform);
        productSmallImageView4.setImage(productImage);
        productWLPriceLabel4.setText(Double.toString(product.getLastPrice()) + "₺");
        if (product.getPrices().size() < 2) {
          preisChangeLabel4.setStyle(("-fx-text-fill: #342e2e;"));
          preisChangeLabel4.setText("% -");
        } else {
          if (product.getLastPrice() > product.getSecondPrice()) {
            preisChangeLabel4.setStyle(("-fx-text-fill: #a80707;"));
            preisChangeLabel4.setText("% " + String.format("%.2f", ((product.getLastPrice() - product.getSecondPrice()) / product.getSecondPrice()) * 100) + " ▲");
          } else if (product.getLastPrice() < product.getSecondPrice()) {
            preisChangeLabel4.setStyle(("-fx-text-fill: #31590d;"));
            preisChangeLabel4.setText("% " + String.format("%.2f", ((product.getLastPrice() - product.getSecondPrice()) / product.getSecondPrice()) * 100) + " ▼");
          } else {
            preisChangeLabel4.setStyle(("-fx-text-fill: #342e2e;"));
            preisChangeLabel4.setText("% -");
          }
        }
      }
    }
  }

  private String getPlatform(String url) {
    if (url.contains("trendyol.com")) {
      return "Trendyol";
    } else if (url.contains("amazon.com.tr")) {
      return "Amazon";
    } else if (url.contains("hepsiburada.com")) {
      return "Hepsiburada";
    } else {
      return "Unknown";
    }
  }

  private int checkLastPage() {
    lastPage = (watchList.size() / 4) - 1;
    int remainder = watchList.size() % 4;

    if (remainder > 0) {
      lastPage += 1;
    }

    return lastPage;
  }

  public static String toTitleCase(String input) {
    String[] words = input.toLowerCase().split(" ");
    StringBuilder sb = new StringBuilder();
    for (String word : words) {
      sb.append(word.substring(0, 1).toUpperCase());
      sb.append(word.substring(1));
      sb.append(" ");
    }
    return sb.toString().trim();
  }

  public void setPrices(Product product) {
    String json = Requests.sendLoadPricesRequest(product.getId());
    ObjectMapper objectMapper = new ObjectMapper();
    List<Double> prices = new ArrayList<>();

    try {
      JsonNode rootNode = objectMapper.readTree(json);
      if (rootNode.isArray()) {
        for (JsonNode node : rootNode) {
          Double price = node.get("price").asDouble();
          prices.add(price);
        }
      }
    } catch (IOException e) {
      // Handle exception
    }

    // Add prices in ascending order
    for (Double price : prices) {
      product.addPrice(price);
    }
  }
}