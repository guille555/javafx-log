package dev.sprout.main.controller;

import dev.sprout.main.model.Entry;
import dev.sprout.main.service.EntryServiceImp;
import dev.sprout.main.service.IEntryService;
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

/**
 * @author DEV Sprout
 */
public final class EntryPanelController implements Initializable {

  private final String ENTRY_NEW = "/dev/sprout/main/EntryNew.fxml";
  private final String ENTRY_EDIT = "/dev/sprout/main/EntryEdit.fxml";

  private final IEntryService service;
  private final ObservableList<Entry> listContactsObservable;

  private List<Entry> listEntries = null;

  @FXML
  private TextField textFieldDate;
  @FXML
  private TableView<Entry> tableEntries;
  @FXML
  private TableColumn<Entry, String> columnDate;

  public EntryPanelController() {
    this.service = new EntryServiceImp();
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
    String date = this.textFieldDate.getText();
    this.listContactsObservable.removeAll(this.listEntries);
    this.listEntries = this.service.findAllByTitle(date);
    this.listContactsObservable.setAll(this.listEntries);
  }

  @FXML
  public void handleClear(ActionEvent event) {
    this.textFieldDate.setText(null);
    this.listContactsObservable.removeAll(this.listEntries);
    this.listEntries = this.service.findAll();
    this.listContactsObservable.setAll(this.listEntries);
  }

  @FXML
  public void handleRedirectNew(ActionEvent event) {
    Node node = (Node) event.getSource();
    ViewNavigator.loadView(node, this.ENTRY_NEW);
  }

  @FXML
  public void handleEdit(ActionEvent event) {
    Node node = (Node) event.getSource();
    EntryEditController controller = null;
    Entry entry = this.tableEntries.getSelectionModel().getSelectedItem();
    if (entry != null) {
      controller = ViewNavigator.loadView(node, this.ENTRY_EDIT);
      controller.setEntry(entry);
    }
  }

  private void delete(Entry et) {
    boolean result = false;
    try {
      result = this.showDeleteMessage();
      if (result) {
        this.service.delete(et.getId());
        this.listContactsObservable.removeAll(this.listEntries);
        this.listEntries = this.service.findAll();
        this.listContactsObservable.setAll(this.listEntries);
        this.showSuccessMessage();
      }
    } catch (Exception e) {
      this.showErrorMessage();
    }
  }

  @FXML
  public void handleDelete(ActionEvent event) {
    Entry entry = this.tableEntries.getSelectionModel().getSelectedItem();
    if (entry != null) {
      this.delete(entry);
    }
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    this.listEntries = this.service.findAll();
    this.columnDate.setCellValueFactory(new PropertyValueFactory<>("title"));
    this.tableEntries.setItems(this.listContactsObservable);
    this.tableEntries.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    this.tableEntries.getColumns().forEach(col -> col.setReorderable(false));
    this.listContactsObservable.addAll(this.listEntries);
  }
}
