package dev.sprout.main.dao;

import dev.sprout.main.config.PathConfig;
import dev.sprout.main.model.Entry;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author DEV Sprout
 */
public final class EntryDAOImp implements IEntryDAO {

  @Override
  public String save(String et) {
    String[] values = null;
    try {
      values = et.split("::");
      Path path = PathConfig.getStoragePath(PathConfig.ENTRY_FOLDER);
      Path filePath = path.resolve(values[0] + ".txt");
      Files.writeString(filePath, et, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
      return et;
    } catch (IOException e) {
      return null;
    }
  }

  @Override
  public String update(String et) {
    Path path = null;
    Path filePath = null;
    String[] values = null;
    try {
      values = et.split("::");
      path = PathConfig.getStoragePath(PathConfig.ENTRY_FOLDER);
      filePath = path.resolve(values[0] + ".txt");
      Files.writeString(filePath, et, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
      return et;
    } catch (IOException e) {
      return null;
    }
  }

  @Override
  public String delete(String et) {
    Path path = null;
    Path filePath = null;
    String[] values = null;
    try {
      values = et.split("::");
      path = PathConfig.getStoragePath(PathConfig.ENTRY_FOLDER);
      filePath = path.resolve(values[0] + ".txt");
      Files.deleteIfExists(filePath);
      return et;
    } catch (IOException e) {
      return null;
    }
  }

  private Entry parseString(String txt) {
    String[] text = txt.split("::");
    Entry result = Entry.create(
      text[1],
      text[2]
    );
    result.setId(text[0]);
    return result;
  }

  private Entry convertPath(Path path) {
    String text = null;
    Entry result = null;
    try {
      text = Files.readString(path);
      result = this.parseString(text);
      return result;
    } catch (Exception e) {
      result = Entry.create();
      return result;
    }
  }

  @Override
  public List<Entry> findAll() throws IOException {
    Path path = PathConfig.getStoragePath(PathConfig.ENTRY_FOLDER);
    List<Entry> result = null;
    try(
      Stream<Path> stream = Files.list(path)
    ) {
      result = stream
        .filter(Files::isRegularFile)
        .filter(item -> item.toString().endsWith(".txt"))
        .map(this::convertPath)
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
      return result;
    } catch (IOException e) {
      result = List.of();
      return result;
    }
  }
}
