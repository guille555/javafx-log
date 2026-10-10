package dev.sprout.main.dao;

import dev.sprout.main.model.Contact;
import java.io.IOException;
import java.util.List;

/**
 * @author DEV Sprout
 */
public interface IContactDAO {

  public abstract String save(String ct);

  public abstract String update(String ct);

  public abstract String delete(String ct);

  public abstract List<Contact> findAll() throws IOException;
}
