package dev.sprout.main.dao;

import dev.sprout.main.config.PathConfig;
import dev.sprout.main.model.Photo;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author DEV Sprout
 */
public final class PhotoDAOImp implements IPhotoDAO {

  @Override
  public void save(Photo ct) throws IOException {
    Path path = PathConfig.getStoragePath(PathConfig.PHOTO_FOLDER);
    path = path.resolve(ct.getName());
    Files.write(
      path,
      ct.getContent(),
      StandardOpenOption.CREATE_NEW,
      StandardOpenOption.WRITE
    );
  }

  @Override
  public void delete(Photo ct) throws IOException {
    Path path = PathConfig.getStoragePath(PathConfig.PHOTO_FOLDER);
    path = path.resolve(ct.getName());
    Files.deleteIfExists(path);
  }

  @Override
  public Optional<String> findById(String id) throws IOException {
    Path path = PathConfig.getStoragePath(PathConfig.PHOTO_FOLDER);
    if (Files.notExists(path.resolve(id))) {
      return Optional.empty();
    }
    return Optional.of(path.getFileName().toString());
  }

  @Override
  public List<String> findAll() throws IOException {
    try (Stream<Path> stream = Files.list(Paths.get(PathConfig.PHOTO_FOLDER))) {
      return stream
        .filter(item -> !Files.isDirectory(item))
        .map(Path::getFileName)
        .map(Path::toString)
        .collect(Collectors.toList());
    }
  }

  @Override
  public List<String> findAllByFilters(String name) throws IOException {
    try (Stream<Path> stream = Files.list(Paths.get(PathConfig.PHOTO_FOLDER))) {
      return stream
        .filter(item -> !Files.isDirectory(item))
        .filter(item -> item.getFileName().toString().contains(name))
        .map(Path::getFileName)
        .map(Path::toString)
        .collect(Collectors.toList());
    } catch(Exception ex) {
      System.err.println(ex.getMessage());
      return List.of();
    }
  }
}
