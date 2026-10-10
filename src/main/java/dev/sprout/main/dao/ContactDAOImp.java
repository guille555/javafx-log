package dev.sprout.main.dao;

import dev.sprout.main.config.PathConfig;
import dev.sprout.main.model.Contact;
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
public final class ContactDAOImp implements IContactDAO {

  @Override
  public String save(String ct) {
    try {
      String[] values = ct.split(";");
      Path path = PathConfig.getStoragePath(PathConfig.CONTACT_FOLDER);
      Path filePath = path.resolve(values[0] + ".txt");
      Files.writeString(filePath, ct, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
      return ct;
    } catch (IOException e) {
      return null;
    }
  }

  @Override
  public String update(String ct) {
    Path path = null;
    Path filePath = null;
    String[] values = null;
    try {
      values = ct.split(";");
      path = PathConfig.getStoragePath(PathConfig.CONTACT_FOLDER);
      filePath = path.resolve(values[0] + ".txt");
      Files.writeString(filePath, ct, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
      return ct;
    } catch (IOException e) {
      return null;
    }
  }

  @Override
  public String delete(String ct) {
    Path path = null;
    Path filePath = null;
    String[] values = null;
    try {
      values = ct.split(";");
      path = PathConfig.getStoragePath(PathConfig.CONTACT_FOLDER);
      filePath = path.resolve(values[0] + ".txt");
      Files.deleteIfExists(filePath);
      return ct;
    } catch (IOException e) {
      return null;
    }
  }

  private Contact parseString(String txt) {
    String[] text = txt.split(";");
    Contact result = Contact.create(
      text[1],
      text[2],
      text[3],
      text[4]
    );
    result.setId(text[0]);
    return result;
  }

  private Contact convertPath(Path path) {
    String text = null;
    Contact result = null;
    try {
      text = Files.readString(path);
      result = this.parseString(text);
      return result;
    } catch (Exception e) {
      result = Contact.create();
      return result;
    }
  }

  @Override
  public List<Contact> findAll() throws IOException {
    Path path = PathConfig.getStoragePath(PathConfig.CONTACT_FOLDER);
    List<Contact> result = null;
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
