package com.price_tracker.client;

import java.net.*;
import java.util.List;
import javafx.fxml.FXML;
import java.awt.Desktop;
import java.io.IOException;
import java.util.ArrayList;
import java.time.LocalDate;
import javafx.scene.chart.*;
import java.util.Collections;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.application.Platform;
import java.util.stream.Collectors;
import javafx.scene.image.ImageView;
import javafx.collections.FXCollections;
import java.time.format.DateTimeFormatter;
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
  private Pane priceAlarmPane;
  @FXML
  private Label preisChangeLabel1;
  @FXML
  private Label preisChangeLabel2;
  @FXML
  private Label preisChangeLabel3;
  @FXML
  private Label preisChangeLabel4;
  @FXML
  private ImageView deleteProductIcon1;
  @FXML
  private ImageView deleteProductIcon2;
  @FXML
  private ImageView deleteProductIcon3;
  @FXML
  private ImageView deleteProductIcon4;
  @FXML
  private ListView watchlistListView;
  @FXML
  private Label priceWarningLabel;
  @FXML
  private LineChart<String, Double> productPriceLineChart;

  private int currentPage = 0;
  private int lastPage = 0;
  private String activeURL;
  private Product activeProduct;
  private int activePane;
  private boolean isEmpty;
  private Product product1;
  private Product product2;
  private Product product3;
  private Product product4;

  private List<Product> watchList = new ArrayList<>();

  @FXML
  private void initialize() throws Exception {
    Platform.runLater(() -> watchlistListView.requestFocus());
    conditionComboBox.getItems().addAll("Beliebige Änderung", "Unter dem Zielwert", "Über dem Zielwert", "Gleich");
    welcomeLabel.setText("Willkommen, " + User.name);

    String wlResponse = Requests.sendLoadWLRequest(User.id);

    if (wlResponse.equals("empty")) {
      isEmpty = true;
      emptyWLWarningLabel.setVisible(true);
      watchListLabel1.setVisible(true);
      watchListLabel.setVisible(false);
      productPane1.setVisible(false);
      productPane2.setVisible(false);
      productPane3.setVisible(false);
      productPane4.setVisible(false);
      nextButton.setVisible(false);
      showFeaturedProducts();
      productPaneClick1();
      Platform.runLater(() -> watchlistListView.requestFocus());
    } else {
      isEmpty = false;
      emptyWLWarningLabel.setVisible(false);
      watchListLabel1.setVisible(false);
      watchListLabel.setVisible(true);
      loadWatchList(wlResponse);
      productPaneClick1();
      deleteProductIcon1.setVisible(true);
      deleteProductIcon2.setVisible(true);
      deleteProductIcon3.setVisible(true);
      deleteProductIcon4.setVisible(true);
      Platform.runLater(() -> watchlistListView.requestFocus());
    }
  }

  private void showFeaturedProducts() throws Exception {
    priceAlarmPane.setVisible(false);

    deleteProductIcon1.setVisible(false);
    deleteProductIcon2.setVisible(false);
    deleteProductIcon3.setVisible(false);
    deleteProductIcon4.setVisible(false);

    productPane1.setVisible(true);
    productPane2.setVisible(true);
    productPane3.setVisible(true);
    productPane4.setVisible(true);

    product1 = new Product("https://www.trendyol.com/vans/old-skool-unisex-ayakkabi-vn000d3hy281-p-1738026");
    product2 = new Product("https://www.hepsiburada.com/samsung-galaxy-watch-5-pro-akilli-saat-siyah-titanium-45mm-sm-r920nzkatur-samsung-turkiye-garantili-p-HBCV00002QNCNS");
    product3 = new Product("https://www.amazon.com.tr/dp/B07GS6ZB7T");
    product4 = new Product("https://www.trendyol.com/bargello/erkek-parfum-561-fresh-50-ml-edp-8691841304531-p-32332095");

    Image img1 = new Image(product1.getImage());
    Image img2 = new Image(product2.getImage());
    Image img3 = new Image(product3.getImage());
    Image img4 = new Image(product4.getImage());

    productSmallImageView1.setImage(img1);
    productSmallImageView2.setImage(img2);
    productSmallImageView3.setImage(img3);
    productSmallImageView4.setImage(img4);

    productNameLabel1.setText("Vans Old School Unis...");
    productNameLabel2.setText("Samsung Galaxy Watch...");
    productNameLabel3.setText("Logitech G G502 HERO...");
    productNameLabel4.setText("Bargello Erkek Parfü...");

    platformLabel1.setText("Trendyol");
    platformLabel2.setText("Hepsiburada");
    platformLabel3.setText("Amazon");
    platformLabel4.setText("Trendyol");

    productWLPriceLabel1.setText("1599.0");
    productWLPriceLabel2.setText("6198.0");
    productWLPriceLabel3.setText("1299.0");
    productWLPriceLabel4.setText("160.0");

    preisChangeLabel1.setStyle(("-fx-text-fill: #31590d;"));
    preisChangeLabel2.setStyle(("-fx-text-fill: #31590d;"));
    preisChangeLabel3.setStyle(("-fx-text-fill: #31590d;"));
    preisChangeLabel4.setStyle(("-fx-text-fill: #31590d;"));

    product1.addPrice(1599.0);
    product1.addPrice(1999.0);
    product1.addPrice(1999.0);
    product1.addPrice(1999.0);
    product1.addPrice(1999.0);
    product1.addPrice(2100.0);
    product1.addPrice(2100.0);

    product2.addPrice(6198.0);
    product2.addPrice(6500.0);
    product2.addPrice(6500.0);
    product2.addPrice(6400.0);
    product2.addPrice(6400.0);
    product2.addPrice(6400.0);
    product2.addPrice(6450.0);

    product3.addPrice(1299.0);
    product3.addPrice(1650.0);
    product3.addPrice(1650.0);
    product3.addPrice(1650.0);
    product3.addPrice(1720.0);
    product3.addPrice(1720.0);
    product3.addPrice(1550.0);

    product4.addPrice(160.0);
    product4.addPrice(189.0);
    product4.addPrice(195.0);
    product4.addPrice(195.0);
    product4.addPrice(195.0);
    product4.addPrice(189.0);
    product4.addPrice(200.0);

    preisChangeLabel1.setText("20.01" + " ▼");
    preisChangeLabel2.setText("4.64" + " ▼");
    preisChangeLabel3.setText("21.27" + " ▼");
    preisChangeLabel4.setText("15.34" + " ▼");
  }

  @FXML
  private void handleAddProductTextFieldEnter() throws Exception {
    String text = addProductTextField.getText();
    text = text.split("\\?")[0];
    addProductTextField.clear();

    if (text.contains("hepsiburada.com") || text.contains("trendyol.com") || text.contains("amazon.com.tr")) {
      productWarningLabel.setVisible(false);
      try {
        Product product = new Product(text);

        String name = product.getName();
        String url = product.getUrl();
        String image = product.getImage();

        String response = Requests.sendAddProductRequest(User.email, name, url, image);
        int startIndex = response.indexOf("id", response.indexOf("id") + 2) + 4;
        response = response.substring(startIndex, response.indexOf(",", startIndex));
        Requests.sendUpdatePriceRequest(Integer.parseInt(response));
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
  public void deleteProduct1() throws Exception {
    int watchlistId = watchList.get(((currentPage) * 4)).getWatchlist_id();
    Requests.sendDeleteProductRequest(watchlistId);
    int lastPageTemp = lastPage;
    watchList.remove(watchList.get(((currentPage) * 4)));
    if (lastPageTemp > checkLastPage() && currentPage != 0) previousButtonClick();
    initialize();
  }

  @FXML
  public void deleteProduct2() throws Exception {
    int watchlistId = watchList.get(((currentPage) * 4) + 1).getWatchlist_id();
    Requests.sendDeleteProductRequest(watchlistId);
    int lastPageTemp = lastPage;
    watchList.remove(watchList.get(((currentPage) * 4) + 1));
    if (lastPageTemp > checkLastPage() && currentPage != 0) previousButtonClick();
    initialize();
  }

  @FXML
  public void deleteProduct3() throws Exception {
    int watchlistId = watchList.get(((currentPage) * 4) + 2).getWatchlist_id();
    Requests.sendDeleteProductRequest(watchlistId);
    int lastPageTemp = lastPage;
    watchList.remove(watchList.get(((currentPage) * 4) + 2));
    if (lastPageTemp > checkLastPage() && currentPage != 0) previousButtonClick();
    initialize();
  }

  @FXML
  public void deleteProduct4() throws Exception {
    int watchlistId = watchList.get(((currentPage) * 4) + 3).getWatchlist_id();
    Requests.sendDeleteProductRequest(watchlistId);
    int lastPageTemp = lastPage;
    watchList.remove(watchList.get(((currentPage) * 4) + 3));
    if (lastPageTemp > checkLastPage() && currentPage != 0) previousButtonClick();
    initialize();
  }

  @FXML
  public void productPaneClick1() throws JsonProcessingException {
    if (watchList.size() != currentPage * 4 || isEmpty == true) {
      productPane1.setStyle("-fx-border-color: black; -fx-background-color: #DEDEDE; -fx-border-radius: 7; -fx-border-width: 0 1 1 1;");
      productPane2.setStyle("-fx-border-color: black; -fx-background-color: #F1F1F1; -fx-border-radius: 7; -fx-border-width: 0 1 1 1;");
      productPane3.setStyle("-fx-border-color: black; -fx-background-color: #F1F1F1; -fx-border-radius: 7; -fx-border-width: 0 1 1 1;");
      productPane4.setStyle("-fx-border-color: black; -fx-background-color: #F1F1F1; -fx-border-radius: 7; -fx-border-width: 0 1 1 1;");

      activeProduct = product1;
      Product product = product1;
      if (isEmpty != true) {
        product = watchList.get(((currentPage) * 4));
        activeProduct = product;
      }

      Image productImage = new Image(product.getImage());
      productImageView.setImage(productImage);
      String name = product.getName();
      String url = product.getUrl();
      activeURL = url;
      String price = Double.toString(product.getLastPrice());
      productPriceLabel.setText(price + "₺");
      plotProductPrices(product.getPrices());

      if (product.getAlarm_id() != -1) {
        priceAlarmPane.setVisible(true);
        String json = Requests.sendLoadAlarmRequest(product.getAlarm_id());
        Alarm alarm = loadAlarm(json);
        conditionLabel.setText(createConditionSentence(alarm.getCondition(), alarm.getTargetPrice()));
        setOnLabel.setText(alarm.getDateCreated());
        if (alarm.getDateTriggered() != null) {
          triggeredOnLabel.setText(alarm.getDateTriggered());
          priceAlarmPane.setStyle("-fx-border-color: black; -fx-border-radius: 7; -fx-border-width: 0 1 1 1; -fx-background-color: #c9c9c9");
        } else {
          triggeredOnLabel.setText("-");
          priceAlarmPane.setStyle("-fx-border-color: black; -fx-border-radius: 7; -fx-border-width: 0 1 1 1; -fx-background-color: #D8EDDF;");
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

      activeProduct = product2;
      Product product = product2;
      if (isEmpty != true) {
        product = watchList.get(((currentPage) * 4) + 1);
        activeProduct = product;
      }

      Image productImage = new Image(product.getImage());
      productImageView.setImage(productImage);
      String name = product.getName();
      String url = product.getUrl();
      activeURL = url;
      String price = Double.toString(product.getLastPrice());
      productPriceLabel.setText(price + "₺");
      plotProductPrices(product.getPrices());

      if (product.getAlarm_id() != -1) {
        priceAlarmPane.setVisible(true);
        String json = Requests.sendLoadAlarmRequest(product.getAlarm_id());
        Alarm alarm = loadAlarm(json);
        conditionLabel.setText(createConditionSentence(alarm.getCondition(), alarm.getTargetPrice()));
        setOnLabel.setText(alarm.getDateCreated());
        if (alarm.getDateTriggered() != null) {
          triggeredOnLabel.setText(alarm.getDateTriggered());
          priceAlarmPane.setStyle("-fx-border-color: black; -fx-border-radius: 7; -fx-border-width: 0 1 1 1; -fx-background-color: #c9c9c9");
        } else {
          triggeredOnLabel.setText("-");
          priceAlarmPane.setStyle("-fx-border-color: black; -fx-border-radius: 7; -fx-border-width: 0 1 1 1; -fx-background-color: #D8EDDF;");
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

      activeProduct = product3;
      Product product = product3;
      if (isEmpty != true) {
        product = watchList.get(((currentPage) * 4) + 2);
        activeProduct = product;
      }

      Image productImage = new Image(product.getImage());
      productImageView.setImage(productImage);
      String name = product.getName();
      String url = product.getUrl();
      activeURL = url;
      String price = Double.toString(product.getLastPrice());
      productPriceLabel.setText(price + "₺");
      plotProductPrices(product.getPrices());

      if (product.getAlarm_id() != -1) {
        priceAlarmPane.setVisible(true);
        String json = Requests.sendLoadAlarmRequest(product.getAlarm_id());
        Alarm alarm = loadAlarm(json);
        conditionLabel.setText(createConditionSentence(alarm.getCondition(), alarm.getTargetPrice()));
        setOnLabel.setText(alarm.getDateCreated());
        if (alarm.getDateTriggered() != null) {
          triggeredOnLabel.setText(alarm.getDateTriggered());
          priceAlarmPane.setStyle("-fx-border-color: black; -fx-border-radius: 7; -fx-border-width: 0 1 1 1; -fx-background-color: #c9c9c9");
        } else {
          triggeredOnLabel.setText("-");
          priceAlarmPane.setStyle("-fx-border-color: black; -fx-border-radius: 7; -fx-border-width: 0 1 1 1; -fx-background-color: #D8EDDF;");
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

      activeProduct = product4;
      Product product = product4;
      if (isEmpty != true) {
        product = watchList.get(((currentPage) * 4) + 3);
        activeProduct = product;
      }
      Image productImage = new Image(product.getImage());
      productImageView.setImage(productImage);
      String name = product.getName();
      String url = product.getUrl();
      activeURL = url;
      String price = Double.toString(product.getLastPrice());
      productPriceLabel.setText(price + "₺");
      plotProductPrices(product.getPrices());

      if (product.getAlarm_id() != -1) {
        priceAlarmPane.setVisible(true);
        String json = Requests.sendLoadAlarmRequest(product.getAlarm_id());
        Alarm alarm = loadAlarm(json);
        conditionLabel.setText(createConditionSentence(alarm.getCondition(), alarm.getTargetPrice()));
        setOnLabel.setText(alarm.getDateCreated());
        if (alarm.getDateTriggered() != null) {
          triggeredOnLabel.setText(alarm.getDateTriggered());
          priceAlarmPane.setStyle("-fx-border-color: black; -fx-border-radius: 7; -fx-border-width: 0 1 1 1; -fx-background-color: #c9c9c9");
        } else {
          triggeredOnLabel.setText("-");
          priceAlarmPane.setStyle("-fx-border-color: black; -fx-border-radius: 7; -fx-border-width: 0 1 1 1; -fx-background-color: #D8EDDF;");
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
    double targetPrice = Double.parseDouble(priceTextField.getText());

    String condition;
    if (conditionComboBox.getValue() == "Beliebige Änderung") {
      condition = "ANY_CHANGE";
      priceWarningLabel.setVisible(false);
    } else if (conditionComboBox.getValue() == "Unter dem Zielwert" && activeProduct.getLastPrice() >= targetPrice) {
      condition = "BELOW_TARGET";
      priceWarningLabel.setVisible(false);
    } else if (conditionComboBox.getValue() == "Über dem Zielwert" && activeProduct.getLastPrice() <= targetPrice) {
      condition = "ABOVE_TARGET";
      priceWarningLabel.setVisible(false);
    } else if (conditionComboBox.getValue() == "Gleich" && activeProduct.getLastPrice() != targetPrice) {
      condition = "EQUALS_TARGET";
      priceWarningLabel.setVisible(false);
    } else {
      priceWarningLabel.setVisible(true);
      return;
    }

    int alarmId = Integer.parseInt(Requests.sendSetAlarmRequest(activeProduct.getId(), activeProduct.getWatchlist_id(), LocalDate.now().toString(), condition, targetPrice));
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


  public void plotProductPrices(ArrayList<Double> prices) {
    ArrayList<LocalDate> dates = new ArrayList<>();
    for (int i = 6; i >= 0; i--) {
      LocalDate date = LocalDate.now().minusDays(i);
      dates.add(date);
    }
    // Clear any existing data
    productPriceLineChart.getData().clear();

    // Create a new data series for the product prices
    XYChart.Series<String, Double> series = new XYChart.Series<>();

    // Set the axis labels
    productPriceLineChart.getXAxis().setLabel("Datum");
    productPriceLineChart.getYAxis().setLabel("Preis");

    // Add the dates for the current product to the x-axis
    CategoryAxis categoryAxis = (CategoryAxis) productPriceLineChart.getXAxis();
    categoryAxis.setCategories(FXCollections.observableArrayList(dates.stream().map(date -> date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).collect(Collectors.toList())));

    // Iterate through the dates in reverse order and get the corresponding price for each date
    ArrayList<Double> pricesReversed = (ArrayList<Double>) prices.clone();
    Collections.reverse(pricesReversed);
    for (int i = dates.size() - 1; i >= 0; i--) {
      // Get the index of the price for the current date
      int priceIndex = prices.size() - (dates.size() - i);
      if (priceIndex >= 0 && priceIndex < prices.size()) {
        // Add the data point to the series
        series.getData().add(new XYChart.Data<>(dates.get(i).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), pricesReversed.get(priceIndex)));
      }
    }

    // Add the series to the chart
    productPriceLineChart.getData().add(series);

    // Hide the legend
    productPriceLineChart.setLegendVisible(false);
  }
}