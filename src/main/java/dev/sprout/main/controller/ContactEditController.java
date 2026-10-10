package dev.sprout.main.controller;

import dev.sprout.main.model.Contact;
import dev.sprout.main.service.ContactServiceImp;
import java.util.HashMap;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import dev.sprout.main.service.IContactService;

/**
 * @author DEV Sprout
 */
public final class ContactEditController {

  private final String CONTACT_PANEL = "/dev/sprout/main/ContactPanel.fxml";

  private final IContactService service;

  private Contact contact;

  @FXML
  private TextField textFieldLastname;
  @FXML
  private TextField textFieldFirstname;
  @FXML
  private TextField textFieldPhoneNumber;
  @FXML
  private TextField textFieldEmail;

  public ContactEditController() {
    this.service = new ContactServiceImp();
  }

  private void prepareEdit() {
    if (this.contact != null) {
      this.textFieldLastname.setText(this.contact.getLastname());
      this.textFieldFirstname.setText(this.contact.getFirstname());
      this.textFieldPhoneNumber.setText(this.contact.getPhoneNumber());
      this.textFieldEmail.setText(this.contact.getEmail());
    }
  }

  public void setContact(Contact contact) {
    this.contact = contact;
    this.prepareEdit();
  }

  private Map<String, String> handleData() {
    String firstname = this.textFieldFirstname.getText();
    String lastname = this.textFieldLastname.getText();
    String phoneNumber = this.textFieldPhoneNumber.getText();
    String email = this.textFieldEmail.getText();
    Map<String, String> data = new HashMap<String, String>();
    data.put("id", this.contact.getId());
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
      Contact result = this.service.update(data);
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
    ViewNavigator.loadView(node, this.CONTACT_PANEL);
  }
}
