package dev.sprout.main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;

/**
 * @author DEV Sprout
 */
public final class SidebarViewController {

  private final String ENTRY_PANEL = "/dev/sprout/main/EntryPanel.fxml";
  private final String CONTACT_PANEL = "/dev/sprout/main/ContactPanel.fxml";
  private final String PHOTO_PANEL = "/dev/sprout/main/PhotoPanel.fxml";

  public SidebarViewController() {}

  @FXML
  public void handleRedirectEntries(ActionEvent event) {
    Node node = (Node) event.getSource();
    ViewNavigator.loadView(node, this.ENTRY_PANEL);
  }

  @FXML
  public void handleRedirectContacts(ActionEvent event) {
    Node node = (Node) event.getSource();
    ViewNavigator.loadView(node, this.CONTACT_PANEL);
  }

  @FXML
  public void handleRedirectPhotos(ActionEvent event) {
    Node node = (Node) event.getSource();
    ViewNavigator.loadView(node, this.PHOTO_PANEL);
  }
}
