package dev.sprout.main.controller;

import dev.sprout.main.model.Entry;
import dev.sprout.main.service.EntryServiceImp;
import dev.sprout.main.service.IEntryService;
import java.util.HashMap;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

/**
 * @author DEV Sprout
 */
public final class EntryNewController {

  private final String ENTRY_PANEL = "/dev/sprout/main/EntryPanel.fxml";

  private final IEntryService service;

  @FXML
  private TextArea textAreaContent;

  public EntryNewController() {
    this.service = new EntryServiceImp();
  }

  private void clear() {
    this.textAreaContent.setText(null);
  }

  private Map<String, String> handleData() {
    String content = this.textAreaContent.getText();
    Map<String, String> data = new HashMap<String, String>();
    data.put("content", content);
    return data;
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
      Map<String, String> data = this.handleData();
      Entry entry = this.service.save(data);
      if (entry.getId()!= null) {
        this.showSuccessMessage();
        this.clear();
      } else {
        this.showErrorMessage();
      }
    } catch (Exception e) {
      this.showErrorMessage();
    }
  }

  @FXML
  public void handleClear(ActionEvent event) {
    this.clear();
  }

  @FXML
  public void handleReturn(ActionEvent event) {
    Node node = (Node) event.getSource();
    ViewNavigator.loadView(node, this.ENTRY_PANEL);
  }
}
