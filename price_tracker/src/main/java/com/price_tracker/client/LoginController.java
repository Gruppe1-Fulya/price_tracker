package com.price_tracker.client;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import com.price_tracker.client.objects.User;
import com.price_tracker.client.objects.Requests;

public class LoginController {
  @FXML
  private TextField emailTextField;
  @FXML
  private PasswordField passwordTextField;
  @FXML
  private Button loginButton;
  @FXML
  private Label warningLabel;

  @FXML
  protected void onLoginButtonClick() throws IOException {
    String email = emailTextField.getText();
    emailTextField.clear();
    String password = passwordTextField.getText();
    passwordTextField.clear();

    String body = "{\n" +
        "  \"email\": \"" + email + "\",\n" +
        "  \"password\": \"" + password + "\"\n" +
        "}";
    String response = Requests.sendLoginRequest("http://localhost:8080/users/login", body);
    if (response.equals("0")) {
      warningLabel.setText("E-Mail-Adresse/Passwort ist falsch!");
      warningLabel.setVisible(true);
    } else if (response.equals("-1")) {
      warningLabel.setText("        Benutzer nicht gefunden!");
      warningLabel.setVisible(true);
    } else {
      int idIndex = response.indexOf("\"id\":") + 5;
      int commaIndex = response.indexOf(",", idIndex);
      int id = Integer.parseInt(response.substring(idIndex, commaIndex).trim());

      int emailIndex = response.indexOf("\"email\":") + 8;
      commaIndex = response.indexOf(",", emailIndex);
      String emailAddress = response.substring(emailIndex, commaIndex).trim().replaceAll("\"", "");

      int nameIndex = response.indexOf("\"name\":") + 7;
      commaIndex = response.indexOf(",", nameIndex);
      String name = response.substring(nameIndex, commaIndex).trim().replaceAll("\"", "");

      int surnameIndex = response.indexOf("\"surname\":") + 10;
      commaIndex = response.indexOf(",", surnameIndex);
      String surname = response.substring(surnameIndex, commaIndex).trim().replaceAll("\"", "");

      User user = new User(name, surname, emailAddress, id);

      Parent root = FXMLLoader.load(getClass().getResource("price_tracker.fxml"));
      Scene mainScene = new Scene(root);

      Stage stage = (Stage) loginButton.getScene().getWindow();
      stage.close();
      stage.setScene(mainScene);
      stage.show();
    }
  }

  @FXML
  protected void onRegisterLabelClick() throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
    Scene registerScene = new Scene(root);

    Stage stage = (Stage) loginButton.getScene().getWindow();
    stage.setScene(registerScene);
  }
}