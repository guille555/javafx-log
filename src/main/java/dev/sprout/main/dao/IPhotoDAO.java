package dev.sprout.main.dao;

import dev.sprout.main.model.Photo;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author DEV Sprout
 */
public interface IPhotoDAO {

  public abstract void save(Photo ct) throws IOException;

  public abstract void delete(Photo ct) throws IOException;

  public abstract Optional<String> findById(String id) throws IOException;

  public abstract List<String> findAll() throws IOException;

  public abstract List<String> findAllByFilters(String name) throws IOException;
}
