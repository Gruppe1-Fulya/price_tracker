package price_tracker;

import javafx.fxml.FXML;
import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.MalformedURLException;

public class Controller {

  @FXML
  private Button myButton;

  @FXML
  private TextField inputField;

  @FXML
  private ImageView ProductImageView;

  @FXML
  private Label nameLabel;

  @FXML
  private Label priceLabel;

  @FXML
  private Label urlLabel;

  @FXML
  private void handleAddProductButtonClick() throws Exception {
    String text = inputField.getText();
    inputField.clear();
    try {
      Product product = new Product(text);

      urlLabel.setText(product.getUrl());
      nameLabel.setText(product.getName());
      priceLabel.setText(""+product.getPrices().get(product.getPrices().size() - 1));

      ProductImageView.setImage(product.getImage());
      urlLabel.setVisible(true);
      nameLabel.setVisible(true);
      priceLabel.setVisible(true);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}