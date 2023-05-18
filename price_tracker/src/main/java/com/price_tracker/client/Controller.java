package com.price_tracker.client;

import com.price_tracker.client.objects.Product;
import com.price_tracker.client.objects.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.io.BufferedReader;
import java.io.IOException;

import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStreamReader;
import java.net.*;

import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

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
        conditionComboBox.getItems().addAll("Any change", "Below target", "Above target", "Equals");
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

            sendRequest(User.email, name, url, image);

            System.out.println(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendRequest(String email, String name, String url, String image) throws URISyntaxException, IOException, InterruptedException {
        String query = String.format("email=%s&name=%s&url=%s&image=%s",
                URLEncoder.encode(email, StandardCharsets.UTF_8),
                URLEncoder.encode(name, StandardCharsets.UTF_8),
                URLEncoder.encode(url, StandardCharsets.UTF_8),
                URLEncoder.encode(image, StandardCharsets.UTF_8));

        String urlString = "http://localhost:8080/watchlists/add-product?" + query;
        URL link = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) link.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println("Response code: " + responseCode);
        System.out.println("Response body: " + response.toString());
    }

}
