package com.price_tracker.client;

import java.net.*;
import java.util.List;
import javafx.fxml.FXML;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.control.*;
import javafx.scene.chart.LineChart;
import com.price_tracker.client.objects.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.price_tracker.client.objects.Product;
import com.price_tracker.client.objects.Requests;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

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
  private TextField priceTextField;
  @FXML
  private Label conditionLabel;
  @FXML
  private Label setOnLabel;
  @FXML
  private Label triggeredOnLabel;
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

  private int currentPage = 0;
  private int lastPage = 0;

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

        System.out.println(url);
      } catch (MalformedURLException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      productWarningLabel.setVisible(true);
    }

    initialize();
  }

  @FXML
  public void deleteProduct1() {
    System.out.println("deleteProduct1");
  }

  @FXML
  public void nextButtonClick() {
    currentPage++;
    if (currentPage != 0) {
      previousButton.setVisible(true);
    } else {
      previousButton.setVisible(false);
    }

    if (currentPage == lastPage) {
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

  @FXML
  public void handleSetAlarmTextFieldEnter() {
    System.out.println("handleSetAlarmTextFieldEnter");
  }

  @FXML
  public void deleteAlarm() {
    System.out.println("deleteAlarm");
  }

  private void loadWatchList(String json) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode rootNode = objectMapper.readTree(json);

    for (JsonNode node : rootNode) {
      JsonNode productNode = node.get("product");
      String url = productNode.get("url").asText();
      String name = productNode.get("name").asText();
      String image = productNode.get("image").asText();
      Product product = new Product(url, name, image);
      watchList.add(product);

      JsonNode alarmNode = node.get("alarm");
      if (alarmNode != null && !alarmNode.isNull()) {
        int alarmId = alarmNode.get("id").asInt();
        product.setAlarm_id(alarmId);
      }
    }
    lastPage = (watchList.size() / 4) - 1;
    int remainder = watchList.size() % 4;

    if (remainder > 0) {
      lastPage += 1;
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
      if (name.length() > 24) {
        name = name.substring(0, 21) + "...";
      }

      if (i == 0) {
        productPane1.setVisible(true);
        productNameLabel1.setText(name);
        platformLabel1.setText(platform);
        productSmallImageView1.setImage(productImage);
      } else if (i == 1) {
        productPane2.setVisible(true);
        productNameLabel2.setText(name);
        platformLabel2.setText(platform);
        productSmallImageView2.setImage(productImage);
      } else if (i == 2) {
        productPane3.setVisible(true);
        productNameLabel3.setText(name);
        platformLabel3.setText(platform);
        productSmallImageView3.setImage(productImage);
      } else if (i == 3) {
        productPane4.setVisible(true);
        productNameLabel4.setText(name);
        platformLabel4.setText(platform);
        productSmallImageView4.setImage(productImage);
      }
    }
  }

  private String getPlatform(String url) {
    if (url.contains("trendyol.com")) {
      return "Trendyol";
    } else if (url.contains("amazon.com.tr")) {
      return "Amazon";
    } else if (url.contains("hepsiburada.com")) {
      return  "Hepsiburada";
    } else {
      return  "Unknown";
    }
  }
}