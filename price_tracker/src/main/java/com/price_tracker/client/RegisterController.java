package com.price_tracker.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterController {
  @FXML
  private TextField nameTextField;
  @FXML
  private TextField surnameTextField;
  @FXML
  private TextField emailTextField;
  @FXML
  private TextField passwordTextField;
  @FXML
  private Button registerButton;



  @FXML

  protected void onRegisterButtonClick() {
    String name = nameTextField.getText();
    String surname = surnameTextField.getText();
    String email = emailTextField.getText();
    String password = passwordTextField.getText();
    System.out.println(name + surname);
    System.out.println(email);
    System.out.println(password);
  }

  @FXML
  protected void onLoginLabelClick() throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
    Scene loginScene = new Scene(root);

    Stage stage = (Stage) registerButton.getScene().getWindow();
    stage.setScene(loginScene);
  }

  public static String sendRequest(String url, String requestBody) throws IOException {
    URL requestUrl = new URL(url);
    HttpURLConnection con = (HttpURLConnection) requestUrl.openConnection();
    con.setRequestMethod("POST");
    con.setRequestProperty("Content-Type", "application/json");
    con.setDoOutput(true);

    OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
    out.write(requestBody);
    out.flush();
    out.close();

    int responseCode = con.getResponseCode();
    if (responseCode == HttpURLConnection.HTTP_OK) {
      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
      StringBuilder response = new StringBuilder();
      String line;
      while ((line = in.readLine()) != null) {
        response.append(line);
      }
      in.close();
      return response.toString();
    } else {
      throw new IOException("Request failed with response code " + responseCode);
    }
  }
}