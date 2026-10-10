package dev.sprout.main.service;

import dev.sprout.main.model.Photo;
import java.util.List;

/**
 * @author DEV Sprout
 */
public interface IPhotoService {

  public abstract Photo save(String nm, String ex, byte[] cnt);

  public abstract Photo delete(String nm);

  public abstract String getPhoto(String id);

  public abstract List<String> findAll();

  public abstract List<String> findAllByFilters(String name);
}
