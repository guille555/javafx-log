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
public final class EntryEditController {

  private final String ENTRY_PANEL = "/dev/sprout/main/EntryPanel.fxml";

  private final IEntryService service;

  private Entry entry;

  @FXML
  private TextArea textAreaContent;

  public EntryEditController() {
    this.service = new EntryServiceImp();
  }

  private void prepareEdit() {
    if (this.entry != null) {
      this.textAreaContent.setText(this.entry.getContent());
    }
  }

  public void setEntry(Entry et) {
    this.entry = et;
    this.prepareEdit();
  }

  private Map<String, String> handleData() {
    String content = this.textAreaContent.getText();
    Map<String, String> data = new HashMap<String, String>();
    data.put("id", this.entry.getId());
    data.put("title", this.entry.getTitle());
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
      Entry result = this.service.update(data);
      if (result.getId()!= null) {
        this.showSuccessMessage();
      } else {
        this.showErrorMessage();
      }
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  @FXML
  public void handleReturn(ActionEvent event) {
    Node node = (Node) event.getSource();
    ViewNavigator.loadView(node, this.ENTRY_PANEL);
  }
}
