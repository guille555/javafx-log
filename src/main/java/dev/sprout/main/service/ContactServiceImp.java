package dev.sprout.main.service;

import dev.sprout.main.dao.ContactDAOImp;
import dev.sprout.main.dao.IContactDAO;
import dev.sprout.main.model.Contact;
import java.io.IOException;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author DEV Sprout
 */
public final class ContactServiceImp implements IContactService {

  private IContactDAO dao;

  public ContactServiceImp() {
    this.dao = new ContactDAOImp();
  }

  private Contact create(Map<String, String> data) {
    String id = UUID.randomUUID().toString();
    String lastname = data.get("lastname").trim().toUpperCase();
    String firstname = data.get("firstname").trim().toUpperCase();
    String phoneNumber = data.get("phoneNumber").trim();
    String email = data.get("email").trim().toLowerCase();
    Contact result = Contact.create(lastname, firstname, phoneNumber, email);
    result.setId(id);
    return result;
  }

  private String parseString(Contact ct) {
    String result = null;
    StringBuilder builder = new StringBuilder();
    builder.append(ct.getId()).append(";");
    builder.append(ct.getLastname()).append(";");
    builder.append(ct.getFirstname()).append(";");
    builder.append(ct.getPhoneNumber()).append(";");
    builder.append(ct.getEmail());
    result = builder.toString();
    return result;
  }

  private void controlOperation(String ct) {
    if (ct == null) {
      throw new EmptyStackException();
    }
  }

  @Override
  public Contact save(Map<String, String> data) {
    String text = null;
    Contact result = null;
    try {
      result = this.create(data);
      text = this.parseString(result);
      text = this.dao.save(text);
      this.controlOperation(text);
      return result;
    } catch (Exception e) {
      System.err.println(e.getMessage());
      result = Contact.create();
      return result;
    }
  }

  private Contact findById(String id) {
    Contact contact = this.findAll().stream()
      .filter(item -> item.getId().equals(id))
      .findFirst()
      .orElse(Contact.create());
    return contact;
  }

  private Contact edit(Contact ct, Map<String, String> data) {
    String id = data.get("id").trim();
    String lastname = data.get("lastname").trim().toUpperCase();
    String firstname = data.get("firstname").trim().toUpperCase();
    String phoneNumber = data.get("phoneNumber").trim();
    String email = data.get("email").trim().toLowerCase();
    ct.setId(id);
    ct.setLastname(lastname);
    ct.setFirstname(firstname);
    ct.setPhoneNumber(phoneNumber);
    ct.setEmail(email);
    return ct;
  }

  @Override
  public Contact update(Map<String, String> data) {
    String text = null;
    Contact contact = null;
    Contact result = null;
    try {
      contact = this.findById(data.get("id"));
      if (contact.getId() != null) {
        result = this.edit(contact, data);
        text = this.parseString(result);
        text = this.dao.update(text);
      }
      this.controlOperation(text);
      return result;
    } catch (Exception e) {
      System.err.println(e.getMessage());
      result = Contact.create();
      return result;
    }
  }

  @Override
  public Contact delete(String id) {
    String text = null;
    Contact contact = null;
    Contact result = null;
    try {
      contact = this.findById(id);
      if (contact.getId() != null) {
        text = this.parseString(contact);
        text = this.dao.delete(text);
      }
      this.controlOperation(text);
      return result;
    } catch (Exception e) {
      System.err.println(e.getMessage());
      result = Contact.create();
      return result;
    }
  }

  @Override
  public List<Contact> findAll() {
    List<Contact> list = null;
    try {
      list = this.dao.findAll();
      return list;
    } catch (IOException e) {
      list = List.of();
      return list;
    }
  }

  private boolean filterFullname(String fn, Contact ct) {
    String fullname = ct.getLastname() + " " + ct.getFirstname();
    boolean result = fn.toUpperCase().contains(fullname);
    return result;
  }

  @Override
  public List<Contact> findAllByFilters(String fullname) {
    List<Contact> list = null;
    try {
      list = this.findAll();
      list = list.stream()
        .filter(item -> this.filterFullname(fullname, item))
        .collect(Collectors.toList());
      return list;
    } catch (Exception e) {
      return List.of();
    }
  }
}
