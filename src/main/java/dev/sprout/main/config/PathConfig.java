package dev.sprout.main.config;

import java.io.File;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.file.Path;

/**
 * @author DEV Sprout
 */
public final class PathConfig {

  private static final String APP_FOLDER = "storage";
  public static final String CONTACT_FOLDER = PathConfig.APP_FOLDER + "/contacts";
  public static final String ENTRY_FOLDER = PathConfig.APP_FOLDER + "/entries";
  public static final String PHOTO_FOLDER = PathConfig.APP_FOLDER + "/media/photos";

  public static Path getStoragePath(String path) throws IOException {
    File directory = new File(path);
    if (!directory.exists()) {
      Files.createDirectories(directory.toPath());
    }
    return directory.toPath();
  }
}
