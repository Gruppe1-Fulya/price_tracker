package com.price_tracker.client;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import com.price_tracker.client.objects.Requests;

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
  private Label warningLabel;

  @FXML
  protected void onRegisterButtonClick() throws IOException {
    String name = nameTextField.getText();
    nameTextField.clear();
    String surname = surnameTextField.getText();
    surnameTextField.clear();
    String email = emailTextField.getText();
    emailTextField.clear();
    String password = passwordTextField.getText();
    passwordTextField.clear();
    String body = "{\n" +
        "  \"email\": \"" + email + "\",\n" +
        "  \"name\": \"" + name + "\",\n" +
        "  \"surname\": \"" + surname + "\",\n" +
        "  \"password\": \"" + password + "\"\n" +
        "}";
    String response = Requests.sendRegisterRequest("http://localhost:8080/users/register", body);

    if (response.equals("User already exists")) {
      warningLabel.setVisible(true);
    } else {
      onLoginLabelClick();
    }
  }

  @FXML
  protected void onLoginLabelClick() throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
    Scene loginScene = new Scene(root);

    Stage stage = (Stage) registerButton.getScene().getWindow();
    stage.setScene(loginScene);
  }
}