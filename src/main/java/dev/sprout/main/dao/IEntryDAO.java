package dev.sprout.main.dao;

import dev.sprout.main.model.Entry;
import java.io.IOException;
import java.util.List;

/**
 * @author DEV Sprout
 */
public interface IEntryDAO {

  public abstract String save(String et);

  public abstract String update(String et);

  public abstract String delete(String et);

  public abstract List<Entry> findAll() throws IOException;
}
