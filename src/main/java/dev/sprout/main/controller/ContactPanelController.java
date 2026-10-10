package dev.sprout.main.controller;

import dev.sprout.main.model.Contact;
import dev.sprout.main.service.ContactServiceImp;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import dev.sprout.main.service.IContactService;

/**
 * @author DEV Sprout
 */
public final class ContactPanelController implements Initializable {

  private final String CONTACT_NEW = "/dev/sprout/main/ContactNew.fxml";
  private final String CONTACT_EDIT = "/dev/sprout/main/ContactEdit.fxml";
  private final IContactService service;
  private final ObservableList<Contact> listContactsObservable;

  private List<Contact> listContacts = null;

  @FXML
  private TextField textLastname;
  @FXML
  private TextField textFirstname;
  @FXML
  private TableView<Contact> tableContacts;
  @FXML
  private TableColumn<Contact, String> columnLastname;
  @FXML
  private TableColumn<Contact, String> columnFirstname;

  public ContactPanelController() {
    this.service = new ContactServiceImp();
    this.listContactsObservable = FXCollections.observableArrayList();
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

  private boolean showDeleteMessage() {
    Alert message = new Alert(Alert.AlertType.CONFIRMATION, "Delete selected item?", ButtonType.YES, ButtonType.NO);
    Optional<ButtonType> result = message.showAndWait();
    if ((result.isPresent()) && (result.get() == ButtonType.YES)) {
      return true;
    }
    return false;
  }

  @FXML
  public void handleFilter(ActionEvent event) {
    String fullname = this.textLastname.getText() + " " + this.textFirstname.getText();
    fullname = fullname.toUpperCase();
    this.listContactsObservable.removeAll(this.listContacts);
    this.listContacts = this.service.findAllByFilters(fullname);
    this.listContactsObservable.setAll(this.listContacts);
  }

  @FXML
  public void handleClear(ActionEvent event) {
    this.textLastname.setText(null);
    this.textLastname.setText(null);
    this.listContactsObservable.removeAll(this.listContacts);
    this.listContacts = this.service.findAll();
    this.listContactsObservable.setAll(this.listContacts);
  }

  @FXML
  public void handleRedirectNew(ActionEvent event) {
    Node node = (Node) event.getSource();
    ViewNavigator.loadView(node, this.CONTACT_NEW);
  }

  @FXML
  public void handleEdit(ActionEvent event) {
    Node node = (Node) event.getSource();
    ContactEditController controller = null;
    Contact contact = this.tableContacts.getSelectionModel().getSelectedItem();
    if (contact != null) {
      controller = ViewNavigator.loadView(node, this.CONTACT_EDIT);
      controller.setContact(contact);
    }
  }

  private void delete(Contact ct) {
    boolean result = false;
    try {
      result = this.showDeleteMessage();
      if (result) {
        this.service.delete(ct.getId());
        this.listContactsObservable.removeAll(this.listContacts);
        this.listContacts = this.service.findAll();
        this.listContactsObservable.setAll(this.listContacts);
        this.showSuccessMessage();
      }
    } catch (Exception e) {
      this.showErrorMessage();
    }
  }

  @FXML
  public void handleDelete(ActionEvent event) {
    Contact contact = this.tableContacts.getSelectionModel().getSelectedItem();
    if (contact != null) {
      this.delete(contact);
    }
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    this.listContacts = this.service.findAll();
    this.columnLastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
    this.columnFirstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
    this.tableContacts.setItems(this.listContactsObservable);
    this.tableContacts.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    this.tableContacts.getColumns().forEach(col -> col.setReorderable(false));
    this.listContactsObservable.addAll(this.listContacts);
  }
}
