package dev.sprout.main.controller;

import dev.sprout.main.service.IPhotoService;
import dev.sprout.main.service.PhotoServiceImp;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

/**
 * @author DEV Sprout
 */
public final class PhotoNewController {

  private final String PHOTO_PANEL;
  private final IPhotoService service;

  private File selectedFile = null;

  @FXML
  private TextField textFieldName;

  public PhotoNewController() {
    this.PHOTO_PANEL = "/dev/sprout/main/PhotoPanel.fxml";
    this.service = new PhotoServiceImp();
  }

  private void clear() {
    this.textFieldName.setText(null);
  }

  private void showSuccessMessage() {
    Alert message = new Alert(Alert.AlertType.INFORMATION);
    message.setTitle("Message");
    message.setHeaderText("Success");
    message.setContentText("Operation completed ok");
    message.showAndWait();
  }

  private void showErrorMessage() {
    Alert message = new Alert(Alert.AlertType.INFORMATION);
    message.setTitle("Message");
    message.setHeaderText("Error");
    message.setContentText("Operation error");
    message.showAndWait();
  }

  @FXML
  public void handleSave(ActionEvent event) {
    try {
      if (this.selectedFile != null) {
        byte[] content = Files.readAllBytes(this.selectedFile.toPath());
        this.service.save(this.selectedFile.getName(), "jpeg", content);
        this.showSuccessMessage();
        this.clear();
      }
    } catch (IOException e) {
      this.showErrorMessage();
    } finally {
      this.selectedFile = null;
    }
  }

  @FXML
  public void handleFileChooser(ActionEvent event) {
    Node node = (Node) event.getSource();
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Choose New...");
    this.selectedFile = fileChooser.showOpenDialog(node.getScene().getWindow());
    if (this.selectedFile != null) {
      this.textFieldName.setText(this.selectedFile.getName());
    }
  }

  @FXML
  public void handleClear(ActionEvent event) {
    this.clear();
    this.selectedFile = null;
  }

  @FXML
  public void handleReturn(ActionEvent event) {
    Node node = (Node) event.getSource();
    ViewNavigator.loadView(node, this.PHOTO_PANEL);
  }
}
