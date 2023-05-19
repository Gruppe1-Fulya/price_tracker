package com.price_tracker.client;

import java.net.*;
import javafx.fxml.FXML;
import java.io.IOException;
import javafx.scene.control.*;
import javafx.scene.chart.LineChart;
import com.price_tracker.client.objects.User;
import com.price_tracker.client.objects.Product;
import com.price_tracker.client.objects.Requests;

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
  private void initialize() {
    conditionComboBox.getItems().addAll("Beliebige Änderung", "Unter dem Zielwert", "Über dem Zielwert", "Gleich");
    welcomeLabel.setText("Willkommen, " + User.name);
  }

  @FXML
  private void handleAddProductTextFieldEnter() throws Exception {
    String text = addProductTextField.getText();
    addProductTextField.clear();
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
  }


  @FXML
  public void deleteProduct1() {
    System.out.println("deleteProduct1");
  }

  @FXML
  public void nextButtonClick() {
    System.out.println("nextButtonClick");
  }

  @FXML
  public void handleSetAlarmTextFieldEnter() {
    System.out.println("handleSetAlarmTextFieldEnter");
  }

  @FXML
  public void deleteAlarm() {
    System.out.println("deleteAlarm");
  }

  @FXML
  public void previousButtonClick() {
    System.out.println("previousButtonClick");
  }
}