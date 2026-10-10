package dev.sprout.main.controller;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author DEV Sprout
 */
public final class ViewNavigator {

  public ViewNavigator() {}

  public static <T> T loadView(Node node, String path) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(ViewNavigator.class.getResource(path));
      Parent root = fxmlLoader.load();
      Stage stage = (Stage) node.getScene().getWindow();
      stage.setTitle("Java Journal");
      stage.setResizable(false);
      stage.setScene(new Scene(root, 720, 720));
      stage.show();
      return fxmlLoader.getController();
    } catch (IOException e) {
      return null;
    }
  }
}
