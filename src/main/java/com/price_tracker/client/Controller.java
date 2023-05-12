package com.price_tracker.client;

import com.price_tracker.client.ProductInterface;
import javafx.fxml.FXML;
import java.io.IOException;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
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
            ProductInterface product = new ProductInterface(text);

            urlLabel.setText(product.getUrl());
            nameLabel.setText(product.getName());
            priceLabel.setText(""+product.getPrices().get(product.getPrices().size() - 1));

            ProductImageView.setImage(new Image(product.getImage()));
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