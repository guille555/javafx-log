module dev.sprout.main {
  requires javafx.controls;
  requires javafx.graphics;
  requires javafx.fxml;

  opens dev.sprout.main to javafx.fxml;
  opens dev.sprout.main.model to javafx.fxml;
  opens dev.sprout.main.controller to javafx.fxml;
  exports dev.sprout.main;
  exports dev.sprout.main.model;
  exports dev.sprout.main.controller;
}
