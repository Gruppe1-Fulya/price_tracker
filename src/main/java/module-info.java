module com.price_tracker.client {
    requires javafx.controls;
    requires javafx.fxml;
  requires org.jsoup;


  opens com.price_tracker.client to javafx.fxml;
    exports com.price_tracker.client;
}