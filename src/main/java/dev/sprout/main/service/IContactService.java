package dev.sprout.main.service;

import dev.sprout.main.model.Contact;
import java.util.List;
import java.util.Map;

/**
 * @author DEV Sprout
 */
public interface IContactService {

  public abstract Contact save(Map<String, String> data);

  public abstract Contact update(Map<String, String> data);

  public abstract Contact delete(String id);

  public abstract List<Contact> findAll();

  public abstract List<Contact> findAllByFilters(String fullname);
}
