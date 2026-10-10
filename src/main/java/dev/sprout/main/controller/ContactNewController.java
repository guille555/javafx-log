package dev.sprout.main.controller;

import dev.sprout.main.model.Contact;
import dev.sprout.main.service.ContactServiceImp;
import java.util.HashMap;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import dev.sprout.main.service.IContactService;
import javafx.scene.control.TextField;

/**
 * @author DEV Sprout
 */
public final class ContactNewController {

  private final String CONTACT_PANEL = "/dev/sprout/main/ContactPanel.fxml";

  private final IContactService service;

  @FXML
  private TextField textFieldLastname;
  @FXML
  private TextField textFieldFirstname;
  @FXML
  private TextField textFieldPhoneNumber;
  @FXML
  private TextField textFieldEmail;

  public ContactNewController() {
    this.service = new ContactServiceImp();
  }

  private void clear() {
    this.textFieldLastname.setText(null);
    this.textFieldFirstname.setText(null);
    this.textFieldPhoneNumber.setText(null);
    this.textFieldEmail.setText(null);
  }

  private Map<String, String> handleData() {
    String lastname = this.textFieldLastname.getText();
    String firstname = this.textFieldFirstname.getText();
    String phoneNumber = this.textFieldPhoneNumber.getText();
    String email = this.textFieldEmail.getText();
    Map<String, String> data = new HashMap<String, String>();
    data.put("lastname", lastname);
    data.put("firstname", firstname);
    data.put("phoneNumber", phoneNumber);
    data.put("email", email);
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
      Contact contact = this.service.save(data);
      if (contact.getId()!= null) {
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
    ViewNavigator.loadView(node, this.CONTACT_PANEL);
  }
}
