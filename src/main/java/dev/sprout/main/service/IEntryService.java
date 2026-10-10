package dev.sprout.main.service;

import dev.sprout.main.model.Entry;
import java.util.List;
import java.util.Map;

/**
 * @author DEV Sprout
 */
public interface IEntryService {

  public abstract Entry save(Map<String, String> data);

  public abstract Entry update(Map<String, String> data);

  public abstract Entry delete(String id);

  public abstract List<Entry> findAll();

  public abstract List<Entry> findAllByTitle(String tl);
}
