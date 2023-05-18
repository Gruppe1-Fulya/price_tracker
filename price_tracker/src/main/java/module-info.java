module com.price_tracker.client {
    requires javafx.controls;
    requires javafx.fxml;
  requires org.jsoup;
    requires java.net.http;


    opens com.price_tracker.client to javafx.fxml;
    exports com.price_tracker.client;
  exports com.price_tracker.client.objects;
  opens com.price_tracker.client.objects to javafx.fxml;
}