package price_tracker;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception{
    Parent root = FXMLLoader.load(getClass().getResource("price_tracker.fxml"));
    Image icon = new Image(getClass().getResourceAsStream("icon.png"));
    primaryStage.getIcons().add(icon);
    primaryStage.setTitle("Price Tracker");
    primaryStage.setScene(new Scene(root, 600, 400));
    primaryStage.show();
  }


  public static void main(String[] args) {
    launch(args);
  }
}