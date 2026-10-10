package dev.sprout.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

  private static Scene scene;

  private static Parent loadFXML(String fxml) throws IOException {
    String path = fxml + ".fxml";
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(path));
    return fxmlLoader.load();
  }

  public static void main(String[] args) {
    Application.launch();
  }

  @Override
  public void start(Stage stage) throws IOException {
    String path = "EntryPanel";
    App.scene = new Scene(App.loadFXML(path), 720, 720);
    stage.setTitle("Java Journal");
    stage.setScene(App.scene);
    stage.setResizable(false);
    stage.show();
  }
}
