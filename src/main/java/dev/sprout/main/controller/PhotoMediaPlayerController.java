package dev.sprout.main.controller;

import dev.sprout.main.config.PathConfig;
import java.nio.file.Path;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author DEV Sprout
 */
public final class PhotoMediaPlayerController {

  @FXML
  private ImageView imageViewer;

  public PhotoMediaPlayerController() {}

  public void showPhoto(String name) {
    String route = null;
    Path path = null;
    StringBuilder builder = new StringBuilder(PathConfig.PHOTO_FOLDER);
    builder.append("/");
    builder.append(name);
    route = builder.toString();
    path = Path.of(route);
    Image image = new Image(path.toUri().toString());
    this.imageViewer.setImage(image);
  }
}
