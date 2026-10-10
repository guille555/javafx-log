package dev.sprout.main.controller;

import dev.sprout.main.App;
import dev.sprout.main.model.Photo;
import dev.sprout.main.service.IPhotoService;
import dev.sprout.main.service.PhotoServiceImp;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author DEV Sprout
 */
public final class PhotoPanelController implements Initializable {

  private final String PHOTO_NEW;
  private final IPhotoService service;
  private final ObservableList<String> listContactsObservable;

  private List<String> listPhotos = null;

  @FXML
  private TextField textFieldSearch;
  @FXML
  private TableColumn<String, String> columnName;
  @FXML
  private TableView<String> tablePhotos;

  public PhotoPanelController() {
    this.PHOTO_NEW = "/dev/sprout/main/PhotoNew.fxml";
    this.service = new PhotoServiceImp();
    this.listContactsObservable = FXCollections.observableArrayList();
  }

  private List<String> listPhotos() {
    List<String> result = this.service.findAll();
    return result;
  }

  private void showSuccessMessage() {
    Alert message = new Alert(Alert.AlertType.INFORMATION);
    message.setTitle("Message");
    message.setHeaderText("Success");
    message.setContentText("Operation completed ok");
    message.showAndWait();
  }

  private void showErrorMessage() {
    Alert message = new Alert(Alert.AlertType.ERROR);
    message.setTitle("Message");
    message.setHeaderText("Error");
    message.setContentText("Operation error");
    message.showAndWait();
  }

  private void showPhoto(String name) {
    try {
      String path = "PhotoMediaPlayer.fxml";
      FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(path));
      Parent root = fxmlLoader.load();
      Stage stage = new Stage();
      PhotoMediaPlayerController controller = fxmlLoader.getController();
      stage.setTitle("Photo viewer");
      stage.setScene(new Scene(root, 720, 720));
      stage.setResizable(false);
      controller.showPhoto(name);
      stage.show();
    } catch (IOException e) {
    }
  }

  private void deletePhoto(String nm) {
    Photo result = null;
    try {
      result = this.service.delete(nm);
      if (result.getName() != null) {
        this.showSuccessMessage();
      } else {
        this.showErrorMessage();
      }
    } catch (Exception e) {
      this.showErrorMessage();
    }
  }

  @FXML
  public void handleFilter(ActionEvent event) {
    String name = this.textFieldSearch.getText();
    List<String> list = this.service.findAllByFilters(name);
    this.listContactsObservable.removeAll(this.listPhotos);
    this.listPhotos = list;
    this.listContactsObservable.addAll(this.listPhotos);
  }

  @FXML
  public void handleClear(ActionEvent event) {
    this.textFieldSearch.setText(null);
    this.listContactsObservable.removeAll(this.listPhotos);
    this.listPhotos = this.listPhotos();
    this.listContactsObservable.addAll(this.listPhotos);
  }

  @FXML
  public void handleRedirectNew(ActionEvent event) {
    Node node = (Node) event.getSource();
    ViewNavigator.loadView(node, this.PHOTO_NEW);
  }

  @FXML
  public void handleView(ActionEvent event) {
    String name = this.tablePhotos.getSelectionModel().getSelectedItem();
    if (name != null) {
      this.showPhoto(name);
    }
  }

  @FXML
  public void handleDelete(ActionEvent event) {
    String name = this.tablePhotos.getSelectionModel().getSelectedItem();
    if (name != null) {
      this.deletePhoto(name);
      this.listContactsObservable.removeAll(this.listPhotos);
      this.listPhotos = this.service.findAll();
      this.listContactsObservable.addAll(this.listPhotos);
    }
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    this.listPhotos = this.listPhotos();
    this.columnName.setCellValueFactory(item -> new SimpleStringProperty(item.getValue()));
    this.tablePhotos.setItems(this.listContactsObservable);
    this.tablePhotos.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    this.tablePhotos.getColumns().forEach(col -> col.setReorderable(false));
    this.listContactsObservable.addAll(this.listPhotos);
  }
}
